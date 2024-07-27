package com.example.backend.core.salesCounter.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.CustomerDTO;

public interface CustomerSCService {
    ServiceResult<CustomerDTO> addCustomer(CustomerDTO customerDTO);
}
