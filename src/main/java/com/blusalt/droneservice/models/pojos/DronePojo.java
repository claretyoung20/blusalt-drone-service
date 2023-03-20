package com.blusalt.droneservice.models.pojos;

import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.enums.DroneStateConstant;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import lombok.Data;

import java.util.Date;

@Data
public class DronePojo {
    private Long id;
    private String serialNumber;
    private Double weightLimit;
    private Integer batteryLevel;
    private GenericStatusConstant droneStatus;
    private Date dateCreated;
    private Date dateUpdated;
    private DroneStateConstant state;
    private String model;

    public static DronePojo toPojo(Drone drone) {
        DronePojo dronePojo = new DronePojo();

        dronePojo.setId(drone.getId());
        dronePojo.setSerialNumber(drone.getSerialNumber());
        dronePojo.setWeightLimit(drone.getWeightLimit());
        dronePojo.setBatteryLevel(drone.getBatteryLevel());

        dronePojo.setDroneStatus(drone.getStatus());
        dronePojo.setDateCreated(drone.getDateCreated());
        dronePojo.setDateUpdated(drone.getDateUpdated());

        dronePojo.setState(drone.getDroneState());
        dronePojo.setModel(drone.getDroneModel().getModelName());

        return dronePojo;
    }
}
