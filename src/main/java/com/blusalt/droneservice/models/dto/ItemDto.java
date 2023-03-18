package com.blusalt.droneservice.models.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static com.blusalt.droneservice.models.constants.Constants.WEIGHT_MAX_LIMIT;
import static com.blusalt.droneservice.models.constants.Constants.WEIGHT_MIN_LIMIT;

@Data
public class ItemDto {

    @NotBlank(message = "Please provide name")
    @Pattern(regexp = "^[a-zA-Z0-9_\\-]*$", message = "can only be letters, numbers, '-', '_")
    private String name;

    @Min(value= WEIGHT_MIN_LIMIT, message = "Please provide weight, in gram (gr) min value 1")
    @Max(value= WEIGHT_MAX_LIMIT, message = "Please provide weight, in gram (gr) max value 500")
    private Double weight;

    @NotBlank(message = "Please provide code")
    @Pattern(regexp = "^[A-Z0-9_]*$", message = "can only upper case letters, underscore and numbers" )
    private String code; // auto generated

    @NotBlank(message = "Please provide image url")
    private String imageUrl;

    private AddressDto addressDto;



}
