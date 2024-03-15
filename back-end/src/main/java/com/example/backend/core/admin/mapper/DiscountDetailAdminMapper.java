package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.DiscountDetail;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface DiscountDetailAdminMapper extends EntityMapper<DiscountDetailAdminDTO, DiscountDetail> {
}
