package com.example.backend.core.salesCounter.repository;

import com.example.backend.core.admin.dto.OrderAdminDTO;

import java.util.List;

public interface OrderSalesCustomRepository {
    public List<OrderAdminDTO> getAllOrderAdmin(OrderAdminDTO orderAdminDTO);
}
