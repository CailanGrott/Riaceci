package com.cailangrott.riaceci.customer.dto;

import com.cailangrott.riaceci.customer.CustomerModel;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;

/**
 * DTO for {@link CustomerModel}
 */
@Builder
public record AddNewCustomerInput(
        Integer id,
        @NotNull @Size(max = 255) String name,
        @NotNull @Size(min = 14, max = 14) String cnpj,
        @NotNull @Size(max = 255) @Email String email,
        @NotNull @Size(max = 255) String customerType) implements Serializable {
    public AddNewCustomerInput(CustomerModel customer) {
        this(customer.getId(), customer.getName(), customer.getCnpj(),
                customer.getEmail(), customer.getCustomerType().getDescription());
    }
}