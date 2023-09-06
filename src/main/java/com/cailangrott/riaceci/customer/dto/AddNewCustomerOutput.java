package com.cailangrott.riaceci.customer.dto;

import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.customer.enums.CustomerType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link CustomerModel}
 */
public record AddNewCustomerOutput(
        @NotNull @Size(max = 255) String name,
        @NotNull @Size(min = 14, max = 14) String cnpj,
        @NotNull @Size(max = 255) @Email String email,
        @NotNull @Size(max = 255) String password,
        @NotNull @Size(max = 255) @Schema(example = "Supermarket", description = "Tipo da empresa") String customerType
) implements Serializable {
}