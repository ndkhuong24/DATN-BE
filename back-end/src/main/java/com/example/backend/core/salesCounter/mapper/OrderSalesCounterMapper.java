package com.example.backend.core.salesCounter.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Order;
import com.example.backend.core.salesCounter.dto.OrderSalesDTO;
import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface OrderSalesCounterMapper extends EntityMapper<OrderSalesDTO, Order> {

}
