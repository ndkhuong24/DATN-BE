package com.example.backend.core.security.config.custom;

import com.example.backend.core.security.entity.CustomerLogin;
import com.example.backend.core.security.repositories.CustomerLoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetalsService implements UserDetailsService {
    @Autowired
    CustomerLoginRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerLogin customer = repository.findByUsername(username);
        if (customer==null){
            throw  new UsernameNotFoundException("Khong Tim Thay User");
        }
        System.out.println(CustomerUserDetails.mapCustomerToUserDetail(customer));
        return  CustomerUserDetails.mapCustomerToUserDetail(customer);
    }
}
