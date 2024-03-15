package com.example.backend.core.security.serivce;

import com.example.backend.core.security.entity.CustomerLogin;

public interface CustomerLoginService {
    CustomerLogin saveCustomer(CustomerLogin customerLogin);
    CustomerLogin findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByPhone(String email);
}
