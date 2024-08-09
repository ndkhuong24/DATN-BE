package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.OrderDetailAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.OrderDetail;

import java.util.Map;

public interface OrderDetailAdminService {
    Map<String, Object> getAllByOrder(Long idOrder);

    ServiceResult<OrderDetailAdminDTO> deleteOrderDetail(Long id);
}
