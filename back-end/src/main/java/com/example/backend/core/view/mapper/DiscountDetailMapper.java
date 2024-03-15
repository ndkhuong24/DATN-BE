package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.DiscountDetail;
import com.example.backend.core.view.dto.DiscountDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface DiscountDetailMapper extends EntityMapper<DiscountDetailDTO, DiscountDetail> {
}
