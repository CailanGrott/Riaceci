package com.cailangrott.riaceci.order.controller;

import com.cailangrott.riaceci.exception.ResourceNotFoundException;
import com.cailangrott.riaceci.order.dto.CreateNewOrder;
import com.cailangrott.riaceci.order.dto.GetOrderById;
import com.cailangrott.riaceci.order.dto.GetOrdersByCustomerCnpj;
import com.cailangrott.riaceci.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/new-order")
    public ResponseEntity<?> addNewOrder(@RequestBody CreateNewOrder newOrder) throws ResourceNotFoundException {
        orderService.addNewOrder(newOrder);
        return new ResponseEntity<>(newOrder, HttpStatus.CREATED);
    }

    @GetMapping("find-order/id/{id}")
    public ResponseEntity<GetOrderById> findOrderById(@PathVariable("id") Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(orderService.findOrderById(id));
    }

    @GetMapping("/find-order-by-cnpj/{cnpj}")
    public ResponseEntity<GetOrdersByCustomerCnpj> getOrdersByCnpj(@PathVariable String cnpj) {
        return ResponseEntity.ok(orderService.findOrdersByCnpj(cnpj));
    }

}
