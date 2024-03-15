package com.example.backend.core.salesCounter.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.OrderDetail;
import com.example.backend.core.salesCounter.dto.OrderSalesDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrderSalesCounterDetailsMapper extends EntityMapper<OrderSalesDetailDTO, OrderDetail> {
}
