package com.cailangrott.riaceci.user.user;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    USER("USER");

    private final String role;

    UserRole(String role){
        this.role = role;
    }

}
