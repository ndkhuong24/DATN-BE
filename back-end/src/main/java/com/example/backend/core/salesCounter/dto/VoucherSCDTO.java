package com.example.backend.core.salesCounter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VoucherSCDTO {
    private Long id;
    private String code;
    private BigDecimal conditionApply;
    private String idCustomer;
    private LocalDate createDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String createName;
    private Integer voucherType;
    private BigDecimal reducedValue;
    private Integer quantity;
    private Integer amountUsed;
    private String description;
    private Integer status;
    private Integer isdel;
    private Integer apply;
    private Integer optionCustomer;
    private BigDecimal maxReduced;
    private Integer limitCustomer;
    private Integer allow;
    private Long idCustomerLogin;
    private Integer delete;
}
