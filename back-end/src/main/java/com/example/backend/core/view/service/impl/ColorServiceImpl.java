package com.example.backend.core.view.service.impl;

import com.example.backend.core.view.dto.ColorDTO;
import com.example.backend.core.view.mapper.ColorMapper;
import com.example.backend.core.view.repository.ColorRepository;
import com.example.backend.core.view.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {

    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ColorMapper colorMapper;

    @Override
    public List<ColorDTO> getAll() {
        return colorMapper.toDto(colorRepository.findAll());
    }
}
