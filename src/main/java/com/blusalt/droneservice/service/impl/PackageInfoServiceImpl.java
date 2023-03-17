package com.blusalt.droneservice.service.impl;

import com.blusalt.droneservice.models.*;
import com.blusalt.droneservice.models.dto.AddressDto;
import com.blusalt.droneservice.models.dto.PackageInfoDto;
import com.blusalt.droneservice.models.dto.PackageInfoListDto;
import com.blusalt.droneservice.models.enums.DeliveryStatusDelivery;
import com.blusalt.droneservice.models.enums.DroneStateConstant;
import com.blusalt.droneservice.models.enums.PackageTypeConstant;
import com.blusalt.droneservice.repository.AddressRepository;
import com.blusalt.droneservice.repository.DeliveryRepository;
import com.blusalt.droneservice.repository.DroneRepository;
import com.blusalt.droneservice.repository.PackageInfoRepository;
import com.blusalt.droneservice.service.PackageInfoService;
import com.blusalt.droneservice.web.rest.errors.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;


/**
 * Service Implementation for managing {@link DroneState}.
 */
@Service
@Transactional
public class PackageInfoServiceImpl implements PackageInfoService {
    @Inject
    private DroneRepository droneRepository;

    @Inject
    private PackageInfoRepository packageInfoRepository;

    @Inject
    private DeliveryRepository deliveryRepository;
    @Inject
    private AddressRepository addressRepository;


    @Override
    public void save(PackageInfoListDto packageInfoListDto) {

        Drone drone = validateAndGetDrone(packageInfoListDto.getDroneId());

        double totalPackage = packageInfoListDto.getPackages().stream().mapToDouble(PackageInfoDto::getWeight).sum();

        if(drone.getWeightLimit() < totalPackage) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Drone weight limit exceeded");
        }

        updateDrone(drone, DroneStateConstant.LOADING);

        Delivery delivery = createDelivery(drone);

        List<String> alreadyExistCode = new ArrayList<>();
        AtomicBoolean codesExist = new AtomicBoolean(false);

        List<PackageInfo> packageInfos = packageInfoListDto.getPackages().stream().map(packageInfoDto -> {

            Optional<PackageInfo> alreadyExistPackage = packageInfoRepository.findByCode(packageInfoDto.getCode());

            if(alreadyExistPackage.isPresent()){
                codesExist.set(true);
                alreadyExistCode.add(packageInfoDto.getCode());
                return null;
            }

            return getPackageInfo(packageInfoDto, delivery);
        }).collect(Collectors.toList());

        if(codesExist.get()) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Packages with codes: %s already exist. Please generate fresh code and try again", alreadyExistCode));
        }

        packageInfoRepository.saveAll(packageInfos);

        updateDelivery(delivery);
        updateDrone(drone, DroneStateConstant.LOADED);

    }

    private  PackageInfo getPackageInfo(PackageInfoDto packageInfoDto, Delivery delivery) {
        PackageInfo packageInfo = new PackageInfo();
        packageInfo.setCode(packageInfoDto.getCode());
        packageInfo.setImageUrl(packageInfoDto.getImageUrl());
        packageInfo.setName(packageInfoDto.getName());
        packageInfo.setPackageStatus(DeliveryStatusDelivery.LOADING);
        packageInfo.setPackageType(PackageTypeConstant.MEDICATION);
        packageInfo.setAddress(getAddress(packageInfoDto.getAddressDto()));
        packageInfo.setDateCreated(new Date());
        packageInfo.setDelivery(delivery);
        packageInfo.setWeight(packageInfoDto.getWeight());

        return packageInfo;
    }


    private void updateDrone(Drone drone, DroneStateConstant loaded) {
        drone.setDroneState(loaded);
        drone.setDateUpdated(new Date());
        droneRepository.save(drone);
    }

    private Delivery updateDelivery(Delivery delivery) {
        delivery.setDeliveryStatus(DeliveryStatusDelivery.LOADING_COMPLETED);
        delivery.setDateUpdated(new Date());
        return deliveryRepository.save(delivery);
    }

    private Delivery createDelivery(Drone drone) {
        Delivery delivery;
        Delivery newDelivery = new Delivery();
        newDelivery.setDeliveryStatus(DeliveryStatusDelivery.LOADING);
        newDelivery.setDrone(drone);
        newDelivery.setDateCreated(new Date());

        delivery = deliveryRepository.save(newDelivery);
        return delivery;
    }

    private Drone validateAndGetDrone(Long id) {
        Optional<Drone> optionalDrone = droneRepository.findActiveDroneById(id);

        if (optionalDrone.isEmpty()) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, "Drone not found" );
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
