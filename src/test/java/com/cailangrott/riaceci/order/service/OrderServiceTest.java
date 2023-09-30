package com.cailangrott.riaceci.order.service;

import com.cailangrott.riaceci.customer.CustomerModel;
import com.cailangrott.riaceci.customer.dto.FindCustomerByCnpj;
import com.cailangrott.riaceci.customer.enums.CustomerType;
import com.cailangrott.riaceci.customer.service.CustomerService;
import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import com.cailangrott.riaceci.order.Order;
import com.cailangrott.riaceci.order.OrderItem;
import com.cailangrott.riaceci.order.dto.CreateNewOrder;
import com.cailangrott.riaceci.order.dto.GetOrderById;
import com.cailangrott.riaceci.order.dto.GetOrdersByCustomerCnpj;
import com.cailangrott.riaceci.order.repository.OrderRepository;
import com.cailangrott.riaceci.product.Product;
import com.cailangrott.riaceci.product.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderItemService orderItemService;

    @Mock
    private ProductService productService;

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderRepository orderRepository;

    private CustomerModel customer;
    private Product product;
    private Order order;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new CustomerModel();
        product = new Product();
        order = new Order();
        order.setOrderDate(LocalDate.now());
    }

    @Test
    public void calculateTotalOrderValue_success() {
        // Arrange
        OrderItem orderItem1 = new OrderItem();
        orderItem1.setTotalPrice(BigDecimal.TEN);

        OrderItem orderItem2 = new OrderItem();
        orderItem2.setTotalPrice(BigDecimal.ONE);

        order.setOrderItems(new HashSet<>(Arrays.asList(orderItem1, orderItem2)));

        when(orderRepository.findById(anyInt())).thenReturn(Optional.of(order));

        // Act
        BigDecimal result = orderService.calculateTotalOrderValue(1);

        // Assert
        assertEquals(new BigDecimal("11"), result);
    }

    @Test
    public void findOrderById_notFound() {
        // Arrange
        when(orderRepository.findOrderByIdTest(anyInt())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.findOrderById(1));
    }

    @Test
    public void calculateTotalOrderValueFromItems_success() {
        // Arrange
        GetOrderById.OrderItemOutput item1 = new GetOrderById.OrderItemOutput(1, "Item1", 2, new BigDecimal("10"));
        GetOrderById.OrderItemOutput item2 = new GetOrderById.OrderItemOutput(2, "Item2", 1, new BigDecimal("10"));

        List<GetOrderById.OrderItemOutput> items = Arrays.asList(item1, item2);

        // Act
        BigDecimal result = orderService.calculateTotalOrderValue(items);

        // Assert
        assertEquals(new BigDecimal("30"), result);
    }


    @Test
    public void findOrdersByCnpj_notFound() {
        // Arrange
        when(customerService.findCustomerByCnpj(anyString())).thenReturn(null);

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> orderService.findOrdersByCnpj("12345678901234"));
    }

    @Test
    public void findOrdersByCnpj_success() {
        // Arrange
        when(orderRepository.findOrdersByCustomerCnpj(anyString())).thenReturn(Collections.singletonList(order));
        when(customerService.findCustomerByCnpj(anyString())).thenReturn(new FindCustomerByCnpj(1, "Test", "12345678901234", "test@example.com", CustomerType.SUPERMARKET));

        // Act
        GetOrdersByCustomerCnpj result = orderService.findOrdersByCnpj("12345678901234");

        // Assert
        assertNotNull(result);
        assertEquals("12345678901234", result.cnpj());
    }

    @Test
    public void getProductQuantity_success() {
        // Arrange
        CreateNewOrder newOrder = new CreateNewOrder(1, Arrays.asList(new CreateNewOrder.Product(1, 2), new CreateNewOrder.Product(2, 3)));

        // Act
        Integer quantityForProduct1 = orderService.getProductQuantity(newOrder, 1);
        Integer quantityForProduct2 = orderService.getProductQuantity(newOrder, 2);

        // Assert
        assertEquals(2, quantityForProduct1);
        assertEquals(3, quantityForProduct2);
    }

    @Test
    public void getProductIds_success() {
        // Arrange
        CreateNewOrder newOrder = new CreateNewOrder(1, Arrays.asList(new CreateNewOrder.Product(1, 2), new CreateNewOrder.Product(2, 3)));

        // Act
        List<Integer> productIds = orderService.getProductIds(newOrder);

        // Assert
        assertTrue(productIds.contains(1));
        assertTrue(productIds.contains(2));
        assertEquals(2, productIds.size());
    }
}