package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.view.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {
    ServiceResult<CustomerDTO> updateInforCustomer(CustomerDTO customerDTO, Customer customer);
    List<CustomerDTO> findByPhoneLike(String phone);
}
