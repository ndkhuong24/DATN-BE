package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.OrderDetailAdminDTO;
import com.example.backend.core.view.dto.OrderDetailDTO;

import java.util.List;
import java.util.Map;

public interface OrderDetailAdminService {
    Map<String, Object> getAllByOrder(Long idOrder);
}
