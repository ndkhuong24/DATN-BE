package com.example.backend.core.security.repositories;

import com.example.backend.core.security.entity.CustomerLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerLoginRepository extends JpaRepository<CustomerLogin, Long> {
    CustomerLogin findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
}
