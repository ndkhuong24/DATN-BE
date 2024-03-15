package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductAdminMapper extends EntityMapper<ProductAdminDTO, Product> {
}
