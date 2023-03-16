package com.blusalt.droneservice.service;


import com.blusalt.droneservice.models.DroneModel;
import com.blusalt.droneservice.models.dto.DroneModelDto;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DroneModel}.
 */
public interface DroneModelService {
    /**
     * Save a droneModel.
     *
     * @param droneModelDto the entity to save.
     * @return the persisted entity.
     */
    DroneModel save(DroneModelDto droneModelDto);

    /**
     * Updates a droneModel.
     *
     * @param droneModelDto the entity to update.
     * @return the persisted entity.
     */
    DroneModel updateStatus(DroneModelDto droneModelDto, Long id);

    /**
     * Get all the droneModels.
     *
     * @return the list of entities.
     */
    List<DroneModel> findAll();

    /**
     * Get all the droneModels that are active.
     *
     * @return the list of entities.
     */
    List<DroneModel> findAllActive();

    /**
     * Get the "id" droneModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroneModel> findById(Long id);

    /**
     * Get the "name" droneModel.
     *
     * @param name the name of the entity.
     * @return the entity.
     */
    Optional<DroneModel> findByName(String name);

    /**
     * Delete the "id" droneModel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
