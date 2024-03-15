package com.example.backend.core.salesCounter.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Customer;
import com.example.backend.core.salesCounter.dto.CustomerSCDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CustomerSCMapper extends EntityMapper<CustomerSCDTO, Customer> {
}
