package com.blusalt.droneservice.web.rest;


import com.blusalt.droneservice.models.DroneAudit;
import com.blusalt.droneservice.service.DroneAuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

/**
 * REST controller for managing {@link com.blusalt.droneservice.models.DroneAudit}.
 */
@RestController
@RequestMapping("/api")
public class DroneAuditResource {

    private final Logger log = LoggerFactory.getLogger(DroneAuditResource.class);

    @Inject
    private  DroneAuditService droneAuditService;

    /**
     * {@code GET  /drone-audits} : get all the droneAudits.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of droneAudits in body.
     */
    @GetMapping("/drone-audits")
    public List<DroneAudit> getAllDroneAudits() {
        log.debug("REST request to get all DroneAudits");
        return droneAuditService.findAll();
    }
}
