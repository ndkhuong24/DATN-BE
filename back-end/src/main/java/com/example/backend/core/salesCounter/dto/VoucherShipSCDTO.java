package com.example.backend.core.salesCounter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class VoucherShipSCDTO {
    private Long id;
    private String code;
    private BigDecimal conditionApply;
    private String idCustomer;
    private Date createDate;
    private Date startDate;
    private Date endDate;
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
