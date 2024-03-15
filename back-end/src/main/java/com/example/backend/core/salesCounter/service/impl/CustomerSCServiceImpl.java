package com.example.backend.core.salesCounter.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.salesCounter.repository.CustomerSCRepository;
import com.example.backend.core.salesCounter.service.CustomerSCService;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.mapper.CustomerMapper;
import com.example.backend.core.view.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CustomerSCServiceImpl implements CustomerSCService {
    @Autowired
    private CustomerSCRepository repository;
    @Autowired
    private CustomerMapper mapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CustomerService service;
    @Override
    public ServiceResult<CustomerDTO> addCustomer(CustomerDTO customerDTO) {
        ServiceResult<CustomerDTO> result = new ServiceResult<>();
        if (repository.existsByPhone(customerDTO.getPhone())){
            result.setMessage("The phone is existed");
            result.setData(customerDTO);
            result.setStatus(HttpStatus.OK);
            return result;
        }
        Customer customer = mapper.toEntity(customerDTO);
        customer.setCode("KH" + Instant.now().getEpochSecond());
        customer.setEmail("emailCustomer" + Instant.now().getEpochSecond() + "@gmail.com" );
        customer.setPassword(encoder.encode("123456"));
        customer.setStatus(0);
        customer.setIdel(0);
        repository.save(customer);
        result.setMessage("done");
        result.setData(customerDTO);
        result.setStatus(HttpStatus.OK);
        return result;
    }

}
