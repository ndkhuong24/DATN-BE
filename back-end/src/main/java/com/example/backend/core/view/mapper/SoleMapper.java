package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Sole;
import com.example.backend.core.view.dto.SoleDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SoleMapper extends EntityMapper<SoleDTO, Sole> {
}
