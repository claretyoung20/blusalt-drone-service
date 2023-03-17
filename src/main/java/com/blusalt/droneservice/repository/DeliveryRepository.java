package com.blusalt.droneservice.repository;

import com.blusalt.droneservice.models.Delivery;
import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.enums.DeliveryStatusDelivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findByDroneAndDeliveryStatus(Drone drone, DeliveryStatusDelivery statusDelivery);
    Optional<Delivery> findByDrone(Drone drone);

}
