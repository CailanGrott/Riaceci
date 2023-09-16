package com.cailangrott.riaceci.order.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
@Builder
public record GetOrdersByCustomerCnpj(
        Integer customerId,
        String customerName,
        String cnpj,
        Collection<OrderDetail> orders) {

    public record OrderDetail(
            Integer orderId,
            LocalDate orderDate,
            Collection<OrderItemOutput> items,
            BigDecimal totalValue) {
    }

    public record OrderItemOutput(
            Integer id,
            String name,
            Integer quantity,
            BigDecimal price) {

        public BigDecimal getTotal() {
            return price.multiply(new BigDecimal(quantity));
        }
    }
}