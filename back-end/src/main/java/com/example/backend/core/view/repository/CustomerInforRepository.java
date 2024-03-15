package com.example.backend.core.view.repository;

import com.example.backend.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerInforRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);
}
