package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.ProductDTO;
import com.example.backend.core.view.dto.ProductDetailDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> getProductNoiBatByBrand(Long thuongHieu);

    ServiceResult<?> getDetailProduct(Long idProduct);

    List<ProductDTO> getProductTuongTu(Long idProduct, Long idCategory);
}
