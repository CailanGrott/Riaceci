package com.cailangrott.riaceci.order.repository;

import com.cailangrott.riaceci.customer.enums.CustomerType;
import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.order.Order;
import com.cailangrott.riaceci.order.OrderItem;
import com.cailangrott.riaceci.product.Product;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

/**
 * Projection for {@link Order}
 */
public interface OrderInfoTest {
    Integer getId();

    LocalDate getOrderDate();

    CustomerInfo getCustomer();

    Set<OrderItemInfo> getOrderItems();

    /**
     * Projection for {@link CustomerModel}
     */
    interface CustomerInfo {
        Integer getId();

        String getName();

        String getCnpj();

        String getEmail();

        CustomerType getCustomerType();
    }

    /**
     * Projection for {@link OrderItem}
     */
    interface OrderItemInfo {
        Integer getId();

        Integer getQuantity();

        ProductInfo getProduct();

        /**
         * Projection for {@link Product}
         */
        interface ProductInfo {
            Integer getId();

            String getDescription();

            String getName();

            BigDecimal getPrice();
        }
    }
}