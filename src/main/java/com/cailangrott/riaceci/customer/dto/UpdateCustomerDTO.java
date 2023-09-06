package com.cailangrott.riaceci.customer.dto;

import com.cailangrott.riaceci.customer.enums.CustomerType;
import com.cailangrott.riaceci.customer.CustomerModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.lang.Nullable;

import java.io.Serializable;

/**
 * DTO for {@link CustomerModel}
 */
public record UpdateCustomerDTO(
        @NotNull @Size(max = 255) String name,
        @NotNull @Size(min = 14, max = 14) String cnpj,
        @NotNull @Size(max = 255) @Email String email,
        @Nullable CustomerType customerType) implements Serializable {
}