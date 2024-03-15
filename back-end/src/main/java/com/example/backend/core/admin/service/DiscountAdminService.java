package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiscountAdminService  {
    List<String> getAllDiscountExport();

}
