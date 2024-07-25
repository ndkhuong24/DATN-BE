package com.example.backend.core.view.service.impl;

import com.example.backend.core.model.Sole;
import com.example.backend.core.view.repository.SoleRepository;
import com.example.backend.core.view.service.SoleSevice;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class SoleServiceImpl implements SoleSevice {
    @Autowired
    private SoleRepository repository;
    @Override
    public List<Sole> getAll() {
        return repository.findAll();
    }
}
