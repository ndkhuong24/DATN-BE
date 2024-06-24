package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.admin.mapper.SizeAdminMapper;
import com.example.backend.core.admin.repository.SizeAdminRepository;
import com.example.backend.core.admin.service.SizeAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Size;
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
public class SizeAdminServiceIplm implements SizeAdminService {
    @Autowired
    private SizeAdminRepository sizeAdminRepository;

    @Autowired
    private SizeAdminMapper sizeAdminMapper;

    @Override
    public List<SizeAdminDTO> getAll() {
        List<SizeAdminDTO> sizeAdminDTOList = sizeAdminMapper.toDto(this.sizeAdminRepository.findAll());
        // Sắp xếp brandAdminDTOList theo thứ tự giảm dần của updateDate
        List<SizeAdminDTO> sortedList = sizeAdminDTOList.stream()
                .sorted(Comparator.comparing(SizeAdminDTO::getUpdateDate).reversed())
                .collect(Collectors.toList());
        return sortedList;
    }

    @Override
    public ServiceResult<SizeAdminDTO> add(SizeAdminDTO sizeAdminDTO) {
        ServiceResult<SizeAdminDTO> result = new ServiceResult<>();

        Size size = sizeAdminMapper.toEntity(sizeAdminDTO);
        size.setCreateDate(LocalDateTime.now());
        size.setUpdateDate(LocalDateTime.now());

        Size savedSize = this.sizeAdminRepository.save(size);
        SizeAdminDTO savedSizeAdminDTO = sizeAdminMapper.toDto(savedSize);

        result.setStatus(HttpStatus.OK);
        result.setMessage("Them thanh cong");
        result.setData(savedSizeAdminDTO);

        return result;
    }

    @Override
    public ServiceResult<SizeAdminDTO> update(SizeAdminDTO sizeAdminDTO, Long id) {
        ServiceResult<SizeAdminDTO> result = new ServiceResult<>();

        Optional<Size> optional = this.sizeAdminRepository.findById(id);

        if (optional.isPresent()) {
            Size size = optional.get();
            size.setSizeNumber(sizeAdminDTO.getSizeNumber());
            size.setStatus(sizeAdminDTO.getStatus());
            size.setUpdateDate(LocalDateTime.now());

            Size updatedSize = this.sizeAdminRepository.save(size);
            SizeAdminDTO updatedSizeAdminDTO = sizeAdminMapper.toDto(updatedSize);

            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(updatedSizeAdminDTO);
        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai");
            result.setData(null);
        }

        return result;
    }

    @Override
    public ServiceResult<SizeAdminDTO> delete(Long id) {
        ServiceResult<SizeAdminDTO> result = new ServiceResult<>();

        Optional<Size> optional = this.sizeAdminRepository.findById(id);
        if (optional.isPresent()) {
            this.sizeAdminRepository.deleteById(id);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xoa thanh cong");
            result.setData(null);
        } else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Id khong ton tai");
            result.setData(null);
        }

        return result;
    }
}
