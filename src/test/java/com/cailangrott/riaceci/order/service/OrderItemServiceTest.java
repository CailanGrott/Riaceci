package com.cailangrott.riaceci.order.service;

import com.cailangrott.riaceci.order.Order;
import com.cailangrott.riaceci.order.repository.OrderItemRepository;
import com.cailangrott.riaceci.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

class OrderItemServiceTest {

    @InjectMocks
    private OrderItemService orderItemService;

    @Mock
    private OrderItemRepository orderItemRepository;

    private Product product;
    private Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Product();
        product.setPrice(BigDecimal.TEN);
        order = new Order();
    }

    @Test
    public void calculateTotalForOrderItem_success() {
        // Act
        BigDecimal result = orderItemService.calculateTotalForOrderItem(product, 2);

        // Assert
        assertEquals(new BigDecimal("20"), result);
    }

    @Test
    public void associarPedidosAoProduto_success() {
        // Act
        orderItemService.associarPedidosAoProduto(product, 2, order);

        // Assert
        verify(orderItemRepository, times(1)).save(any());
    }
}