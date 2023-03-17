package com.blusalt.droneservice.models.dto;

import com.blusalt.droneservice.models.enums.DroneStateConstant;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import lombok.Data;

@Data
public class DroneUpdateDto {
    private Double weightLimit;
    private Integer batteryLevel;
    private DroneStateConstant droneState;
    private GenericStatusConstant status;
}
