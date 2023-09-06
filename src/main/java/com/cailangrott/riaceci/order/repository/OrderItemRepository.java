package com.cailangrott.riaceci.order.repository;

import com.cailangrott.riaceci.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}