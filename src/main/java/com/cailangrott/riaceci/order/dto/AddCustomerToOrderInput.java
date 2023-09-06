package com.cailangrott.riaceci.order.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

import com.cailangrott.riaceci.order.Order;
import com.cailangrott.riaceci.customer.CustomerModel;

/**
 * DTO for {@link Order}
 */
public record AddCustomerToOrderInput(
        Integer id,
        @NotNull AddCustomerToOrderInput.CustomerDto customer) implements Serializable {
    /**
     * DTO for {@link CustomerModel}
     */
    public record CustomerDto(
            Integer id) implements Serializable {
    }
}