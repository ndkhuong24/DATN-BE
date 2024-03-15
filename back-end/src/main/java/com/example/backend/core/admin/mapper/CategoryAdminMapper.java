package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.CategoryAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {})
public interface CategoryAdminMapper extends EntityMapper<CategoryAdminDTO, Category> {
}
