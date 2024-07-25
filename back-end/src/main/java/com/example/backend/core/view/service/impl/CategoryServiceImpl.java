package com.example.backend.core.view.service.impl;

import com.example.backend.core.model.Category;
import com.example.backend.core.view.repository.CategoryRepository;
import com.example.backend.core.view.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository repository;

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }
}
