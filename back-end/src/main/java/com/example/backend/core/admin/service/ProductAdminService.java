package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface ProductAdminService {
    List<ProductAdminDTO> getAll();

    ServiceResult<ProductAdminDTO> add(ProductAdminDTO productAdminDTO);

    ServiceResult<ProductAdminDTO> getById(Long id);

    void activateProduct(Long productId);

    void deactivateProduct(Long productId);

    ServiceResult<ProductAdminDTO> update(ProductAdminDTO productAdminDTO, Long id);

    List<ProductAdminDTO> findByNameLikeOrCodeLike(String param);

//    ServiceResult<ProductAdminDTO> delete(Long id);
//    List<ProductAdminDTO> findByNameLikeOrCodeLike(String param);
//    List<ProductAdminDTO> getAllProductsWithDetailsAndImages(String keyword);
//    List<Product> searchProducts(String keyword);
}
