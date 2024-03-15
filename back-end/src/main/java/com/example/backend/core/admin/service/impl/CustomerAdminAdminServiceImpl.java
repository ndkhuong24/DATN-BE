package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.repository.CustomerAdminRepository;
import com.example.backend.core.admin.service.CustomerAdminService;
import com.example.backend.core.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CustomerAdminAdminServiceImpl implements CustomerAdminService {
    @Autowired
    private CustomerAdminRepository repository;
    @Override
    public Optional<Customer> findById(String id) {
        return repository.findById(Long.valueOf(id));
    }
}
