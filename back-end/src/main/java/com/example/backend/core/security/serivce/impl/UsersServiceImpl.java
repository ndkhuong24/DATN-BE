package com.example.backend.core.security.serivce.impl;

import com.example.backend.core.admin.repository.StaffAdminRepository;
import com.example.backend.core.security.entity.Users;
import com.example.backend.core.security.repositories.CustomerLoginRepository;
import com.example.backend.core.security.repositories.UserRepository;
import com.example.backend.core.security.serivce.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private CustomerLoginRepository customerRepository;

    @Autowired
    private StaffAdminRepository staffAdminRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Users findByUsername(String userName) {
        return repository.findByUsername(userName);
    }

    @Override
    public boolean existsByUsername(String userName) {
        return repository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return repository.existsByPhone(phone);
    }

    @Override
    public String findByRole(String role) {
        return repository.findByRole(role);
    }

    @Override
    public Users saveOrUpdate(Users users) {
        return repository.save(users);
    }

    @Override
    public boolean isUser(String username) {
        Users users = repository.findByUsername(username);
        if (users.getRole()==null){
            return false;
        }
        return true;
    }


}
