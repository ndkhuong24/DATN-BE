package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Size;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SizeAdminMapper extends EntityMapper<SizeAdminDTO, Size> {
}
