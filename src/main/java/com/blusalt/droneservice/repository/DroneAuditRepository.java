package com.blusalt.droneservice.repository;

import com.blusalt.droneservice.models.DroneAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the DroneAudit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DroneAuditRepository extends JpaRepository<DroneAudit, Long> {}
