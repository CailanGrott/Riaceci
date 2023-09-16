package com.cailangrott.riaceci.user.user;

import java.io.Serializable;

public record LoginResponseDTO(
        String token) implements Serializable {
}
