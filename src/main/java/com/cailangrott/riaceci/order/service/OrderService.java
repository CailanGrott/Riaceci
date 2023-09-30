package com.cailangrott.riaceci.order.service;

import com.cailangrott.riaceci.customer.dto.FindCustomerByCnpj;
import com.cailangrott.riaceci.customer.service.CustomerService;
import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import com.cailangrott.riaceci.order.Order;
import com.cailangrott.riaceci.order.OrderItem;
import com.cailangrott.riaceci.order.dto.CreateNewOrder;
import com.cailangrott.riaceci.order.dto.GetOrderById;
import com.cailangrott.riaceci.order.dto.GetOrdersByCustomerCnpj;
import com.cailangrott.riaceci.order.mapper.OrderMapper;
import com.cailangrott.riaceci.order.repository.OrderRepository;
import com.cailangrott.riaceci.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.cailangrott.riaceci.order.mapper.OrderMapper.mapToGetOrdersByCnpjResponseDto;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final CustomerService customerService;
    private final OrderRepository orderRepository;

    public void addNewOrder(CreateNewOrder newOrder) throws ResourceNotFoundException {
        var customer = customerService.findCustomerById(newOrder.customerId());
        var products = productService.findAllProductsById(getProductIds(newOrder));

        var order = orderRepository.saveAndFlush(Order.builder()
                .orderDate(LocalDate.now())
                .customer(customer)
                .build());

        products.forEach(product ->
                orderItemService.associarPedidosAoProduto(product,
                        getProductQuantity(newOrder, product.getId()), order));

        BigDecimal totalOrderValue = calculateTotalOrderValue(order.getId());
        order.setTotalValue(totalOrderValue);
        orderRepository.save(order);
    }

    public BigDecimal calculateTotalOrderValue(Integer orderId) throws ResourceNotFoundException {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "Pedido não encontrado",
                        "Nenhum pedido encontrado com o ID: " + orderId
                ));

        return order.getOrderItems().stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal calculateTotalOrderValue(Collection<GetOrderById.OrderItemOutput> items) {
        return items.stream()
                .map(GetOrderById.OrderItemOutput::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public GetOrderById findOrderById(Integer id) throws ResourceNotFoundException {
        var orderDto = orderRepository.findOrderByIdTest(id)
                .map(OrderMapper::mapToGetOrderById)
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "Pedido não encontrado",
                        "Nenhum pedido encontrado com o ID: " + id));

        BigDecimal totalValue = calculateTotalOrderValue(orderDto.items());

        return new GetOrderById(
                orderDto.orderId(),
                orderDto.orderDate(),
                orderDto.customerId(),
                orderDto.customerName(),
                orderDto.cnpj(),
                orderDto.email(),
                orderDto.customerType(),
                orderDto.items(),
                totalValue);
    }

    public GetOrdersByCustomerCnpj findOrdersByCnpj(String cnpj) {
        List<Order> orders = orderRepository.findOrdersByCustomerCnpj(cnpj);

        FindCustomerByCnpj customer = Optional.ofNullable(customerService.findCustomerByCnpj(cnpj))
                .orElseThrow(() -> new ResourceNotFoundException(
                        HttpStatus.NOT_FOUND,
                        "Cliente não encontrado",
                        "Nenhum cliente encontrado com o CNPJ: " + cnpj
                ));

        return mapToGetOrdersByCnpjResponseDto(customer, orders);
    }

    Integer getProductQuantity(CreateNewOrder order, Integer id) {
        return order.products().stream()
                .filter(product -> product.id().equals(id))
                .map(CreateNewOrder.Product::quantity)
                .findFirst()
                .orElse(1);
    }

    List<Integer> getProductIds(CreateNewOrder order) {
        return order.products().stream()
                .map(CreateNewOrder.Product::id)
                .toList();
    }
}
