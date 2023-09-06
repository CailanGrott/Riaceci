package com.cailangrott.riaceci.order.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

@Builder
public record GetOrderById(
        Integer orderId,
        LocalDate orderDate,
        Integer customerId,
        String customerName,
        String cnpj,
        String email,
        String customerType,
        Collection<OrderItemOutput> items,
        BigDecimal totalValue  // Novo campo para o valor total
) {
    @Builder
    public record OrderItemOutput(
            Integer id,
            String name,
            Integer quantity,
            BigDecimal price
    ) {
        public BigDecimal getTotal() {  // Calcula o total por item
            return price.multiply(new BigDecimal(quantity));
        }
    }
}