package com.example.backend.core.admin.service;

import com.example.backend.core.model.Customer;

import java.util.Optional;

public interface CustomerAdminService {
    Optional<Customer> findById(String id);
}
