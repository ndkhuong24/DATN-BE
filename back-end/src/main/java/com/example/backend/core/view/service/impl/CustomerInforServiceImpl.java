package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.mapper.CustomerMapper;
import com.example.backend.core.view.repository.CustomerInforRepository;
import com.example.backend.core.view.service.CustomerInforSerivce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class CustomerInforServiceImpl implements CustomerInforSerivce {

    @Autowired
    private CustomerInforRepository repository;

    private ServiceResult<CustomerDTO> result = new ServiceResult<>();
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private PasswordEncoder encoder;

    @Override
    public ServiceResult<CustomerDTO> updatePassword(CustomerDTO customerDTO, Customer customer) {
        if (encoder.matches(customerDTO.getPassword(), customer.getPassword())) {
            customer.setPassword(encoder.encode(customerDTO.getNewPass()));
            customer.setUpdateDate(Instant.now());
            this.repository.save(customer);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(customerMapper.toDto(customer));
        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("mat khau cu khong chinh xac");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<CustomerDTO> resetPassword(CustomerDTO customerDTO) {
        Customer customer = repository.findByEmail(customerDTO.getEmail());
        if (customer != null){
            customer.setPassword(encoder.encode(customerDTO.getNewPass()));
            customer.setUpdateDate(Instant.now());
            this.repository.save(customer);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(customerMapper.toDto(customer));
            return result;
        }
        result.setStatus(HttpStatus.BAD_REQUEST);
        result.setMessage("Sua  khong thanh cong");
        result.setData(null);
        return result;
    }
}
