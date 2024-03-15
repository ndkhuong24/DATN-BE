package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.StatisticalAdminDTO;
import com.example.backend.core.admin.dto.TotalStatisticalAdminDTO;
import com.example.backend.core.admin.repository.ProductAdminCustomRepository;
import com.example.backend.core.admin.repository.StatisticalAdminCustomRepository;
import com.example.backend.core.admin.service.StatisticalAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import java.util.List;

@Service
public class StatisticalAdminServiceImpl implements StatisticalAdminService {

    @Autowired
    private StatisticalAdminCustomRepository statisticalAdminCustomRepository;

    @Autowired
    private ProductAdminCustomRepository productAdminCustomRepository;
    @Override
    public TotalStatisticalAdminDTO getTotalStatisticalByYear(StatisticalAdminDTO statisticalAdminDTO) {
        TotalStatisticalAdminDTO dto = new TotalStatisticalAdminDTO();
        List<StatisticalAdminDTO> list = statisticalAdminCustomRepository.getStatisticalByDate(statisticalAdminDTO);
        List<ProductAdminDTO> productAdminDTOList = productAdminCustomRepository.topProductBestSeller();
        dto = statisticalAdminCustomRepository.totalStatistical();
        dto.setListProductBestSeller(productAdminDTOList);
        dto.setStatisticalAdminDTOList(list);
//        TotalStatisticalAdminDTO totalDTO = new TotalStatisticalAdminDTO(0L, 0L, 0L, new BigDecimal(0), 0L);
//        int year = Integer.parseInt(statisticalAdminDTO.getYear());
//        list.forEach(s -> {
//            if (s.getQuantityOrder() != null) {
//                totalDTO.setTotalOrder(totalDTO.getTotalOrder() + s.getQuantityOrder());
//            }
//            if (s.getQuantityCompleted() != null) {
//                totalDTO.setTotalOrderCompleted(totalDTO.getTotalOrderCompleted() + s.getQuantityCompleted());
//            }
//            if (s.getQuantityRefunded() != null) {
//                totalDTO.setTotalOrderRefunded(totalDTO.getTotalOrderRefunded() + s.getQuantityRefunded());
//            }
//            totalDTO.setTotalRevenue(totalDTO.getTotalRevenue().add(s.getRevenue()));
//            Calendar calendarStart = Calendar.getInstance();
//            calendarStart.set(Calendar.YEAR, year);
//            calendarStart.set(Calendar.MONTH, (int) (s.getMonth() - 1)); // Trừ đi 1 vì tháng bắt đầu từ 0
//            calendarStart.set(Calendar.DAY_OF_MONTH, 1);
//
//            // get last day of month
//            Calendar calendarEnd = Calendar.getInstance();
//            calendarEnd.set(Calendar.YEAR, year);
//            calendarEnd.set(Calendar.MONTH, (int) (s.getMonth() - 1));
//            calendarEnd.set(Calendar.DAY_OF_MONTH, calendarEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
//            s.setMonthStr("Tháng " + s.getMonth());
//            s.setYear(statisticalAdminDTO.getYear());
//        });
        return dto;
    }
}
