package com.cailangrott.riaceci.customer.repository;

import com.cailangrott.riaceci.customer.CustomerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Integer> {
    @Transactional
    @Modifying
    @Query(value =
            """
                    UPDATE riaceci.customer c
                    SET c.name = :name,
                        c.cnpj = :cnpj,
                        c.email = :email,
                        c.customer_type = :customer_type
                    WHERE c.customer_id = :customerId
                    """, nativeQuery = true)
    void updateCustomerById(@Nullable @Param("name") String name, @Nullable @Param("cnpj") String cnpj,
                            @Nullable @Param("email") String email, @Nullable @Param("customer_type") String customerType,
                            @Param("customerId") Integer customerId);
}
