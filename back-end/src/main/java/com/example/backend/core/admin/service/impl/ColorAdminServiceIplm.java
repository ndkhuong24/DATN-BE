package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.mapper.ColorAdminMapper;
import com.example.backend.core.admin.repository.ColorAdminRepository;
import com.example.backend.core.admin.service.ColorAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ColorAdminServiceIplm implements ColorAdminService {
    @Autowired
    private ColorAdminRepository colorAdminRepository;

    @Autowired
    private ColorAdminMapper colorAdminMapper;

    @Override
    public List<ColorAdminDTO> getAll() {
        List<ColorAdminDTO> colorAdminDTOList = colorAdminMapper.toDto(this.colorAdminRepository.findAll());
        // Sắp xếp brandAdminDTOList theo thứ tự giảm dần của updateDate
        List<ColorAdminDTO> sortedList = colorAdminDTOList.stream()
                .sorted(Comparator.comparing(ColorAdminDTO::getUpdateDate).reversed())
                .collect(Collectors.toList());
        return sortedList;
    }

    @Override
    public ServiceResult<ColorAdminDTO> add(ColorAdminDTO colorAdminDTO) {
        ServiceResult<ColorAdminDTO> result = new ServiceResult<>();

        Color color = colorAdminMapper.toEntity(colorAdminDTO);
        color.setCreateDate(LocalDateTime.now());
        color.setUpdateDate(LocalDateTime.now());

        this.colorAdminRepository.save(color);

        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(colorAdminDTO);

        return result;
    }

    @Override
    public ServiceResult<ColorAdminDTO> update(ColorAdminDTO colorAdminDTO, Long id) {
        ServiceResult<ColorAdminDTO> result = new ServiceResult<>();

        Optional<Color> optional = this.colorAdminRepository.findById(id);
        if (optional.isPresent()) {
            Color color = optional.get();
            color.setId(id);
            color.setName(colorAdminDTO.getName());
            color.setCode(colorAdminDTO.getCode());
            color.setStatus(colorAdminDTO.getStatus());
            color.setUpdateDate(LocalDateTime.now());

            Color updateColor = this.colorAdminRepository.save(color);
            ColorAdminDTO updateColorAdminDTO = colorAdminMapper.toDto(updateColor);

            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(updateColorAdminDTO);
        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<ColorAdminDTO> delete(Long id) {
        ServiceResult<ColorAdminDTO> result = new ServiceResult<>();

        Optional<Color> optional = this.colorAdminRepository.findById(id);
        if (optional.isPresent()) {
            this.colorAdminRepository.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Xoa khong  thanh cong");
            result.setData(null);
        }
        return result;
    }
}
