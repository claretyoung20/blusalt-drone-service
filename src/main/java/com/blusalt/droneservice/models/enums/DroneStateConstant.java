package com.blusalt.droneservice.models.enums;

public enum DroneStateConstant {
    IDLE("IDLE"),
    LOADING("LOADING"),
    LOADED("LOADED"),
    DELIVERING("DELIVERING"),
    RETURNING("RETURNING"),
    DELIVERED("DELIVERED");

    private final String enumValue;
    private DroneStateConstant(String value) {
        this.enumValue = value;
    }

    public String value() {
        return this.enumValue;
    }


    public String getValue() {
        return this.enumValue;
    }
}
