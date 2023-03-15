package com.blusalt.droneservice.models.enums;

public enum PackageTypeConstant {
    ACTIVE("MEDICATION");

    private final String enumValue;
    private PackageTypeConstant(String value) {
        this.enumValue = value;
    }

    public String value() {
        return this.enumValue;
    }


    public String getValue() {
        return this.enumValue;
    }


}
