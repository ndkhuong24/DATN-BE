package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Customer;
import com.example.backend.core.view.dto.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CustomerMapper extends EntityMapper<CustomerDTO, Customer> {
}
