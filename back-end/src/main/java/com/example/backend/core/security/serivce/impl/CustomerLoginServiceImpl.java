package com.example.backend.core.security.serivce.impl;

import com.example.backend.core.model.Customer;
import com.example.backend.core.security.entity.CustomerLogin;
import com.example.backend.core.security.entity.Users;
import com.example.backend.core.security.repositories.CustomerLoginRepository;
import com.example.backend.core.security.serivce.CustomerLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerLoginServiceImpl implements CustomerLoginService {
    @Autowired
    private CustomerLoginRepository repository;
    @Override
    public CustomerLogin saveCustomer(CustomerLogin customer) {
        return repository.save(customer);
    }

    @Override
    public CustomerLogin findByUsername(String username) {
        return repository.findByUsername(username);
    }

    @Override
    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }
}
