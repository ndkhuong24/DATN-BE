package com.example.backend.core.view.repository;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.ProductDTO;

import java.util.List;

public interface ProductCustomRepository {

    List<ProductDTO> getProductNoiBatByBrand(Long thuongHieu);

    List<ProductDTO> getProductTuongTu(Long idProduct, Long idCategory);

}
