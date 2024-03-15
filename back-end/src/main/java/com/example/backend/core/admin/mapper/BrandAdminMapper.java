package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface BrandAdminMapper extends EntityMapper<BrandAdminDTO, Brand> {
}
