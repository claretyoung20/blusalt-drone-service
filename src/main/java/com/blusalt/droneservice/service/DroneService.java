package com.blusalt.droneservice.service;

import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.dto.DroneDto;
import com.blusalt.droneservice.models.dto.DroneUpdateDto;
import com.blusalt.droneservice.models.pojos.DronePojo;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Drone}.
 */
public interface DroneService {
    /**
     * Save a drone.
     *
     * @param drone the entity to save.
     * @return the persisted entity.
     */
    DronePojo save(DroneDto drone);

    /**
     * Updates a drone.
     *
     * @param drone the entity to update.
     * @return the persisted entity.
     */
    DronePojo update(DroneUpdateDto drone, Long id);

    /**
     * Get all active the drones.
     *
     * @return the list of entities.
     */
    List<DronePojo> findAllActiveDrone();

    /**
     * Get all the drones.
     *
     * @return the list of entities.
     */
    List<DronePojo> findAllDrone();


    /**
     * Get the "id" drone.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    DronePojo findDroneById(Long id);

    /**
     * Delete the "id" drone.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
