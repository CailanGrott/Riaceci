package com.cailangrott.riaceci.order.dto;

import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.order.Order;
import com.cailangrott.riaceci.product.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link Order}
 */
public record OrderResponseDto(Integer id, @NotNull OrderResponseDto.CustomerDto1 customer, @NotNull OrderResponseDto.ProductDto product,
                               @NotNull Integer quantity, @NotNull LocalDate orderDate) implements Serializable {
    /**
     * DTO for {@link CustomerModel}
     */
    public record CustomerDto1(Integer id, @NotNull @Size(max = 255) String name, @NotNull @Size(max = 14) String cnpj,
                               @NotNull @Size(max = 255) String email,
                               @NotNull @Size(max = 255) String customerType) implements Serializable {
    }

    /**
     * DTO for {@link Product}
     */
    public record ProductDto(Integer id, @NotNull @Size(max = 255) String name,
                             @NotNull @Size(max = 255) String description,
                             @NotNull BigDecimal price) implements Serializable {
    }
}