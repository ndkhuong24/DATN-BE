package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CustomerAdminMapper extends EntityMapper<CustomerAdminDTO, Customer> {
}
