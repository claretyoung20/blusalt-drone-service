package com.blusalt.droneservice.service.impl;


import com.blusalt.droneservice.models.Delivery;
import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.DroneModel;
import com.blusalt.droneservice.models.Item;
import com.blusalt.droneservice.models.dto.DroneDto;
import com.blusalt.droneservice.models.dto.DroneUpdateDto;
import com.blusalt.droneservice.models.enums.DeliveryStatus;
import com.blusalt.droneservice.models.enums.DroneStateConstant;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import com.blusalt.droneservice.models.pojos.DronePojo;
import com.blusalt.droneservice.repository.DeliveryRepository;
import com.blusalt.droneservice.repository.DroneRepository;
import com.blusalt.droneservice.repository.ItemRepository;
import com.blusalt.droneservice.service.DroneModelService;
import com.blusalt.droneservice.service.DroneService;
import com.blusalt.droneservice.web.rest.errors.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.blusalt.droneservice.models.constants.Constants.*;

/**
 * Service Implementation for managing {@link Drone}.
 */
@Service
@Transactional
public class DroneServiceImpl implements DroneService {

    private final Logger log = LoggerFactory.getLogger(DroneServiceImpl.class);

    @Inject
    private DroneRepository droneRepository;
    @Inject
    private DroneModelService droneModelService;

    @Inject
    private DeliveryRepository deliveryRepository;

    @Inject
    private ItemRepository itemRepository;


    @Override
    public DronePojo save(DroneDto droneDto) {
        log.debug("Request to save Drone payload : {}", droneDto);

        Optional<Drone> withSerialNumber = droneRepository.findDroneBySerialNumber(droneDto.getSerialNumber());

        if (withSerialNumber.isPresent()) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with serial number: %s already exist", droneDto.getSerialNumber()));
        }

        Drone drone = DroneDto.toModel(droneDto);

        DroneModel droneModel = droneModelService.findById(droneDto.getDroneModelId()).get();

        drone.setDroneModel(droneModel);
        drone.setStatus(GenericStatusConstant.ACTIVE);
        drone.setDroneState(DroneStateConstant.IDLE);
        drone.setDateCreated(new Date());

        return DronePojo.toPojo(droneRepository.save(drone));
    }

    @Override
    public DronePojo update(DroneUpdateDto droneUpdateDto, Long id) {
        log.debug("Request to update Drone dto : {}", droneUpdateDto);

        Drone drone = droneRepository.findByIdAndStatusIsNot(id, GenericStatusConstant.DELETED)
                .map(existingDrone -> {
                    validateDroneDto(droneUpdateDto, existingDrone);
                    return existingDrone;
                }).map(droneRepository::save)
                .orElseThrow(() ->
                        new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d not found", id)));

        return DronePojo.toPojo(droneRepository.save(drone));
    }

    @Override
    public DronePojo updateDroneState(Long id, DroneStateConstant droneState) {

        Optional<Drone> optionalDrone = droneRepository.findByIdAndStatusIsNot(id, GenericStatusConstant.DELETED);

        if(optionalDrone.isPresent()){
            if(!optionalDrone.get().getStatus().getValue().equals(GenericStatusConstant.ACTIVE.getValue())) {
                throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d is not active", id));
            }
        }

        Drone drone = optionalDrone
                .map(existingDrone -> {
                    updateDroneState(existingDrone, droneState);
                    return existingDrone;
                }).map(droneRepository::save)
                .orElseThrow(() ->
                        new ErrorResponse(HttpStatus.NOT_FOUND, String.format("Drone with id: %d not found", id)));

        return DronePojo.toPojo(droneRepository.save(drone));
    }

    private void validateDroneDto(DroneUpdateDto droneUpdateDto, Drone existingDrone) {
        if (droneUpdateDto.getWeightLimit() != null) {
            if (droneUpdateDto.getWeightLimit() > WEIGHT_MIN_LIMIT &&
                    droneUpdateDto.getWeightLimit() <= WEIGHT_MAX_LIMIT) {
                existingDrone.setWeightLimit(droneUpdateDto.getWeightLimit());
            } else {
                throw new ErrorResponse(HttpStatus.BAD_REQUEST,
                        String.format("Weight limit in gram (gr) min value= %d and max = %d", WEIGHT_MIN_LIMIT, WEIGHT_MAX_LIMIT));
            }
        }

        if (droneUpdateDto.getBatteryLevel() != null) {
            if (droneUpdateDto.getBatteryLevel() > BATTERY_MIN_LIMIT &&
                    droneUpdateDto.getBatteryLevel() <= BATTERY_MAX_LIMIT) {
                existingDrone.setBatteryLevel(droneUpdateDto.getBatteryLevel());
            } else {
                throw new ErrorResponse(HttpStatus.BAD_REQUEST,
                        String.format("Battery percentage min value = %d and max = %d", BATTERY_MIN_LIMIT, BATTERY_MAX_LIMIT));
            }

        }
        updateDroneStatus(droneUpdateDto, existingDrone);

        existingDrone.setDateUpdated(new Date());
    }

    private void updateDroneStatus(DroneUpdateDto droneUpdateDto, Drone existingDrone) {
        if (droneUpdateDto.getStatus() != null) {
            if (!droneUpdateDto.getStatus().getValue().equals(GenericStatusConstant.ACTIVE.getValue())) {

                if (existingDrone.getDroneState().value().equals(DroneStateConstant.LOADED.getValue())) {

                    updateDeliveryAndItems(existingDrone, DeliveryStatus.CANCELED);

                }
            }
            existingDrone.setStatus(droneUpdateDto.getStatus());
        }

    }

    private void updateDeliveryAndItems(Drone existingDrone, DeliveryStatus statusDelivery) {
        Optional<Delivery> delivery = deliveryRepository.findByDrone(existingDrone);
        if (delivery.isPresent()) {
            Delivery delivery1 = delivery.get();
            delivery1.setDeliveryStatus(statusDelivery);
            delivery1.setDateUpdated(new Date());

            if(statusDelivery.value().equals(DeliveryStatus.IN_TRANSIT.getValue())){
                delivery1.setStartTime(Instant.now());
            }

            if(statusDelivery.value().equals(DeliveryStatus.DELIVERY_COMPLETED.getValue())){
                delivery1.setEndTime(Instant.now());
            }

            deliveryRepository.save(delivery1);

            /** this can be updated to actually update at it own time*/
            List<Item> items = delivery1.getItems().stream().map(item -> {
                item.setItemStatus(statusDelivery);
                item.setDateUpdated(new Date());
                return item;
            }).collect(Collectors.toList());

            if (items.isEmpty()) {
                itemRepository.saveAll(items);
            }
        }
    }


    private void updateDroneState(Drone existingDrone, DroneStateConstant droneState) {

        if (existingDrone.getDroneState().getValue().equals(droneState.value())) {
            return;
        }

        String existingDroneState = existingDrone.getDroneState().getValue();

        if (droneState.getValue().equals(DroneStateConstant.LOADING.value()) ||
                droneState.getValue().equals(DroneStateConstant.LOADED.value())) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "To change Drone to LOADING or LOADED state go through the load function");
        } else if (existingDroneState.equals(DroneStateConstant.LOADED.value()) &&
                droneState.getValue().equals(DroneStateConstant.DELIVERING.value())) {

            updateDeliveryAndItems(existingDrone, DeliveryStatus.IN_TRANSIT);

            existingDrone.setDroneState(droneState);
        } else if (existingDroneState.equals(DroneStateConstant.DELIVERING.value()) &&
                droneState.getValue().equals(DroneStateConstant.DELIVERED.value())) {

            updateDeliveryAndItems(existingDrone, DeliveryStatus.DELIVERY_COMPLETED);
            existingDrone.setDroneState(droneState);
        } else if (existingDroneState.equals(DroneStateConstant.DELIVERED.value()) &&
                droneState.getValue().equals(DroneStateConstant.RETURNING.value())) {

            existingDrone.setDroneState(droneState);
        }else if (existingDroneState.equals(DroneStateConstant.RETURNING.value()) &&
                droneState.getValue().equals(DroneStateConstant.IDLE.value())) {

            existingDrone.setDroneState(droneState);
        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Drone state can only be changed in this order: IDLE -> LOADING - > LOADED -> DELIVERING -> DELIVERED -> RETURNING");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DronePojo> findAllActiveDrone() {
        log.debug("Request to get all active Drones");
        return droneRepository.findAllByStatusActive().stream().map(DronePojo::toPojo).collect(Collectors.toList());
    }

    @Override
    public List<DronePojo> findAllActiveAvailableDrone() {
        log.debug("Request to get all active Drones");
        return droneRepository.findAllByDroneState(DroneStateConstant.IDLE).stream().map(DronePojo::toPojo).collect(Collectors.toList());
    }

    @Override
    public List<DronePojo> findAllDrone() {
        log.debug("Request to get all Drones");
        return droneRepository.findAllByStatusIsNot(GenericStatusConstant.DELETED).stream().map(DronePojo::toPojo).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DronePojo findDroneById(Long id) {
        log.debug("Request to get Drone : {}", id);

        Optional<Drone> drone = droneRepository.findByIdAndStatusIsNot(id, GenericStatusConstant.DELETED);

        if (drone.isEmpty()) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d not found", id));
        }

        return DronePojo.toPojo(drone.get());

    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Drone : {}", id);

        Optional<Drone> droneById = droneRepository.findActiveDroneById(id);

        if (droneById.isPresent()) {

            Drone drone = droneById.get();
            drone.setStatus(GenericStatusConstant.DELETED);
            drone.setDateUpdated(new Date());

            droneRepository.save(drone);
        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d not found", id));
        }
    }
}
