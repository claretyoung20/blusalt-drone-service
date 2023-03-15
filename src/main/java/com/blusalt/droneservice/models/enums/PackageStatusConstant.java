package com.blusalt.droneservice.models.enums;

public enum PackageStatusConstant {

    PROCESSING("PROCESSING"),
    IN_TRANSIT("IN_TRANSIT"),
    DELIVERED("DELIVERED");
    private final String enumValue;
    private PackageStatusConstant(String value) {
        this.enumValue = value;
    }

    public String value() {
        return this.enumValue;
    }


    public String getValue() {
        return this.enumValue;
    }
}
