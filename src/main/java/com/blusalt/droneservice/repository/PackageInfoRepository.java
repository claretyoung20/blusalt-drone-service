package com.blusalt.droneservice.repository;

import com.blusalt.droneservice.models.PackageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the DroneState entity.
 */
public interface PackageInfoRepository extends JpaRepository<PackageInfo, Long> {

    Optional<PackageInfo> findByCode(String code);
}
