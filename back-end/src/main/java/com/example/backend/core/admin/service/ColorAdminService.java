package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface ColorAdminService {
    List<ColorAdminDTO> getAll();

    ServiceResult<ColorAdminDTO> add(ColorAdminDTO colorADDTO);

    ServiceResult<ColorAdminDTO> update(ColorAdminDTO colorAdminDTO, Long id);

    ServiceResult<ColorAdminDTO> delete(Long id);
}
