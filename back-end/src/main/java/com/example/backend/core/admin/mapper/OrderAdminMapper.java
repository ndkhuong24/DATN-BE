package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrderAdminMapper extends EntityMapper<OrderAdminDTO, Order> {
}
