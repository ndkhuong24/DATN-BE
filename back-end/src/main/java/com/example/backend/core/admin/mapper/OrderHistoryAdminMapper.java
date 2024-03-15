package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.OrderHistoryAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.OrderHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrderHistoryAdminMapper extends EntityMapper<OrderHistoryAdminDTO, OrderHistory> {
}
