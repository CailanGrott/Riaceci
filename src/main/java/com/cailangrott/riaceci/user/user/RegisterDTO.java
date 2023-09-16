package com.cailangrott.riaceci.user.user;

import java.io.Serializable;

public record RegisterDTO(
        String login,
        String password,
        UserRole role) implements Serializable {
}
