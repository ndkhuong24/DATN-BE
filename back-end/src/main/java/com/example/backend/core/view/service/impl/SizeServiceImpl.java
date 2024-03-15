package com.example.backend.core.view.service.impl;

import com.example.backend.core.view.dto.SizeDTO;
import com.example.backend.core.view.mapper.SizeMapper;
import com.example.backend.core.view.repository.SizeRepository;
import com.example.backend.core.view.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeMapper sizeMapper;

    @Override
    public List<SizeDTO> getAll() {
        return sizeMapper.toDto(sizeRepository.findAll());
    }
}
