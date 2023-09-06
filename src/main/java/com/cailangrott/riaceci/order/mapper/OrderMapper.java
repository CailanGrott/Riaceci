package com.cailangrott.riaceci.order.mapper;

import com.cailangrott.riaceci.order.dto.GetOrderById;
import com.cailangrott.riaceci.order.repository.OrderInfoTest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class OrderMapper {

    public static GetOrderById mapToGetOrderById(OrderInfoTest order) {
        return GetOrderById.builder()
                .orderId(order.getId())
                .orderDate(order.getOrderDate())
                .customerName(order.getCustomer().getName())
                .customerId(order.getCustomer().getId())
                .cnpj(order.getCustomer().getCnpj())
                .email(order.getCustomer().getEmail())
                .customerType(String.valueOf(order.getCustomer().getCustomerType()))
                .items(buildOrderItemsOutput(order))
                .build();
    }

    private static Collection<GetOrderById.OrderItemOutput> buildOrderItemsOutput(OrderInfoTest order) {
        return order.getOrderItems().stream()
                .map(orderItemInfo -> GetOrderById.OrderItemOutput.builder()
                        .id(orderItemInfo.getProduct().getId())
                        .name(orderItemInfo.getProduct().getName())
                        .quantity(orderItemInfo.getQuantity())
                        .price(orderItemInfo.getProduct().getPrice())
                        .build())
                .toList();
    }
}
