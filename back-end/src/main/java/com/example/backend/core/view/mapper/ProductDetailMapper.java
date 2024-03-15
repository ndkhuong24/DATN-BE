package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.view.dto.ProductDetailDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductDetailMapper extends EntityMapper<ProductDetailDTO, ProductDetail> {
}
