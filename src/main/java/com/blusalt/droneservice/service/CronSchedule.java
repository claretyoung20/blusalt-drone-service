package com.blusalt.droneservice.service;


import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.DroneAudit;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import com.blusalt.droneservice.repository.DroneAuditRepository;
import com.blusalt.droneservice.repository.DroneRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CronSchedule {

    private final Logger log = LoggerFactory.getLogger(CronSchedule.class);
    @Inject
    private DroneRepository droneRepository;

    @Inject
    DroneAuditRepository droneAuditRepository;

    @Scheduled(cron = "0 */5 * * * *")
    public void checkDroneBatteryLevel() {

        log.info("DRONES AUDIT EVERY 5 MINUTES");
        List<Drone> drones = droneRepository.findAllByStatusIsNot(GenericStatusConstant.DELETED);

        List<DroneAudit> droneAudits = drones.stream().map(drone -> {
            log.debug("DRONE AUDIT LOG: {}", drone);
            DroneAudit dAudit = new DroneAudit();
            dAudit.setDrone(drone);
            dAudit.setBatteryLevel(drone.getBatteryLevel());
            dAudit.setDateCreated(new Date());
            dAudit.setDroneState(drone.getDroneState().value());
            return dAudit;
        }).collect(Collectors.toList());

        log.info("Total drone: {}", droneAudits.size());

        if(!droneAudits.isEmpty()) {
            droneAuditRepository.saveAll(droneAudits);
        }

    }
}
