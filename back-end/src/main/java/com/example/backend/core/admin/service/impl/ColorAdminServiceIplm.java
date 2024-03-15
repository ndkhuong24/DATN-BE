package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.mapper.ColorAdminMapper;
import com.example.backend.core.admin.repository.ColorAdminRepository;
import com.example.backend.core.admin.service.ColorAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Color;
import com.example.backend.core.view.dto.ColorDTO;
import com.example.backend.core.view.mapper.ColorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class ColorAdminServiceIplm implements ColorAdminService {
    @Autowired
    private ColorAdminRepository clrp;

    @Autowired
    private ColorAdminMapper colorAdminMapper;

    private ServiceResult<ColorAdminDTO> result = new ServiceResult<>();

    @Override
    public List<ColorAdminDTO> getAll() {
        List<ColorAdminDTO> lstdto = colorAdminMapper.toDto(this.clrp.findAll());
        return lstdto;
    }

    @Override
    public ServiceResult<ColorAdminDTO> addColor(ColorAdminDTO colorADDTO) {
        Color color = colorAdminMapper.toEntity(colorADDTO);
        color.setCreateDate(Instant.now());
        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(colorADDTO);
        clrp.save(color);
        return result;
    }

    @Override
    public ServiceResult<ColorAdminDTO> update(ColorAdminDTO colorAdminDTO, Long id) {
        Optional<Color> optional = this.clrp.findById(id);
        if (optional.isPresent()) {
            Color color = optional.get();
            color.setId(id);
            color.setName(colorAdminDTO.getName());
            color.setCode(colorAdminDTO.getCode());
            color  = clrp.save(color);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(colorAdminMapper.toDto(color));
        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<ColorAdminDTO> delete(Long id) {
        Optional<Color> optional = this.clrp.findById(id);
        if (optional.isPresent()) {
            this.clrp.deleteById(id);
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
