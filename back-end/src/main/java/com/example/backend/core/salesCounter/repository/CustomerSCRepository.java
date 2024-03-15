package com.example.backend.core.salesCounter.repository;

import com.example.backend.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerSCRepository extends JpaRepository<Customer, Long> {
    Customer findByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
