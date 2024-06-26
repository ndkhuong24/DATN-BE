package com.example.backend.core.admin.dto;

import com.example.backend.core.commons.ExportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VoucherAdminDTO extends ExportDTO {
    private Long id;
    private String code;
    private String name;
    private String idCustomer;
    private LocalDateTime createDate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal conditionApply;
    private String createName;
    private Integer voucherType;
    private BigDecimal reducedValue;
    private String description;
    private Integer status;
    private Integer idel;
    private Integer quantity;
    private BigDecimal maxReduced;
    private Integer limitCustomer;
    private Integer allow;
    private Integer optionCustomer;
    private Integer apply;
    private Integer useVoucher;
    private Integer amountUsed;
    private Integer delete;
    private CustomerAdminDTO customerAdminDTO;
    private List<CustomerAdminDTO> customerAdminDTOList;
    private String dateFrom;
    private String dateTo;
    private String nameCustomer;
    private Integer isUpdate = 0;
    private String listCodeCustomerExport;
}
