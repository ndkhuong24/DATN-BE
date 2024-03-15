package com.example.backend.core.admin.service;


import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.dto.MaterialAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.MaterialDTO;

import java.util.List;

public interface MaterialAdminService {
    List<MaterialAdminDTO> getAll();
    List<String> getAllListExport();
    ServiceResult<MaterialAdminDTO> add(MaterialAdminDTO materialAdminDTO);
    ServiceResult<MaterialAdminDTO> update(MaterialAdminDTO materialAdminDTO,Long id);
    ServiceResult<MaterialAdminDTO> delete(Long id);
    ServiceResult<MaterialAdminDTO> findbyid(Long id);
}
