package com.cailangrott.riaceci.user.services;

import com.cailangrott.riaceci.user.repositories.UserRepository;
import com.cailangrott.riaceci.user.user.RegisterDTO;
import com.cailangrott.riaceci.user.user.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;


    public User registerUser(RegisterDTO data) {
        if (repository.findByLogin(data.login()) != null) {
            throw new UserAlreadyExistsException("User already exists with login: " + data.login());
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role());
        repository.save(newUser);
        return newUser;
    }

    public UserDetails findByLogin(String login) {
        return repository.findByLogin(login);
    }

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }
}