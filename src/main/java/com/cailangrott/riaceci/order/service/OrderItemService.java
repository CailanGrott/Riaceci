package com.cailangrott.riaceci.order.service;

import com.cailangrott.riaceci.order.Order;
import com.cailangrott.riaceci.order.OrderItem;
import com.cailangrott.riaceci.order.repository.OrderItemRepository;
import com.cailangrott.riaceci.product.Product;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemService(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    public BigDecimal calculateTotalForOrderItem(Product product, Integer productQuantity) {
        return product.getPrice().multiply(new BigDecimal(productQuantity));
    }

    public void associarPedidosAoProduto(Product product, Integer productQuantity, Order order) {
        BigDecimal totalPrice = calculateTotalForOrderItem(product, productQuantity);

        orderItemRepository.save(OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(productQuantity)
                .totalPrice(totalPrice)
                .build());
    }
}