package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.MaterialAdminDTO;
import com.example.backend.core.admin.mapper.MaterialAdminMapper;
import com.example.backend.core.admin.repository.MaterialAdminRepository;
import com.example.backend.core.admin.service.MaterialAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Material;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MaterialAdminServiceIplm implements MaterialAdminService {
    @Autowired
    private MaterialAdminRepository materialAdminRepository;

    @Autowired
    private MaterialAdminMapper materialAdminMapper;

    private ServiceResult<MaterialAdminDTO> result = new ServiceResult<>();

    @Override
    public List<MaterialAdminDTO> getAll() {
        List<MaterialAdminDTO> list = materialAdminMapper.toDto(this.materialAdminRepository.findAll());
        return list;
    }

    @Override
    public List<String> getAllListExport() {
        return materialAdminRepository.findAll().stream().map(m -> m.getId() + "-" + m.getName()).collect(Collectors.toList());
    }

    @Override
    public ServiceResult<MaterialAdminDTO> add(MaterialAdminDTO materialAdminDTO) {
        Material material = materialAdminMapper.toEntity(materialAdminDTO);
        material.setCreateDate(LocalDate.now());
        material.setUpdateDate(LocalDate.now());

        this.materialAdminRepository.save(material);

        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(materialAdminDTO);

        return result;
    }

    @Override
    public ServiceResult<MaterialAdminDTO> update(MaterialAdminDTO materialAdminDTO, Long id) {
        Optional<Material> optional = this.materialAdminRepository.findById(id);
        if (optional.isPresent()) {
            Material material = optional.get();
            material.setId(id);
            material.setName(materialAdminDTO.getName());
            material.setStatus(materialAdminDTO.getStatus());
            material.setDescription(materialAdminDTO.getDescription());
            material.setUpdateDate(LocalDate.now());
            material = this.materialAdminRepository.save(material);

            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(materialAdminMapper.toDto(material));

        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<MaterialAdminDTO> delete(Long id) {
        Optional<Material> optional = this.materialAdminRepository.findById(id);
        if (optional.isPresent()) {
            this.materialAdminRepository.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<MaterialAdminDTO> findbyid(Long id) {
        Optional<Material> optional = this.materialAdminRepository.findById(id);
        if (optional.isPresent()) {
            Material material = optional.get();
            MaterialAdminDTO materialAdminDTO = materialAdminMapper.toDto(material);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Tìm thấy material thành công");
            result.setData(materialAdminDTO);
        } else {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Không tìm thấy material với id " + id);
        }
        return result;
    }
}
