package com.example.backend.core.view.service;


import com.example.backend.core.model.Brand;
import com.example.backend.core.view.dto.BrandDTO;

import java.util.List;

public interface BrandService {
    List<BrandDTO> getAllBrandTop();

    List<Brand> getAll();
}
