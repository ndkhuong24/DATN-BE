package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface StaffAdminService {
    List<StaffAdminDTO> getAllStaff();

    ServiceResult<StaffAdminDTO> findById(Long id);

    ServiceResult<StaffAdminDTO> updateStaff(Long id, StaffAdminDTO staffAdminDTO);

    List<StaffAdminDTO> findByFullnameOrPhoneLike(String params);
}
