package com.example.backend.core.salesCounter.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.salesCounter.dto.OrderSalesDTO;
import com.example.backend.core.salesCounter.dto.OrderSalesDetailDTO;

public interface OrderSalesCounterDetailService {
    ServiceResult<OrderSalesDetailDTO> createrOrderDetailSales(OrderSalesDetailDTO orderSalesDetailDTO);
}
