package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Images;
import com.example.backend.core.view.dto.ImagesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ImagesMapper extends EntityMapper<ImagesDTO, Images> {
}
