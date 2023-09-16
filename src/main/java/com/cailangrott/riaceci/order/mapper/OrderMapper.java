package com.cailangrott.riaceci.order.mapper;

import com.cailangrott.riaceci.customer.dto.FindCustomerByCnpj;
import com.cailangrott.riaceci.order.Order;
import com.cailangrott.riaceci.order.dto.GetOrderById;
import com.cailangrott.riaceci.order.dto.GetOrdersByCustomerCnpj;
import com.cailangrott.riaceci.order.dto.GetOrdersByCustomerName;
import com.cailangrott.riaceci.order.repository.OrderInfoTest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


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

    public static GetOrdersByCustomerCnpj mapToGetOrdersByCnpjResponseDto(FindCustomerByCnpj customer, List<Order> orders) {
        List<GetOrdersByCustomerCnpj.OrderDetail> orderDetails = orders.stream()
                .map(order -> {
                    List<GetOrdersByCustomerCnpj.OrderItemOutput> orderItemOutputs = order.getOrderItems().stream()
                            .map(item -> new GetOrdersByCustomerCnpj.OrderItemOutput(
                                    item.getId(),
                                    item.getProduct().getName(),
                                    item.getQuantity(),
                                    item.getProduct().getPrice()
                            ))
                            .collect(Collectors.toList());

                    BigDecimal totalValue = orderItemOutputs.stream()
                            .map(GetOrdersByCustomerCnpj.OrderItemOutput::getTotal)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new GetOrdersByCustomerCnpj.OrderDetail(
                            order.getId(),
                            order.getOrderDate(),
                            orderItemOutputs,
                            totalValue
                    );
                })
                .collect(Collectors.toList());

        return new GetOrdersByCustomerCnpj(
                customer.id(),
                customer.name(),
                customer.cnpj(),
                orderDetails
        );
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
