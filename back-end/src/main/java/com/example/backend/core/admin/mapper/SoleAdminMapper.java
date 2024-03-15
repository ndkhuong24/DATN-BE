package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Sole;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface SoleAdminMapper extends EntityMapper<SoleAdminDTO, Sole> {
}
