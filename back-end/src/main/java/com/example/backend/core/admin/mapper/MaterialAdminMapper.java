package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.MaterialAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Material;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface MaterialAdminMapper extends EntityMapper<MaterialAdminDTO, Material> {

}
