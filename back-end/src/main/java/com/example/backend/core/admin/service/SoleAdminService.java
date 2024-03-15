package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface SoleAdminService {
    List<SoleAdminDTO> getAll();
    List<String> getAllListExport();
    ServiceResult<SoleAdminDTO> add(SoleAdminDTO soleAdminDTO);
    ServiceResult<SoleAdminDTO> update(SoleAdminDTO soleAdminDTO,Long id);
    ServiceResult<SoleAdminDTO> delete(Long id);
    ServiceResult<SoleAdminDTO> findbyid(Long id);
}
