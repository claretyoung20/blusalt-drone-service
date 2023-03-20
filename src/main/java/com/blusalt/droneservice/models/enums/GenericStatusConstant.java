package com.blusalt.droneservice.models.enums;

public enum GenericStatusConstant {
    ACTIVE("ACTIVE"),
    DELETED("DELETED"),
    INACTIVE("INACTIVE"),
    DEACTIVATED("DEACTIVATED");

    private final String enumValue;
    GenericStatusConstant(String value) {
        this.enumValue = value;
    }

    public String value() {
        return this.enumValue;
    }


    public String getValue() {
        return this.enumValue;
    }


}
