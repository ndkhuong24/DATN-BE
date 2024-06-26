package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.mapper.BrandAdminMapper;
import com.example.backend.core.admin.repository.BrandAdminRepository;
import com.example.backend.core.admin.service.BrandAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BrandAdminServiceIplm implements BrandAdminService {
    @Autowired
    private BrandAdminRepository brandAdminRepository;

    @Autowired
    private BrandAdminMapper brandAdminMapper;

    @Override
    public List<BrandAdminDTO> getAll() {
        List<BrandAdminDTO> brandAdminDTOList = brandAdminMapper.toDto(this.brandAdminRepository.findAll());
        // Sắp xếp brandAdminDTOList theo thứ tự giảm dần của updateDate
        List<BrandAdminDTO> sortedList = brandAdminDTOList.stream()
                .sorted(Comparator.comparing(BrandAdminDTO::getUpdateDate).reversed())
                .collect(Collectors.toList());
        return sortedList;
    }

    @Override
    public List<String> getAllBrandExport() {
        List<String> lstStr = brandAdminRepository.findAll()
                .stream()
                .map(b -> b.getId() + "-" + b.getName())
                .collect(Collectors.toList());
        return lstStr;
    }

    @Override
    public ServiceResult<BrandAdminDTO> add(BrandAdminDTO brandAdminDTO) {
        ServiceResult<BrandAdminDTO> result = new ServiceResult<>();

        Brand brand = brandAdminMapper.toEntity(brandAdminDTO);
        brand.setCreateDate(LocalDateTime.now());
        brand.setUpdateDate(LocalDateTime.now());

        this.brandAdminRepository.save(brand);

        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(brandAdminDTO);

        return result;
    }

    @Override
    public ServiceResult<BrandAdminDTO> update(BrandAdminDTO brandAdminDTO, Long id) {
        ServiceResult<BrandAdminDTO> result = new ServiceResult<>();

        Optional<Brand> optional = this.brandAdminRepository.findById(id);

        if (optional.isPresent()) {
            Brand brand = optional.get();
            brand.setId(id);
            brand.setName(brandAdminDTO.getName());
            brand.setStatus(brandAdminDTO.getStatus());
            brand.setUpdateDate(LocalDateTime.now());

            Brand updatedBrand = this.brandAdminRepository.save(brand);
            BrandAdminDTO updatedBrandAdminDTO = brandAdminMapper.toDto(updatedBrand);

            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(updatedBrandAdminDTO);
        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai ");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<BrandAdminDTO> delete(Long id) {
        ServiceResult<BrandAdminDTO> result = new ServiceResult<>();

        Optional<Brand> optional = this.brandAdminRepository.findById(id);
        if (optional.isPresent()) {
            this.brandAdminRepository.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        }
        return result;
    }

    @Override
    public ServiceResult<BrandAdminDTO> findbyid(Long id) {
        ServiceResult<BrandAdminDTO> result = new ServiceResult<>();

        Optional<Brand> optional = this.brandAdminRepository.findById(id);
        if (optional.isPresent()) {
            Brand brand = optional.get();

            BrandAdminDTO brandAdminDTO = brandAdminMapper.toDto(brand);

            result.setStatus(HttpStatus.OK);
            result.setMessage("Tìm thấy Brand thành công");
            result.setData(brandAdminDTO);
        } else {
            result.setStatus(HttpStatus.NOT_FOUND);
            result.setMessage("Không tìm thấy Brand với id " + id);
        }
        return result;
    }
}
