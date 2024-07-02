package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.ProductDetailAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface ProductDetailAdminService {
    List<ProductDetailAdminDTO> getAll();

    ServiceResult<ProductDetailAdminDTO> add(ProductDetailAdminDTO productDetailAdminDTO);

    ServiceResult<ProductDetailAdminDTO> update(ProductDetailAdminDTO productDetailAdminDTO, Long id);

    ServiceResult<ProductDetailAdminDTO> delete(Long id);

    ServiceResult<ProductDetailAdminDTO> getById(Long id);

    List<ProductDetailAdminDTO> getProductDetails(int idColor, int idSize);

    ServiceResult<List<ProductDetailAdminDTO>> getProductDetailsByProductId(long idProduct);

    List<ProductDetailAdminDTO> findByNameLikeOrCodeLike(String param);
}
