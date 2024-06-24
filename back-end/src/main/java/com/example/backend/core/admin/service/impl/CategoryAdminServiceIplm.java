package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.dto.CategoryAdminDTO;
import com.example.backend.core.admin.mapper.CategoryAdminMapper;
import com.example.backend.core.admin.repository.CategoryAdminRepository;
import com.example.backend.core.admin.service.CategoryAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Category;
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
public class CategoryAdminServiceIplm implements CategoryAdminService {
    @Autowired
    private CategoryAdminRepository categoryAdminRepository;

    @Autowired
    private CategoryAdminMapper categoryAdminMapper;

    @Override
    public List<CategoryAdminDTO> getAll() {
        List<CategoryAdminDTO> categoryAdminDTOList = categoryAdminMapper.toDto(this.categoryAdminRepository.findAll());

        // Sắp xếp brandAdminDTOList theo thứ tự giảm dần của updateDate
        List<CategoryAdminDTO> sortedList = categoryAdminDTOList.stream()
                .sorted(Comparator.comparing(CategoryAdminDTO::getUpdateDate).reversed())
                .collect(Collectors.toList());

        return sortedList;
    }

    @Override
    public List<String> getAllListExport() {
        return categoryAdminRepository.findAll().stream().map(c -> c.getId() + "-" + c.getName()).collect(Collectors.toList());
    }

    @Override
    public ServiceResult<CategoryAdminDTO> add(CategoryAdminDTO categoryAdminDTO) {
        ServiceResult<CategoryAdminDTO> result = new ServiceResult<>();

        Category category = categoryAdminMapper.toEntity(categoryAdminDTO);
        category.setCreateDate(LocalDateTime.now());
        category.setUpdateDate(LocalDateTime.now());
        this.categoryAdminRepository.save(category);

        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(categoryAdminDTO);
        return result;
    }

    @Override
    public ServiceResult<CategoryAdminDTO> update(CategoryAdminDTO categoryAdminDTO, Long id) {
        ServiceResult<CategoryAdminDTO> result = new ServiceResult<>();

        Optional<Category> optional = this.categoryAdminRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            category.setId(id);
            category.setUpdateDate(LocalDateTime.now());
            category.setStatus(categoryAdminDTO.getStatus());
            category.setName(categoryAdminDTO.getName());

            Category updateCategory = this.categoryAdminRepository.save(category);
            CategoryAdminDTO  updateCategoryAdminDTO = categoryAdminMapper.toDto(updateCategory);

            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(updateCategoryAdminDTO);
        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<CategoryAdminDTO> delete(Long id) {
        ServiceResult<CategoryAdminDTO> result = new ServiceResult<>();

        Optional<Category> optional = this.categoryAdminRepository.findById(id);
        if (optional.isPresent()) {
            this.categoryAdminRepository.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<CategoryAdminDTO> findbyid(Long id) {
        ServiceResult<CategoryAdminDTO> result = new ServiceResult<>();

        Optional<Category> optional = this.categoryAdminRepository.findById(id);
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
