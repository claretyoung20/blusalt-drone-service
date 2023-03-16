package com.blusalt.droneservice.models.dto;

import com.blusalt.droneservice.models.DroneModel;
import com.blusalt.droneservice.models.enums.GenericStatusConstant;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class DroneModelDto implements Serializable {

    @NotBlank
    private String modelName;

    @NotNull
    private GenericStatusConstant droneModelStatus;

    public static DroneModel toModel(DroneModelDto dto) {
        DroneModel model = new DroneModel();

        model.setStatus(dto.getDroneModelStatus());
        model.setModelName(dto.getModelName());

        return model;
    }
}
