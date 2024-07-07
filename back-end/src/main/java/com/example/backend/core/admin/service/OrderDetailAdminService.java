package com.example.backend.core.admin.service;

import java.util.Map;

public interface OrderDetailAdminService {
    Map<String, Object> getAllByOrder(Long idOrder);
}
