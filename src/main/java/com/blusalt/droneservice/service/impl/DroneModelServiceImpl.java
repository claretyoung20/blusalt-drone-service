package com.blusalt.droneservice.service.impl;


import com.blusalt.droneservice.models.DroneModel;
import com.blusalt.droneservice.models.dto.DroneModelDto;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import com.blusalt.droneservice.repository.DroneModelRepository;
import com.blusalt.droneservice.service.DroneModelService;
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
 * Service Implementation for managing {@link DroneModel}.
 */
@Service
@Transactional
public class DroneModelServiceImpl implements DroneModelService {

    private final Logger log = LoggerFactory.getLogger(DroneModelServiceImpl.class);
    private static final String ENTITY_NAME = "droneModel";


    private final DroneModelRepository droneModelRepository;

    public DroneModelServiceImpl(DroneModelRepository droneModelRepository) {
        this.droneModelRepository = droneModelRepository;
    }

    @Override
    public DroneModel save(DroneModelDto droneModelDto) {
        log.debug("Request to save DroneModel, dto : {}", droneModelDto);
        DroneModel model = DroneModelDto.toModel(droneModelDto);
        model.setDateCreated(new Date());
        return droneModelRepository.save(model);
    }

    @Override
    public DroneModel updateStatus(DroneModelDto droneModelDto, Long id) {
        log.debug("Request to update DroneModel : {}", droneModelDto);

        Optional<DroneModel> model = droneModelRepository.findByIdAndStatusIsNot(id, GenericStatusConstant.DELETED);

        if(model.isPresent()){

            DroneModel droneModel = model.get();
            droneModel.setStatus(droneModelDto.getDroneModelStatus());
            droneModel.setDateUpdated(new Date());

            //todo check if name is part of the chnage do name chck on the db befr editig it

            return droneModelRepository.save(droneModel);
        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Model with id: %d not found", id) );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroneModel> findAll() {
        log.debug("Request to get all DroneModels");
        return droneModelRepository.findAllByStatusIsNot(GenericStatusConstant.DELETED);
    }

    @Override
    public List<DroneModel> findAllActive() {
        log.debug("Request to get all active DroneModels");
        return droneModelRepository.findAllActiveDroneModel();

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<DroneModel> findById(Long id) {
        log.debug("Request to get DroneModel : {}", id);
        return droneModelRepository.findByIdAndStatusIsNot(id, GenericStatusConstant.DELETED);
    }

    @Override
    public Optional<DroneModel> findByName(String name) {
        log.debug("Request to get DroneModel by name : {}", name);
        return droneModelRepository.findActiveDroneModel(name);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete DroneModel : {}", id);

        Optional<DroneModel> model = droneModelRepository.findActiveDroneModelById(id);

        if(model.isPresent()){

            DroneModel droneModel = model.get();

            droneModel.setStatus(GenericStatusConstant.DELETED);
            droneModel.setDateUpdated(new Date());

           droneModelRepository.save(droneModel);

        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Model with id: %d not found", id) );
        }
    }
}
