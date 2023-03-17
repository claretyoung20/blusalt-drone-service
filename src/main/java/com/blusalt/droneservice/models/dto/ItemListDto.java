package com.blusalt.droneservice.models.dto;

import com.blusalt.droneservice.models.DroneState;
import com.blusalt.droneservice.models.constraint.ExistsColumnValue;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class ItemListDto {
    @ExistsColumnValue(
            value = DroneState.class,
            message = "Invalid drone state Id.")
    private long droneId;

    @Size(min = 1, message = "Please provide one item.")
    List<@Valid ItemDto> itemDtos;
}
