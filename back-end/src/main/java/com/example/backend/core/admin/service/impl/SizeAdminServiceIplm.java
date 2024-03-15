package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.admin.mapper.SizeAdminMapper;
import com.example.backend.core.admin.repository.SizeAdminReposiotry;
import com.example.backend.core.admin.service.SizeAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class SizeAdminServiceIplm implements SizeAdminService {
    @Autowired
    private SizeAdminReposiotry srp;
    @Autowired
    private SizeAdminMapper sizeAdminMapper;

    private ServiceResult<SizeAdminDTO> result = new ServiceResult<>();
    @Override
    public List<SizeAdminDTO> getAll() {
        List<SizeAdminDTO> lstdto = sizeAdminMapper.toDto(this.srp.findAll());
        return lstdto;
    }

    @Override
    public ServiceResult<SizeAdminDTO> add(SizeAdminDTO sizeAdminDTO) {
        Size size =  sizeAdminMapper.toEntity(sizeAdminDTO);
        size.setCreateDate(Instant.now());
        this.srp.save(size);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(sizeAdminDTO);
        return result;
    }

    @Override
    public ServiceResult<SizeAdminDTO> update(SizeAdminDTO sizeAdminDTO, Long id) {
        Optional<Size> optional = this.srp.findById(id);
        if (optional.isPresent()){
            Size size = optional.get();
            size.setId(id);
            size.setSizeNumber(sizeAdminDTO.getSizeNumber());
            size.setStatus(sizeAdminDTO.getStatus());
            size =  this.srp.save(size);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(sizeAdminMapper.toDto(size));

        }else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<SizeAdminDTO> delete(Long id) {
        Optional<Size> optional = this.srp.findById(id);
        if (optional.isPresent()){
            this.srp.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }
}
