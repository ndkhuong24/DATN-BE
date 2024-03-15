package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.repository.OrderHistoryAdminRepository;
import com.example.backend.core.admin.service.OrderHistoryAdminService;
import com.example.backend.core.model.OrderHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryAdminServiceImpl implements OrderHistoryAdminService {

    @Autowired
    private OrderHistoryAdminRepository orderHistoryAdminRepository;

    @Override
    public List<OrderHistory> getAllOrderHistoryByOrder(Long idOrder) {
        return null;
    }
}
