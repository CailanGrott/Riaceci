package com.cailangrott.riaceci.customer;

import com.cailangrott.riaceci.customer.enums.CustomerType;
import com.cailangrott.riaceci.order.Order;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Setter
@Table(name = "customer", schema = "riaceci")
public class CustomerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id", nullable = false)
    private Integer id;

    @Size(max = 255)
    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 14)
    @NotNull
    @Column(name = "cnpj", nullable = false, length = 14)
    private String cnpj;

    @Size(max = 255)
    @NotNull
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 255)
    @NotNull
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "customer_type")
    @Enumerated(EnumType.STRING)
    private CustomerType customerType;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
    private Set<Order> orders = new LinkedHashSet<>();
}