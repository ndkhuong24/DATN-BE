package com.example.backend.core.admin.dto;

import com.example.backend.core.commons.ExportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VoucherFreeShipDTO extends ExportDTO {
    private Long id;
    private String code;
    private String name;
    private String idCustomer;
    private LocalDate createDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal conditionApply;
    private String createName;
    private BigDecimal reducedValue;
    private String description;
    private Integer status;
    private Integer idel;
    private Integer quantity;
    private Integer limitCustomer;
    private Integer allow;
    private Integer optionCustomer;
    private Integer useVoucher;
    private Integer amountUsed;
    private Integer delete;
    private CustomerAdminDTO customerAdminDTO;
    private String nameCustomer;
    private List<CustomerAdminDTO> customerAdminDTOList;
    private Integer isUpdate = 0;
    private String listCodeCustomerExport;
}
