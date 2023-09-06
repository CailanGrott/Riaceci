package com.cailangrott.riaceci.product.repository;

import com.cailangrott.riaceci.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Transactional
    @Modifying
    @Query(value =
            """
                    UPDATE riaceci.product p
                    SET p.name = :name,
                        p.description = :description,
                        p.price = :price,
                        p.image = :image
                    WHERE p.product_id = :productId
                    """, nativeQuery = true)
    void updateProductById(@Nullable @Param("name") String name, @Nullable @Param("description") String description,
                           @Nullable @Param("price") BigDecimal price, @Nullable @Param("image") String image,
                           @Param("productId") Integer productId);
}