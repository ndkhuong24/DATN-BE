package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.dto.CategoryAdminDTO;
import com.example.backend.core.admin.mapper.CategoryAdminMapper;
import com.example.backend.core.admin.repository.CategoryAdminRepository;
import com.example.backend.core.admin.service.CategoryAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Brand;
import com.example.backend.core.model.Category;
import com.example.backend.core.model.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryAdminServiceIplm implements CategoryAdminService {
    @Autowired
    private CategoryAdminRepository ctrp;
    @Autowired
    private CategoryAdminMapper categoryAdminMapper;

    private ServiceResult<CategoryAdminDTO> result = new ServiceResult<>();
    @Override
    public List<CategoryAdminDTO> getAll() {
        List<CategoryAdminDTO> list = categoryAdminMapper.toDto(this.ctrp.findAll());
        return list;
    }

    @Override
    public List<String> getAllListExport() {
        return ctrp.findAll().stream().map(c -> c.getId() + "-" + c.getName()).collect(Collectors.toList());
    }

    @Override
    public ServiceResult<CategoryAdminDTO> add(CategoryAdminDTO categoryAdminDTO) {
        Category category =  categoryAdminMapper.toEntity(categoryAdminDTO);
        category.setCreateDate(Instant.now());
        category.setUpdateDate(Instant.now());
        this.ctrp.save(category);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(categoryAdminDTO);
        return result;
    }

    @Override
    public ServiceResult<CategoryAdminDTO> update(CategoryAdminDTO categoryAdminDTO, Long id) {
        Optional<Category> optional = this.ctrp.findById(id);
        if (optional.isPresent()){
            Category category = optional.get();
            category.setId(id);
            category.setUpdateDate(Instant.now());
            category.setStatus(categoryAdminDTO.getStatus());
            category.setName(categoryAdminDTO.getName());
            category  =  this.ctrp.save(category);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(categoryAdminMapper.toDto(category));

        }else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<CategoryAdminDTO> delete(Long id) {
        Optional<Category> optional = this.ctrp.findById(id);
        if (optional.isPresent()){
            this.ctrp.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<CategoryAdminDTO> findbyid(Long id) {
        Optional<Category> optional = this.ctrp.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            CategoryAdminDTO categoryAdminDTO = categoryAdminMapper.toDto(category);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Tìm thấy category thành công");
            result.setData(categoryAdminDTO);
        } else {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Không tìm thấy category với id " + id);
        }
        return result;
    }
}
