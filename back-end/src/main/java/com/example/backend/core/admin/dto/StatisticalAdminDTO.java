package com.example.backend.core.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class StatisticalAdminDTO implements Serializable {
    private String dateStr;
    private BigDecimal revenue;
    private Long quantityOrder;
    private Long quantityProduct;
    private String dateFrom;
    private String dateTo;
}
