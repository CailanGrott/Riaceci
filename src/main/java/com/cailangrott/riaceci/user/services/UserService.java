package com.cailangrott.riaceci.user.services;

import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import com.cailangrott.riaceci.user.mapper.UserMapper;
import com.cailangrott.riaceci.user.repositories.UserRepository;
import com.cailangrott.riaceci.user.user.RegisterDTO;
import com.cailangrott.riaceci.user.user.User;
import com.cailangrott.riaceci.user.user.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public static class UserAlreadyExistsException extends RuntimeException {
        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    public List<UserDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(UserMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteUserByLogin(String login) throws ResourceNotFoundException {
        User userReturn = (User) repository.findUserByLogin(login)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado",
                        "Nenhum cliente encontrado com o ID: " + login));

        repository.delete(userReturn);
    }
}