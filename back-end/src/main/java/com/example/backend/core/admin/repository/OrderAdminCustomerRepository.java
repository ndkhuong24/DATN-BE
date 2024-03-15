package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.OrderAdminDTO;

import java.util.List;
import java.util.Map;

public interface OrderAdminCustomerRepository {

    List<OrderAdminDTO> getAllOrderAdmin(OrderAdminDTO orderAdminDTO);

    Map<String, Integer> totalStatusOrder(OrderAdminDTO orderAdminDTO);

    List<OrderAdminDTO> getAllOrderSalesAdmin(OrderAdminDTO orderAdminDTO);
}
