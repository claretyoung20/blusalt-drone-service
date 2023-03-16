package com.blusalt.droneservice.models.dto;

import com.blusalt.droneservice.models.DroneState;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import lombok.Data;

import java.io.Serializable;

@Data
public class DroneStateDto implements Serializable {

    private String state;
    private GenericStatusConstant droneStateStatus;


    public static DroneState toModel(DroneStateDto dto) {
        DroneState droneState = new DroneState();

        droneState.setState(dto.getState());
        droneState.setStatus(dto.getDroneStateStatus());

        return droneState;
    }
}
