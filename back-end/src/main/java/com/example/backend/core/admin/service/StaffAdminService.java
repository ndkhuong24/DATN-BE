package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Staff;

import java.util.List;
import java.util.Optional;

public interface StaffAdminService {
    List<StaffAdminDTO> getAllStaff();
    ServiceResult<StaffAdminDTO> findById(Long id);
    ServiceResult<StaffAdminDTO> updateStaff(Long id,StaffAdminDTO staffAdminDTO);
    List<StaffAdminDTO> findByCodeOrPhone(String param);
    List<StaffAdminDTO> findByFullnameOrPhoneLike(String params);
}
