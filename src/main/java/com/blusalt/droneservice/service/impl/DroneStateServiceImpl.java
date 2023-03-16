package com.blusalt.droneservice.service.impl;


import com.blusalt.droneservice.models.DroneState;
import com.blusalt.droneservice.models.dto.DroneStateDto;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import com.blusalt.droneservice.repository.DroneStateRepository;
import com.blusalt.droneservice.service.DroneStateService;
import com.blusalt.droneservice.web.rest.errors.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link DroneState}.
 */
@Service
@Transactional
public class DroneStateServiceImpl implements DroneStateService {

    private final Logger log = LoggerFactory.getLogger(DroneStateServiceImpl.class);

    private final DroneStateRepository droneStateRepository;

    public DroneStateServiceImpl(DroneStateRepository droneStateRepository) {
        this.droneStateRepository = droneStateRepository;
    }

    @Override
    public DroneState save(DroneStateDto droneStateDto) {
        log.debug("Request to save DroneState DTO: {}", droneStateDto);

        DroneState droneState = DroneStateDto.toModel(droneStateDto);
        droneState.setDateCreated(new Date());
        return droneStateRepository.save(droneState);
    }

    @Override
    public DroneState updateStatus(DroneStateDto droneStateDto, Long id) {
        log.debug("Request to update DroneState dto: {}", droneStateDto);

        Optional<DroneState> state = droneStateRepository.findActiveDroneStateById(id);

        if(state.isPresent()){

            DroneState droneModel = state.get();
            droneModel.setStatus(droneStateDto.getDroneStateStatus());
            droneModel.setDateUpdated(new Date());

            return droneStateRepository.save(droneModel);
        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("State with id: %d not found", id) );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroneState> findAll() {
        log.debug("Request to get all DroneStates");
        return droneStateRepository.findAllByStatusIsNot(GenericStatusConstant.DELETED);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroneState> findAllActive() {
        log.debug("Request to get all DroneStates");
        return droneStateRepository.findAllActiveDroneState();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroneState> findById(Long id) {
        log.debug("Request to get DroneState : {}", id);
        return droneStateRepository.findActiveDroneStateById(id);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroneState> findByState(String state) {
        log.debug("Request to get DroneState by state: {}", state);
        return droneStateRepository.findActiveDroneState(state);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DroneState : {}", id);

        Optional<DroneState> state = droneStateRepository.findActiveDroneStateById(id);

        if(state.isPresent()){

            DroneState droneModel = state.get();
            droneModel.setStatus(GenericStatusConstant.DELETED);
            droneModel.setDateUpdated(new Date());

            droneStateRepository.save(droneModel);
        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("state with id: %d not found", id) );
        }
    }
}
