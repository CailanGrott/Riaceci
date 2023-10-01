package com.cailangrott.riaceci.user.mapper;

import com.cailangrott.riaceci.user.user.User;
import com.cailangrott.riaceci.user.user.UserDTO;

public class UserMapper {
    public static UserDTO mapToDTO(User user) {
        return UserDTO.builder()
                .login(user.getLogin())
                .role(user.getRole())
                .build();
    }
}
