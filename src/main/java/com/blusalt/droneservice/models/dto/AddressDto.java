package com.blusalt.droneservice.models.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddressDto implements Serializable {

    private String fullAddress;
    private Double longitude;
    private Double latitude;

}
