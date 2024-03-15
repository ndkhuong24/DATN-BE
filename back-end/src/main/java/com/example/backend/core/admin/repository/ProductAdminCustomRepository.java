package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.model.Product;

import java.util.List;

public interface ProductAdminCustomRepository {

    List<ProductAdminDTO> getAllProductExport();

    List<ProductAdminDTO> topProductBestSeller();
}
