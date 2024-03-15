package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Discount;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})

public interface DiscountAdminMapper extends EntityMapper<DiscountAdminDTO, Discount> {
}
