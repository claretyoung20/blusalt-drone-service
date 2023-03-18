package com.blusalt.droneservice.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class AddressDto implements Serializable {

    private String fullAddress;

    @NotNull
    private Double longitude;

    @NotNull
    private Double latitude;

}
