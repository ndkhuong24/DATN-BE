package com.example.backend.core.admin.service;

import com.example.backend.core.model.OrderHistory;

import java.util.List;

public interface OrderHistoryAdminService {

    List<OrderHistory> getAllOrderHistoryByOrder(Long idOrder);

}
