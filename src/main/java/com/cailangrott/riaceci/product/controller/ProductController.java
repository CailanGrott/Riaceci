package com.cailangrott.riaceci.product.controller;

import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import com.cailangrott.riaceci.product.dto.AddNewProductInput;
import com.cailangrott.riaceci.product.dto.AddNewProductOutput;
import com.cailangrott.riaceci.product.dto.FindAllProductsOutput;
import com.cailangrott.riaceci.product.dto.UpdateProductOutput;
import com.cailangrott.riaceci.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<AddNewProductInput> addNewProduct(@RequestBody AddNewProductOutput addNewProductOutput) {
        var productReturn = productService.addNewProduct(addNewProductOutput);
        return new ResponseEntity<>(productReturn, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<FindAllProductsOutput>> findAllProducts() {
        Iterable<FindAllProductsOutput> findAllProductsDTO = productService.findAllProducts();
        return new ResponseEntity<>(findAllProductsDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProduct(@PathVariable("id") Integer id,
                                              @RequestBody UpdateProductOutput updateProductOutput) {
        productService.updateProduct(id, updateProductOutput);
        return ResponseEntity.ok().build();
    }
}
