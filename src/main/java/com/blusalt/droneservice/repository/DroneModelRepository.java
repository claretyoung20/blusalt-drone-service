package com.blusalt.droneservice.repository;


import com.blusalt.droneservice.models.DroneModel;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the DroneModel entity.
 */
@Repository
public interface DroneModelRepository extends JpaRepository<DroneModel, Long> {

    @Query("select dm from DroneModel dm where dm.modelName = ?1 and dm.droneModelStatus='ACTIVE'")
    Optional<DroneModel> findActiveDroneModel(String model);

    @Query("select dm from DroneModel dm where dm.id = ?1 and dm.droneModelStatus='ACTIVE'")
    Optional<DroneModel> findActiveDroneModelById(Long id);

    @Query("select dm from DroneModel dm where dm.droneModelStatus='ACTIVE'")
    List<DroneModel> findAllActiveDroneModel();

    List<DroneModel> findAllByDroneModelStatusIsNot(GenericStatusConstant statusConstant);
}
