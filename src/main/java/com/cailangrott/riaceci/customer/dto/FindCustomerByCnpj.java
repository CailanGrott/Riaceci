package com.cailangrott.riaceci.customer.dto;

import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.customer.enums.CustomerType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link CustomerModel}
 */
@Builder
public record FindCustomerByCnpj(
        Integer id,
        @NotNull @Size(max = 255) String name,
        @NotNull @Size(min = 14, max = 14) String cnpj,
        @NotNull @Size(max = 255) @Email String email,
        @NotNull CustomerType customerType) implements Serializable {
}