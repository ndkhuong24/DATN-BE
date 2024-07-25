package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Product;
import com.example.backend.core.view.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getProductNoiBatByBrand(Long thuongHieu);

    ServiceResult<?> getDetailProduct(Long idProduct);

    List<ProductDTO> getProductTuongTu(Long idProduct, Long idCategory);

    ServiceResult<List<ProductDTO>> GetAll();
}
