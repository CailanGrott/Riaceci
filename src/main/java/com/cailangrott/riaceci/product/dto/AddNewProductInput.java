package com.cailangrott.riaceci.product.dto;

import com.cailangrott.riaceci.product.Product;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link Product}
 */
@Builder
public record AddNewProductInput(
        Integer id,
        @NotNull @Size(max = 255) String name,
        @NotNull @Size(max = 255) String description,
        @NotNull BigDecimal price,
        @NotNull String image) implements Serializable {
    public AddNewProductInput(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImage());
    }
}