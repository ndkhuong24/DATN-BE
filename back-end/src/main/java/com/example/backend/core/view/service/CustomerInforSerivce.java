package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.view.dto.CustomerDTO;

public interface CustomerInforSerivce {
    ServiceResult<CustomerDTO> updatePassword(CustomerDTO customerDTO, Customer customer);
    ServiceResult<CustomerDTO> resetPassword(CustomerDTO customerDTO);
}
