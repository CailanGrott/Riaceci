package com.cailangrott.riaceci.product.service;

import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import com.cailangrott.riaceci.product.Product;
import com.cailangrott.riaceci.product.dto.AddNewProductInput;
import com.cailangrott.riaceci.product.dto.AddNewProductOutput;
import com.cailangrott.riaceci.product.dto.FindAllProductsOutput;
import com.cailangrott.riaceci.product.dto.UpdateProductOutput;
import com.cailangrott.riaceci.product.mapper.ProductMapper;
import com.cailangrott.riaceci.product.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;

import java.util.Collection;
import java.util.List;

import static com.cailangrott.riaceci.product.mapper.ProductMapper.*;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public AddNewProductInput addNewProduct(AddNewProductOutput addNewProductOutput) {
        var productReturn = productRepository.saveAndFlush(mapDtoToProduct(addNewProductOutput));
        return mapProductToNewProductDTO(productReturn);
    }

    public Iterable<Product> findAllProductsById(Collection<Integer> productIds) throws ResourceNotFoundException {
        final List<Product> products = productRepository.findAllById(productIds);

        if (!products.isEmpty()) {
            return products;
        }

        throw new ResourceNotFoundException("");
    }

    public Iterable<FindAllProductsOutput> findAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::mapProductToFindAllDTO)
                .toList();
    }

    public void deleteProduct(Integer id) throws ResourceAccessException {
        var productReturn = productRepository.findById(id).orElseThrow();
        productRepository.delete(productReturn);
    }

    public void updateProduct(Integer id, UpdateProductOutput updateProductOutput) throws ResourceNotFoundException {
        var productReturn = validateProductAttributesUpdate(updateProductOutput,
                productRepository.findById(id).orElseThrow());
        productRepository.updateProductById(productReturn.getName(),
                productReturn.getDescription(),
                productReturn.getPrice(),
                productReturn.getImage(),
                id);
    }

    private Product validateProductAttributesUpdate(UpdateProductOutput updateProductOutput, Product product) {
        return Product.builder()
                .name(updateProductOutput.name().isBlank() ? product.getName() : updateProductOutput.name())
                .description(updateProductOutput.description().isBlank() ? product.getDescription() : updateProductOutput.description())
                .price(updateProductOutput.price() == null ? product.getPrice() : updateProductOutput.price())
                .image(updateProductOutput.image().isBlank() ? product.getImage() : updateProductOutput.image())
                .build();
    }
}
