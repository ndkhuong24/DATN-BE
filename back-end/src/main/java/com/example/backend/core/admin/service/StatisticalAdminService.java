package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.StatisticalAdminDTO;
import com.example.backend.core.admin.dto.TotalStatisticalAdminDTO;

import java.util.List;

public interface StatisticalAdminService {

    TotalStatisticalAdminDTO getTotalStatisticalByYear(StatisticalAdminDTO statisticalAdminDTO);
}
