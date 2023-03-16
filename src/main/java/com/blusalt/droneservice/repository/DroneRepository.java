package com.blusalt.droneservice.repository;

import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("select ds from DroneState ds where ds.status='ACTIVE'")
    List<Drone> findAllActiveDrone();

    List<Drone> findAllByStatusIsNot(GenericStatusConstant statusConstant);




    default Optional<Drone> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Drone> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Drone> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select distinct drone from Drone drone left join fetch drone.droneState left join fetch drone.droneModel",
        countQuery = "select count(distinct drone) from Drone drone"
    )
    Page<Drone> findAllWithToOneRelationships(Pageable pageable);

    @Query("select distinct drone from Drone drone left join fetch drone.droneState left join fetch drone.droneModel")
    List<Drone> findAllWithToOneRelationships();

    @Query("select drone from Drone drone left join fetch drone.droneState left join fetch drone.droneModel where drone.id =:id")
    Optional<Drone> findOneWithToOneRelationships(@Param("id") Long id);
}
