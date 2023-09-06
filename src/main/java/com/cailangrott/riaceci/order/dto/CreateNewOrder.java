package com.cailangrott.riaceci.order.dto;

import java.util.Collection;

public record CreateNewOrder(Integer customerId, Collection<Product> products) {
    public record Product(Integer id, Integer quantity) {
    }
}