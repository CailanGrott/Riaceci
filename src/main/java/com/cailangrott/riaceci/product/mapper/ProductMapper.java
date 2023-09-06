package com.cailangrott.riaceci.product.mapper;

import com.cailangrott.riaceci.product.Product;
import com.cailangrott.riaceci.product.dto.AddNewProductInput;
import com.cailangrott.riaceci.product.dto.AddNewProductOutput;
import com.cailangrott.riaceci.product.dto.FindAllProductsOutput;

public class ProductMapper {
    public static AddNewProductInput mapProductToNewProductDTO(Product product) {
        return AddNewProductInput.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
    }

    public static FindAllProductsOutput mapProductToFindAllDTO(Product product) {
        return FindAllProductsOutput.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .image(product.getImage())
                .build();
    }

    public static Product mapDtoToProduct(AddNewProductInput addNewProductInput) {
        return Product.builder()
                .id(addNewProductInput.id())
                .name(addNewProductInput.name())
                .description(addNewProductInput.description())
                .price(addNewProductInput.price())
                .image(addNewProductInput.image())
                .build();
    }

    public static Product mapDtoToProduct(AddNewProductOutput addNewProductOutput) {
        return Product.builder()
                .name(addNewProductOutput.name())
                .description(addNewProductOutput.description())
                .price(addNewProductOutput.price())
                .image(addNewProductOutput.image())
                .build();
    }
}
