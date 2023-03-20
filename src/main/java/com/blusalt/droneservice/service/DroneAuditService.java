package com.blusalt.droneservice.service;


import com.blusalt.droneservice.models.DroneAudit;

import java.util.List;

/**
 * Service Interface for managing {@link DroneAudit}.
 */
public interface DroneAuditService {
    /**
     * Save a droneAudit.
     *
     * @param droneAudit the entity to save.
     * @return the persisted entity.
     */
    DroneAudit save(DroneAudit droneAudit);


    List<DroneAudit> findAll();

}
