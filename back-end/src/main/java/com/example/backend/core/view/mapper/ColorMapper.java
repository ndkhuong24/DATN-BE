package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Color;
import com.example.backend.core.view.dto.ColorDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ColorMapper extends EntityMapper<ColorDTO, Color> {
}
