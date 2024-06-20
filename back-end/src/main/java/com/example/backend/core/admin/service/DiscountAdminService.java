package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface DiscountAdminService {
    List<String> getAllDiscountExport();

    List<DiscountAdminDTO> getAll();
}
