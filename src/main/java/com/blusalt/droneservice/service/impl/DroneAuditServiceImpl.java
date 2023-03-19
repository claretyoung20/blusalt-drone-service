package com.blusalt.droneservice.service.impl;

import com.blusalt.droneservice.models.DroneAudit;
import com.blusalt.droneservice.repository.DroneAuditRepository;
import com.blusalt.droneservice.service.DroneAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing {@link DroneAudit}.
 */
@Service
@Transactional
public class DroneAuditServiceImpl implements DroneAuditService {

    private final Logger log = LoggerFactory.getLogger(DroneAuditServiceImpl.class);

    private final DroneAuditRepository droneAuditRepository;

    public DroneAuditServiceImpl(DroneAuditRepository droneAuditRepository) {
        this.droneAuditRepository = droneAuditRepository;
    }

    @Override
    public DroneAudit save(DroneAudit droneAudit) {
        log.debug("Request to save DroneAudit : {}", droneAudit);
        return droneAuditRepository.save(droneAudit);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DroneAudit> findAll() {
        log.debug("Request to get all DroneAudits");
        return droneAuditRepository.findAll();
    }

}
