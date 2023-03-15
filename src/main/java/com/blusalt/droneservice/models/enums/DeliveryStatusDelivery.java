package com.blusalt.droneservice.models.enums;

public enum DeliveryStatusDelivery {

    ON_GOING("ON_GOING"),
    COMPLETED("COMPLETED");
    private final String enumValue;
    private DeliveryStatusDelivery(String value) {
        this.enumValue = value;
    }

    public String value() {
        return this.enumValue;
    }


    public String getValue() {
        return this.enumValue;
    }
}
