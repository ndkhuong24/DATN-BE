package com.example.backend.core.view.service.impl;

import com.example.backend.core.model.Brand;
import com.example.backend.core.view.dto.BrandDTO;
import com.example.backend.core.view.repository.BrandCustomRepository;
import com.example.backend.core.view.repository.BrandRepository;
import com.example.backend.core.view.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandCustomRepository brandCustomRepository;

    @Autowired
    private BrandRepository repository;

    @Override
    public List<BrandDTO> getAllBrandTop() {
        return brandCustomRepository.getAllBrandTop();
    }

    @Override
    public List<Brand> getAll() {
        return repository.findAll();
    }
}
