package com.blusalt.droneservice.repository;

import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.enums.DroneStateConstant;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the Drone entity.
 */
@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    @Query("select drone from Drone drone where drone.id = ?1 and drone.status='ACTIVE'")
    Optional<Drone> findActiveDroneById(Long id);

    @Query("select drone from Drone drone where drone.id = ?1 and drone.droneState = ?2 and drone.status='ACTIVE'")
    Optional<Drone> findActiveDroneByIdAndDroneState(Long id, DroneStateConstant state);

    @Query("select drone from Drone drone where drone.droneState = ?1 and drone.status='ACTIVE'")
    List<Drone> findAllByDroneState(DroneStateConstant state);

    @Query("select ds from Drone ds where ds.status='ACTIVE'")
    List<Drone> findAllByStatusActive();

    List<Drone> findAllByStatusIsNot(GenericStatusConstant statusConstant);

    Optional<Drone> findByIdAndStatusIsNot(Long id, GenericStatusConstant statusConstant);

    Optional<Drone> findDroneBySerialNumber(String serialNumber);

}
