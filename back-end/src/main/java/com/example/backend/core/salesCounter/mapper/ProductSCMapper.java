package com.example.backend.core.salesCounter.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Product;
import com.example.backend.core.salesCounter.dto.ProductSCDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ProductSCMapper extends EntityMapper<ProductSCDTO, Product> {
}
