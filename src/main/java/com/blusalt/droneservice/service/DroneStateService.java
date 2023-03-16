package com.blusalt.droneservice.service;


import com.blusalt.droneservice.models.DroneState;
import com.blusalt.droneservice.models.dto.DroneStateDto;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link DroneState}.
 */
public interface DroneStateService {
    /**
     * Save a droneState.
     *
     * @param droneState the entity to save.
     * @return the persisted entity.
     */
    DroneState save(DroneStateDto droneState);

    /**
     * Updates a droneState.
     *
     * @param droneState the entity to update.
     * @return the persisted entity.
     */
    DroneState updateStatus(DroneStateDto droneState, Long id);

    /**
     * Get all the droneStates.
     *
     * @return the list of entities.
     */
    List<DroneState> findAll();
    /**
     * Get all the active droneStates.
     *
     * @return the list of entities.
     */
    List<DroneState> findAllActive();

    /**
     * Get the "id" droneState.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DroneState> findById(Long id);

    /**
     * Get the "state droneState.
     *
     * @param state the state of the entity.
     * @return the entity.
     */
    Optional<DroneState> findByState(String state);

    /**
     * Delete the "id" droneState.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
