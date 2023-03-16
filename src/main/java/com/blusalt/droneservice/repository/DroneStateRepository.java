package com.blusalt.droneservice.repository;

import com.blusalt.droneservice.models.DroneState;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DroneState entity.
 */
@Repository
public interface DroneStateRepository extends JpaRepository<DroneState, Long> {
    @Query("select ds from DroneState ds where ds.state = ?1 and ds.droneStateStatus='ACTIVE'")
    Optional<DroneState> findActiveDroneState(String state);

    @Query("select ds from DroneState ds where ds.id = ?1 and ds.droneStateStatus='ACTIVE'")
    Optional<DroneState> findActiveDroneStateById(Long id);

    @Query("select ds from DroneState ds where ds.droneStateStatus='ACTIVE'")
    List<DroneState> findAllActiveDroneState();

    List<DroneState> findAllByDroneStateStatusIsNot(GenericStatusConstant statusConstant);
}
