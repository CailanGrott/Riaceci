package com.cailangrott.riaceci.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll()
                        //USER
                        .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()

                        //CUSTOMER
                        .requestMatchers(HttpMethod.POST, "/customer/new-customer").permitAll()
                        .requestMatchers(HttpMethod.GET, "/customer/find-customers").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/customer/update-customer/id/{id}").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.DELETE, "/customer/delete-customer/id/{id}").hasRole("ADMIN")

                        //ORDER
                        .requestMatchers(HttpMethod.POST, "/orders/new-order").hasRole("CUSTOMER")
                        .requestMatchers(HttpMethod.GET, "/orders/find-order/id/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/orders/find-order-by-cnpj/{cnpj}").hasRole("ADMIN")

                        //PRODUCT
                        .requestMatchers(HttpMethod.POST, "/products/new-product").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/products/find-products").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/products/update-product/id/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/products/delete-product/id/{id}").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return (web) -> web.ignoring().requestMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }
}
