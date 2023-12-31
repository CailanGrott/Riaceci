package com.cailangrott.riaceci.order.repository;

import com.cailangrott.riaceci.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where o.id = ?1")
    Optional<OrderInfoTest> findOrderByIdTest(Integer id);

    List<Order> findOrdersByCustomerCnpj(String cnpj);

    List<Order> findOrdersByCustomerId(Integer customerId);
}