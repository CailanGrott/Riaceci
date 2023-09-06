package com.cailangrott.riaceci.customer.enums;

import lombok.Getter;

@Getter
public enum CustomerType {
    SUPERMARKET("Supermarket"),
    STORE("Store"),
    CONVENIENCE_STORE("Convenience_Store"),
    COFFEE_SHOP("Coffee_Shop"),
    BAKERY("Bakery"),
    PASTRY_SHOP("Pastry_Shop"),
    OTHERS("Others");

    private final String description;

    CustomerType(String description) {
        this.description = description;
    }

    public static CustomerType fromStringIgnoreCase(String description) {
        for (CustomerType type : CustomerType.values()) {
            if (type.description.equalsIgnoreCase(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + description);
    }
}