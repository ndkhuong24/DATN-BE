package com.example.backend.core.view.service.impl;

import com.example.backend.core.model.Material;
import com.example.backend.core.view.repository.MaterialRepository;
import com.example.backend.core.view.service.MaterialService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository repository;
    @Override
    public List<Material> getAll() {
        return repository.findAll();
    }
}
