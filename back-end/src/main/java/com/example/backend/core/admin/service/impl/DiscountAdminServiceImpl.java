package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.repository.DiscountAdminRepository;
import com.example.backend.core.admin.service.DiscountAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Discount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DiscountAdminServiceImpl implements DiscountAdminService {
    @Autowired
    private DiscountAdminRepository discountAdminRepository;
    @Override
    public List<String> getAllDiscountExport() {
        List<String> lstStr = discountAdminRepository.findAll()
                .stream()
                .map(b -> b.getId() + "-" + b.getName())
                .collect(Collectors.toList());
        return lstStr;
    }
}
