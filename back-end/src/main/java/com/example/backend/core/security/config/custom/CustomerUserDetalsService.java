package com.example.backend.core.security.config.custom;

import com.example.backend.core.security.entity.CustomerLogin;
import com.example.backend.core.security.entity.ERole;
import com.example.backend.core.security.repositories.CustomerLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomerUserDetalsService implements UserDetailsService {

    @Autowired
    CustomerLoginRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerLogin customer = repository.findByUsername(username);
        if (customer == null) {
            throw new UsernameNotFoundException("Khong Tim Thay User");
        }
        UserDetails userDetails;
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(ERole.CUSTOMER.name()));
        userDetails = new User(customer.getUsername(), customer.getPassword(), authorities);
        System.out.println(userDetails);
        return userDetails;
    }
}
