package com.example.backend.core.salesCounter.service;

import com.example.backend.core.salesCounter.dto.ProductSCDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ProductSCService {
    List<ProductSCDTO> getAllProductDetail();
}
