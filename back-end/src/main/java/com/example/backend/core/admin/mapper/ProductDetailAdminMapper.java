package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.ProductDetailAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.ProductDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductDetailAdminMapper extends EntityMapper<ProductDetailAdminDTO, ProductDetail> {
}
