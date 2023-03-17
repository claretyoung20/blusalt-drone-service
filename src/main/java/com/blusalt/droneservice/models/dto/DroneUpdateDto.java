package com.blusalt.droneservice.models.dto;

import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import lombok.Data;

@Data
public class DroneUpdateDto {
    private Double weightLimit;
    private Integer batteryLevel;
    private GenericStatusConstant status;
}
