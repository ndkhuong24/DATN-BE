package com.example.backend.core.salesCounter.service;

import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.salesCounter.dto.OrderSalesDTO;

import java.util.List;

public interface OrderSalesCounterService {
    ServiceResult<OrderSalesDTO> createOrderSales(OrderSalesDTO orderSalesDTO);

    ServiceResult<OrderSalesDTO> updateOrderSales(OrderSalesDTO orderSalesDTO);

    List<OrderSalesDTO> getAllOrder();

    List<OrderAdminDTO> getAllOrderSalesAdmin(OrderAdminDTO orderAdminDTO);
}
