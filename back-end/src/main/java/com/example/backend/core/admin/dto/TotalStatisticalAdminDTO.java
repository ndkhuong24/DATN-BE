package com.example.backend.core.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TotalStatisticalAdminDTO implements Serializable {
    private Long totalQuantityProduct;
    private Long totalOrder;
    private BigDecimal totalRevenue;
    private Long totalOrderToday;
    private BigDecimal totalRevenueToday;
    private List<StatisticalAdminDTO> statisticalAdminDTOList;
    private List<ProductAdminDTO> listProductBestSeller;

}
