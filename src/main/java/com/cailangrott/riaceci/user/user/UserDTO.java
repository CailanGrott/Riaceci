package com.cailangrott.riaceci.user.user;

import lombok.Builder;

@Builder
public record UserDTO(
        String login,
        UserRole role) {
}
