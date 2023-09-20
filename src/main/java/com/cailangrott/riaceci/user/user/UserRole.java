package com.cailangrott.riaceci.user.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public static UserRole fromStringIgnoreCase(String description) {
        for (UserRole type : UserRole.values()) {
            if (type.description.equalsIgnoreCase(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant " + description);
    }
}
