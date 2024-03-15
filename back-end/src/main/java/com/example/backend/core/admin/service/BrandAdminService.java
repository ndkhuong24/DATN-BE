package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface BrandAdminService {
    List<BrandAdminDTO> getAll();
    List<String> getAllBrandExport();
    ServiceResult<BrandAdminDTO> add(BrandAdminDTO brandAdminDTO);
    ServiceResult<BrandAdminDTO> update(BrandAdminDTO brandAdminDTO,Long id);
    ServiceResult<BrandAdminDTO> delete(Long id);
    ServiceResult<BrandAdminDTO> findbyid(Long id);
}
