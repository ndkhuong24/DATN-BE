package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.OrderHistory;
import com.example.backend.core.view.dto.OrderHistoryDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrderHistoryMapper extends EntityMapper<OrderHistoryDTO, OrderHistory> {
}
