package com.blusalt.droneservice.service.impl;


import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.DroneModel;
import com.blusalt.droneservice.models.DroneState;
import com.blusalt.droneservice.models.dto.DroneDto;
import com.blusalt.droneservice.models.dto.DroneUpdateDto;
import com.blusalt.droneservice.models.pojos.DronePojo;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import com.blusalt.droneservice.repository.DroneRepository;
import com.blusalt.droneservice.service.DroneModelService;
import com.blusalt.droneservice.service.DroneService;
import com.blusalt.droneservice.service.DroneStateService;
import com.blusalt.droneservice.web.rest.errors.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.blusalt.droneservice.models.Constants.*;

/**
 * Service Implementation for managing {@link Drone}.
 */
@Service
@Transactional
public class DroneServiceImpl implements DroneService {

    private final Logger log = LoggerFactory.getLogger(DroneServiceImpl.class);

    private final DroneRepository droneRepository;
    private final DroneModelService droneModelService;
    private final DroneStateService droneStateService;

    public DroneServiceImpl(DroneRepository droneRepository, DroneModelService droneModelService, DroneStateService droneStateService) {
        this.droneRepository = droneRepository;
        this.droneModelService = droneModelService;
        this.droneStateService = droneStateService;
    }

    @Override
    public DronePojo save(DroneDto droneDto) {
        log.debug("Request to save Drone payload : {}", droneDto);

        Drone drone = DroneDto.toModel(droneDto);

        Optional<DroneState> state = droneStateService.findById(droneDto.getDroneStateId());
        DroneState droneState = state.get();
        DroneModel droneModel = droneModelService.findById(droneDto.getDroneModelId()).get();

        drone.setDroneModel(droneModel);
        drone.setStatus(GenericStatusConstant.ACTIVE);
        drone.setDroneState(droneState);
        drone.setDateCreated(new Date());

        return DronePojo.toPojo(droneRepository.save(drone));
    }

    @Override
    public DronePojo update(DroneUpdateDto droneUpdateDto, Long id) {
        log.debug("Request to update Drone dto : {}", droneUpdateDto);

        Drone drone =  droneRepository.findActiveDroneById(id)
                .map(existingDrone -> {
                    validateDroneDto(droneUpdateDto, existingDrone);
                    return existingDrone;
                }).map(droneRepository::save)
                .orElseThrow(() ->
                        new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d not found", id)));

        return DronePojo.toPojo(droneRepository.save(drone));
    }

    private void validateDroneDto(DroneUpdateDto droneUpdateDto, Drone existingDrone) {
        if (droneUpdateDto.getWeightLimit() != null &&
                droneUpdateDto.getWeightLimit() > WEIGHT_MIN_LIMIT &&
                droneUpdateDto.getWeightLimit() <= WEIGHT_MAX_LIMIT) {
            existingDrone.setWeightLimit(droneUpdateDto.getWeightLimit());
        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST,
                    String.format("Weight limit in gram (gr) min value= %d and max = %d",WEIGHT_MIN_LIMIT, WEIGHT_MAX_LIMIT));
        }

        if (droneUpdateDto.getBatteryLevel() != null &&
                droneUpdateDto.getBatteryLevel() > BATTERY_MIN_LIMIT &&
                droneUpdateDto.getBatteryLevel() <= BATTERY_MAX_LIMIT) {
            existingDrone.setBatteryLevel(droneUpdateDto.getBatteryLevel());
        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST,
                    String.format("Battery percentage min value = %d and max = %d",BATTERY_MIN_LIMIT, BATTERY_MAX_LIMIT));
        }
        if (droneUpdateDto.getStatus() != null) {
            existingDrone.setStatus(droneUpdateDto.getStatus());
        }

        existingDrone.setDateUpdated(new Date());
    }
    @Override
    @Transactional(readOnly = true)
    public List<DronePojo> findAllActiveDrone() {
        log.debug("Request to get all active Drones");
        return droneRepository.findAllActiveDrone().stream().map(DronePojo::toPojo).collect(Collectors.toList());
    }

    @Override
    public List<DronePojo> findAllDrone() {
        log.debug("Request to get all Drones");
        return droneRepository.findAllByStatusIsNot(GenericStatusConstant.DELETED).stream().map(DronePojo::toPojo).collect(Collectors.toList());
    }

    public Page<Drone> findAllWithEagerRelationships(Pageable pageable) {
        return droneRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public DronePojo findDroneById(Long id) {
        log.debug("Request to get Drone : {}", id);

        Optional<Drone> drone = droneRepository.findActiveDroneById(id);

        if(drone.isEmpty()) {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d not found", id) );
        }

        return DronePojo.toPojo(drone.get());

    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Drone : {}", id);
        droneRepository.deleteById(id);

        Optional<Drone> droneById = droneRepository.findActiveDroneById(id);

        if(droneById.isPresent()){

            Drone drone = droneById.get();
            drone.setStatus(GenericStatusConstant.DELETED);
            drone.setDateUpdated(new Date());

            droneRepository.save(drone);
        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d not found", id) );
        }
    }
}
