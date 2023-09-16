package com.cailangrott.riaceci.user.user;

import java.io.Serializable;

public record AuthenticationDTO(
        String login,
        String password) implements Serializable {
}
