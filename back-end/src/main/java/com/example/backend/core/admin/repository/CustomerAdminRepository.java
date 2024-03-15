package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerAdminRepository extends JpaRepository<Customer, Long> {
}
