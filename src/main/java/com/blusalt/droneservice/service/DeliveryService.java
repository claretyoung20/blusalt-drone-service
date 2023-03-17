package com.blusalt.droneservice.service;

import com.blusalt.droneservice.models.Delivery;

public interface DeliveryService {

    Delivery getLoadedDeliveryByDrone(Long droneId);
}
