package com.blusalt.droneservice.service.impl;

import com.blusalt.droneservice.models.*;
import com.blusalt.droneservice.models.dto.AddressDto;
import com.blusalt.droneservice.models.dto.ItemDto;
import com.blusalt.droneservice.models.dto.ItemListDto;
import com.blusalt.droneservice.models.enums.DeliveryStatus;
import com.blusalt.droneservice.models.enums.DroneStateConstant;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import com.blusalt.droneservice.models.enums.ItemTypeConstant;
import com.blusalt.droneservice.repository.AddressRepository;
import com.blusalt.droneservice.repository.DeliveryRepository;
import com.blusalt.droneservice.repository.DroneRepository;
import com.blusalt.droneservice.repository.ItemRepository;
import com.blusalt.droneservice.service.ItemService;
import com.blusalt.droneservice.web.rest.errors.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing {@link DroneState}.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);
    @Inject
    private DroneRepository droneRepository;

    @Inject
    private ItemRepository itemRepository;

    @Inject
    private DeliveryRepository deliveryRepository;
    @Inject
    private AddressRepository addressRepository;


    @Override
    public void save(ItemListDto itemListDto) {

        Drone drone = validateAndGetDrone(itemListDto.getDroneId());

        double totalItemWeight = itemListDto.getItemDtos().stream().mapToDouble(ItemDto::getWeight).sum();

        if(drone.getWeightLimit() < totalItemWeight) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Drone weight limit exceeded");
        }

        updateDrone(drone, DroneStateConstant.LOADING);
        Delivery delivery = createDelivery(drone);

        List<String> itemDtoCodes = itemListDto.getItemDtos().stream().map(ItemDto::getCode).collect(Collectors.toList());

        if(itemDtoCodes.size() != itemDtoCodes.stream().distinct().count()){
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Invalid payload: Duplicate item code");
        }

        List<Item> itemsFromDb = itemRepository.findAllByCodeIn(itemDtoCodes);
        if(itemsFromDb.isEmpty()) {
            List<Item> items = itemListDto.getItemDtos().stream().map(itemDto -> getItem(itemDto, delivery)).collect(Collectors.toList());
            itemRepository.saveAll(items);
        } else{
            List<String> itemCodes = itemsFromDb.stream().map(Item::getCode).collect(Collectors.toList());
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Item with codes: %s already exist. Please generate fresh code and try again", itemCodes));
        }

        updateDelivery(delivery);
        updateDrone(drone, DroneStateConstant.LOADED);

    }

    private Item getItem(ItemDto itemDto, Delivery delivery) {
        Item item = new Item();
        item.setCode(itemDto.getCode());
        item.setImageUrl(itemDto.getImageUrl());
        item.setName(itemDto.getName());
        item.setItemStatus(DeliveryStatus.LOADING);
        item.setItemType(ItemTypeConstant.MEDICATION);
        item.setAddress(getAddress(itemDto.getAddressDto()));
        item.setDateCreated(new Date());
        item.setDelivery(delivery);
        item.setWeight(itemDto.getWeight());

        return item;
    }


    private void updateDrone(Drone drone, DroneStateConstant loaded) {
        drone.setDroneState(loaded);
        drone.setDateUpdated(new Date());
        droneRepository.save(drone);
    }

    private Delivery updateDelivery(Delivery delivery) {
        delivery.setDeliveryStatus(DeliveryStatus.LOADING_COMPLETED);
        delivery.setDateUpdated(new Date());
        return deliveryRepository.save(delivery);
    }

    private Delivery createDelivery(Drone drone) {
        Delivery delivery;
        Delivery newDelivery = new Delivery();
        newDelivery.setDeliveryStatus(DeliveryStatus.LOADING);
        newDelivery.setDrone(drone);
        newDelivery.setDateCreated(new Date());

        delivery = deliveryRepository.save(newDelivery);
        return delivery;
    }

    private Drone validateAndGetDrone(Long id) {
        Optional<Drone> optionalDrone = droneRepository.findByIdAndStatusIsNot(id, GenericStatusConstant.DELETED);

        if (optionalDrone.isEmpty()) {
            throw new ErrorResponse(HttpStatus.NOT_FOUND, "Drone not found" );
        }

        if(!optionalDrone.get().getStatus().getValue().equals(GenericStatusConstant.ACTIVE.getValue())) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d is not active", id));
        }

        Drone drone = optionalDrone.get();

        if(!drone.getDroneState().getValue().equals(DroneStateConstant.IDLE.getValue())) {

            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Drone is not available" );
        }

        if (drone.getDroneState().value().equals(DroneStateConstant.IDLE.value())) {

            if (drone.getBatteryLevel() < 25) {
                throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Drone battery is below 25%, please charge drone");
            }
        }
        return drone;
    }

    private Address getAddress(AddressDto addressDto) {
        Address address = new Address();
        if(addressDto.getFullAddress() != null &&
                !addressDto.getFullAddress().isEmpty()) {

            address.setFullAddress(addressDto.getFullAddress());
        }
        address.setLatitude(addressDto.getLatitude());
        address.setLongitude(addressDto.getLongitude());
        address.setDateCreated(new Date());
        return addressRepository.save(address);
    }
}
