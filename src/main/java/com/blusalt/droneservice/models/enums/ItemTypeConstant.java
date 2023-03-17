package com.blusalt.droneservice.models.enums;

public enum ItemTypeConstant {
    MEDICATION("MEDICATION");

    private final String enumValue;
    private ItemTypeConstant(String value) {
        this.enumValue = value;
    }

    public String value() {
        return this.enumValue;
    }


    public String getValue() {
        return this.enumValue;
    }


}
