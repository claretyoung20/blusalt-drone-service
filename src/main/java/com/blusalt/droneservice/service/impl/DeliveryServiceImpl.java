package com.blusalt.droneservice.service.impl;

import com.blusalt.droneservice.models.Delivery;
import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.enums.DroneStateConstant;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import com.blusalt.droneservice.repository.DeliveryRepository;
import com.blusalt.droneservice.repository.DroneRepository;
import com.blusalt.droneservice.service.DeliveryService;
import com.blusalt.droneservice.web.rest.errors.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Delivery}.
 */
@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    private final Logger log = LoggerFactory.getLogger(DeliveryServiceImpl.class);
    @Inject
    private DeliveryRepository deliveryRepository;

    @Inject
    private DroneRepository droneRepository;

    @Override
    public Delivery getLoadedDeliveryByDrone(Long droneId) {

        Optional<Drone> droneById = droneRepository.findByIdAndStatusIsNot(droneId, GenericStatusConstant.DELETED);

        if(droneById.isPresent()) {
            Drone drone  = droneById.get();

            if(drone.getDroneState().value().equals(DroneStateConstant.LOADED.getValue())) {

                return deliveryRepository.findByDrone(drone)
                        .orElseThrow(() ->
                                new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Delivery packages with drone id: %d is not found", droneId)));
            }
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d is not loaded", droneId));
        } else {
            throw new ErrorResponse(HttpStatus.BAD_REQUEST, String.format("Drone with id: %d is not found", droneId));
        }

    }

}
