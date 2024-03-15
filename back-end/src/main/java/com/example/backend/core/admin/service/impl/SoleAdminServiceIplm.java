package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.admin.mapper.SoleAdminMapper;
import com.example.backend.core.admin.repository.SoleAdminRepository;
import com.example.backend.core.admin.service.SoleAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Size;
import com.example.backend.core.model.Sole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SoleAdminServiceIplm  implements SoleAdminService {
    @Autowired
    private SoleAdminRepository slrp;
    @Autowired
    private SoleAdminMapper soleAdminMapper;

    private ServiceResult<SoleAdminDTO> result = new ServiceResult<>();
    @Override
    public List<SoleAdminDTO> getAll() {
         List<SoleAdminDTO> lst = soleAdminMapper.toDto(slrp.findAll());
         return lst;
    }

    @Override
    public List<String> getAllListExport() {
        return slrp.findAll().stream().map(s -> s.getId() + "-" + s.getSoleHeight() +" cm").collect(Collectors.toList());
    }

    @Override
    public ServiceResult<SoleAdminDTO> add(SoleAdminDTO soleAdminDTO) {
        Sole sole =  soleAdminMapper.toEntity(soleAdminDTO);
        sole.setCreateDate(Instant.now());
        sole.setUpdateDate(Instant.now());
        this.slrp.save(sole);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(soleAdminDTO);
        return result;
    }

    @Override
    public ServiceResult<SoleAdminDTO> update(SoleAdminDTO soleAdminDTO, Long id) {
        Optional<Sole> optional = this.slrp.findById(id);
        if (optional.isPresent()){
            Sole sole = optional.get();
            sole.setId(id);
            sole.setStatus(soleAdminDTO.getStatus());
            sole.setUpdateDate(Instant.now());
            sole.setSoleHeight(soleAdminDTO.getSoleHeight());
            sole.setSoleMaterial(soleAdminDTO.getSoleMaterial());
            sole.setDescription(soleAdminDTO.getDescription());
            Sole soleUpdate =  this.slrp.save(sole);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(soleAdminMapper.toDto(soleUpdate));

        }else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<SoleAdminDTO> delete(Long id) {
        Optional<Sole> optional = this.slrp.findById(id);
        if (optional.isPresent()){
            this.slrp.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<SoleAdminDTO> findbyid(Long id) {
        Optional<Sole> optional = this.slrp.findById(id);
        if (optional.isPresent()) {
            Sole sole = optional.get();
            SoleAdminDTO soleAdminDTO = soleAdminMapper.toDto(sole);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Tìm thấy Sole thành công");
            result.setData(soleAdminDTO);
        } else {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Không tìm thấy Sole với id " + id);
        }
        return result;
    }
}
