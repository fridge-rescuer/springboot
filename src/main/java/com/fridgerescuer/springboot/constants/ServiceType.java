package com.fridgerescuer.springboot.constants;

public enum ServiceType {
    RECIPE(1),
    MEMBER(2),
    COMMENT(3),
    INGREDIENT(4);

    private final int value;

    ServiceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
