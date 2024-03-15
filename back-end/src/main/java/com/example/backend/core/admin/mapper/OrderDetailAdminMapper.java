package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.OrderDetailAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrderDetailAdminMapper extends EntityMapper<OrderDetailAdminDTO, OrderDetail> {
}
