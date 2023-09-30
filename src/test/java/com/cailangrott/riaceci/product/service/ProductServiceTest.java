package com.cailangrott.riaceci.product.service;

import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import com.cailangrott.riaceci.product.Product;
import com.cailangrott.riaceci.product.dto.*;
import com.cailangrott.riaceci.product.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void addNewProduct_success() {
        // Arrange
        AddNewProductOutput dto = new AddNewProductOutput("Name", "Description", BigDecimal.valueOf(10), "ImageURL");
        Product product = Product.builder().name("Name").description("Description").price(BigDecimal.valueOf(10.0)).image("ImageURL").build();
        when(productRepository.saveAndFlush(any())).thenReturn(product);

        // Act
        AddNewProductInput result = productService.addNewProduct(dto);

        // Assert
        assertEquals("Name", result.name());
        verify(productRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void findAllProductsById_success() {
        // Arrange
        Product product = Product.builder().name("Name").description("Description").price(BigDecimal.valueOf(10.0)).image("ImageURL").build();
        when(productRepository.findAllById(any())).thenReturn(Collections.singletonList(product));

        // Act
        Iterable<Product> products = productService.findAllProductsById(Collections.singletonList(1));

        // Assert
        assertNotNull(products);
        assertTrue(products.iterator().hasNext());
    }

    @Test
    public void findAllProductsById_notFound() {
        // Arrange
        when(productRepository.findAllById(any())).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.findAllProductsById(Collections.singletonList(1)));
    }

    @Test
    public void findAllProducts_success() {
        // Arrange
        Product product = Product.builder().name("Name").description("Description").price(BigDecimal.valueOf(10.0)).image("ImageURL").build();
        when(productRepository.findAll()).thenReturn(Collections.singletonList(product));

        // Act
        Iterable<FindAllProductsOutput> products = productService.findAllProducts();

        // Assert
        assertNotNull(products);
        assertTrue(products.iterator().hasNext());
    }

    @Test
    public void deleteProduct_success() {
        // Arrange
        Product product = Product.builder().name("Name").description("Description").price(BigDecimal.valueOf(10.0)).image("ImageURL").build();
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // Act
        productService.deleteProduct(1);

        // Assert
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    public void deleteProduct_notFound() {
        // Arrange
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(1));
    }

    @Test
    public void updateProduct_success() {
        // Arrange
        Product product = Product.builder().name("OldName").description("OldDescription").price(BigDecimal.valueOf(5.0)).image("OldImageURL").build();
        UpdateProductOutput updateProductOutput = new UpdateProductOutput("NewName", "NewDescription", BigDecimal.valueOf(10.0), "NewImageURL");
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // Act
        productService.updateProduct(1, updateProductOutput);

        // Assert
        verify(productRepository, times(1)).updateProductById(any(), any(), any(), any(), anyInt());
    }

    @Test
    public void updateProduct_notFound() {
        // Arrange
        UpdateProductOutput updateProductOutput = new UpdateProductOutput("NewName", "NewDescription", BigDecimal.valueOf(10.0), "NewImageURL");
        when(productRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.updateProduct(1, updateProductOutput));
    }
}
