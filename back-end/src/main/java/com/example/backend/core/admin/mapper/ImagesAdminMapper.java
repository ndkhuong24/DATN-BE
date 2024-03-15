package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.ImagesAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Images;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ImagesAdminMapper extends EntityMapper<ImagesAdminDTO, Images> {
}
