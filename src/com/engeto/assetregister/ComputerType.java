package com.engeto.assetregister;

public enum ComputerType {
    LAPTOP("laptop"), DESKTOP("desktop"), TABLET("tablet");

    final String description;

    ComputerType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
