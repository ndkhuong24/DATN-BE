package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Size;
import com.example.backend.core.view.dto.SizeDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SizeMapper extends EntityMapper<SizeDTO, Size> {
}
