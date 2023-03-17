package com.blusalt.droneservice.models.enums;

public enum DeliveryStatusDelivery {

    LOADING("LOADING"),
    IN_TRANSIT("IN_TRANSIT"),
    LOADING_COMPLETED("LOADING_COMPLETED"),
    DELIVERY_COMPLETED("DELIVERY_COMPLETED"),
    CANCELED("CANCELED");
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
