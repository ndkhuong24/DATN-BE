package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface SizeAdminService {
    List<SizeAdminDTO> getAll();
    ServiceResult<SizeAdminDTO> add(SizeAdminDTO sizeAdminDTO);
    ServiceResult<SizeAdminDTO> update(SizeAdminDTO sizeAdminDTO,Long id);
    ServiceResult<SizeAdminDTO> delete(Long id);
}
