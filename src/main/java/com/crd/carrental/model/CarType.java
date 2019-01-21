package com.crd.carrental.model;

import java.util.HashMap;
import java.util.Map;

// enum can be refactored to dynamic behaviour later on
public enum CarType {
    SEDAN("sedan"), SUV("suv"), MINIVAN("mini-van");

    private static final Map<String, CarType> lookup = new HashMap<>();

    static {
        for (CarType type : CarType.values()) {
            lookup.put(type.getTypeValue(), type);
        }
    }

    private String type;

    CarType(String type) {
        this.type = type;
    }

    public static CarType getType(String type) {
        return lookup.get(type);
    }

    public String getTypeValue() {
        return this.type;
    }
}
