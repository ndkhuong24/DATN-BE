package com.example.backend.core.view.repository;

import com.example.backend.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByCode(String code);
    List<Customer> findByPhoneLike(String phone);
    Customer findByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
}
