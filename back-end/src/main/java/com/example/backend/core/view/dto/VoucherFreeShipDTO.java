package com.example.backend.core.view.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoucherFreeShipDTO {
    private Long id;
    private String code;
    private BigDecimal conditionApply;
    private String idCustomer;
    private LocalDateTime createDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String createName;
    private BigDecimal reducedValue;
    private Integer quantity;
    private Integer amountUsed;
    private String description;
    private Integer status;
    private Integer isdel;
    private Integer optionCustomer;
    private Integer limitCustomer;
    private Long idCustomerLogin;
    private Integer delete;
}
