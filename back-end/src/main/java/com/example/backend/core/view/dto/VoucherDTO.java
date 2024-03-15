package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class VoucherDTO {
    private Long id;
    private String code;
    private BigDecimal conditionApply;
    private String idCustomer;
    private Date createDate;
    private Date startDate;
    private Date endDate;
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
