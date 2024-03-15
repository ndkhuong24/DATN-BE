package com.example.backend.core.view.service;

import com.example.backend.core.model.Size;
import com.example.backend.core.view.dto.SizeDTO;

import java.util.List;

public interface SizeService {

    List<SizeDTO> getAll();
}
