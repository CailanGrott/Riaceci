package com.cailangrott.riaceci.user.services;

import com.cailangrott.riaceci.customer.repository.CustomerRepository;
import com.cailangrott.riaceci.user.repositories.UserRepository;
import com.cailangrott.riaceci.user.user.User;
import com.cailangrott.riaceci.user.user.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AuthorizationService implements UserDetailsService {
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(userRepository.findByLogin(username))
                .orElseGet(() ->
                        Optional.ofNullable(customerRepository.findUsernameByCnpj(username))
                                .map(customer -> new User(customer.getCnpj(), customer.getPassword(), customer.getRole()))
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with username or cnpj: " + username))
                );
    }
}
