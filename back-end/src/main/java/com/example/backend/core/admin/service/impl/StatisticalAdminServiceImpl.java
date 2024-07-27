package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.ProductDetailAdminDTO;
import com.example.backend.core.admin.dto.StatisticalAdminDTO;
import com.example.backend.core.admin.dto.TotalStatisticalAdminDTO;
import com.example.backend.core.admin.repository.ProductAdminCustomRepository;
import com.example.backend.core.admin.repository.StatisticalAdminCustomRepository;
import com.example.backend.core.admin.service.StatisticalAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticalAdminServiceImpl implements StatisticalAdminService {

    @Autowired
    private StatisticalAdminCustomRepository statisticalAdminCustomRepository;

    @Autowired
    private ProductAdminCustomRepository productAdminCustomRepository;

    @Override
    public TotalStatisticalAdminDTO getTotalStatisticalByYear(StatisticalAdminDTO statisticalAdminDTO) {
        TotalStatisticalAdminDTO totalStatisticalAdminDTO;

        List<StatisticalAdminDTO> statisticalAdminDTOList = statisticalAdminCustomRepository.getStatisticalByDate(statisticalAdminDTO);

        List<ProductDetailAdminDTO> productDetailAdminDTOList = productAdminCustomRepository.topProductBestSeller();

        totalStatisticalAdminDTO = statisticalAdminCustomRepository.totalStatistical();

        totalStatisticalAdminDTO.setListProductDetailBestSeller(productDetailAdminDTOList);

        totalStatisticalAdminDTO.setStatisticalAdminDTOList(statisticalAdminDTOList);

        return totalStatisticalAdminDTO;
    }
}
