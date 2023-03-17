package com.blusalt.droneservice.models.dto;

import com.blusalt.droneservice.models.Drone;
import com.blusalt.droneservice.models.DroneModel;
import com.blusalt.droneservice.models.DroneState;
import com.blusalt.droneservice.models.constraint.ExistsColumnValue;
import com.blusalt.droneservice.models.enums.DroneStateConstant;
import lombok.Data;

import javax.validation.constraints.*;

import static com.blusalt.droneservice.models.constants.Constants.*;

@Data
public class DroneDto{

    @NotBlank
    @Size(min = 1, max = 100, message = "Please provide serial number min value= 1 and max value = 100")
    private String serialNumber;

    @Min(value = WEIGHT_MIN_LIMIT, message = "Weight limit in gram (gr) min value= 1")
    @Max(value = WEIGHT_MAX_LIMIT, message = "Weight limit in gram (gr) max value = 500")
    private double weightLimit;

    @Min(value = 1, message = "Battery percentage (%) min value = 1")
    @Max(value = 100, message = "Battery percentage (%) max value = 100")
    private int batteryLevel;

    @ExistsColumnValue(
            value = DroneModel.class,
            message = "Invalid model Id.")
    private long droneModelId;

    public static Drone toModel(DroneDto dto) {
        Drone drone = new Drone();

        drone.setSerialNumber(dto.getSerialNumber());
        drone.setWeightLimit(dto.getWeightLimit());
        drone.setBatteryLevel(dto.getBatteryLevel());

        return drone;
    }
}
