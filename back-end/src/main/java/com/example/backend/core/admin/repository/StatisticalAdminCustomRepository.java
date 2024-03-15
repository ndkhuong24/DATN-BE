package com.example.backend.core.admin.repository;


import com.example.backend.core.admin.dto.StatisticalAdminDTO;
import com.example.backend.core.admin.dto.TotalStatisticalAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface StatisticalAdminCustomRepository {
    List<StatisticalAdminDTO> getStatisticalByDate(StatisticalAdminDTO statisticalAdminDTO);

    TotalStatisticalAdminDTO totalStatistical();
}
