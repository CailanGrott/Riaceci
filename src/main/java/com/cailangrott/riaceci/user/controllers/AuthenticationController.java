package com.cailangrott.riaceci.user.controllers;

import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.customer.repository.CustomerRepository;
import com.cailangrott.riaceci.user.security.TokenService;
import com.cailangrott.riaceci.user.services.UserService;
import com.cailangrott.riaceci.user.user.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final TokenService tokenService;
    private final CustomerRepository customerRepository; // Certifique-se de injetar o CustomerRepository aqui

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = authenticationManager.authenticate(usernamePassword);

        Object principal = auth.getPrincipal();
        if (principal instanceof User) {
            User user = (User) principal;
            String token;
            if (user.getRole() == UserRole.ADMIN) {
                token = tokenService.generateToken(user);
            } else {
                CustomerModel customer = customerRepository.findCustomerByCnpj(user.getUsername()); // Adicionando a busca do customer pelo CNPJ (que é o username do User)
                token = tokenService.generateTokenForCustomer(customer);
            }
            return ResponseEntity.ok(new LoginResponseDTO(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed");
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data) {
        userService.registerUser(data);
        return ResponseEntity.ok().build();
    }
}
