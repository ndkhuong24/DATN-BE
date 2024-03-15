package com.example.backend.core.admin.dto;
import com.example.backend.core.commons.ExportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountAdminDTO extends ExportDTO {
    private Long id;
    private String code;
    private String name;
    private Date createDate;
    private java.util.Date startDate;
    private java.util.Date endDate;
    private String description;
    private String createName;
    private Integer status;
    private Integer idel;
    private Integer  used_count;
    private Integer  delete;

    private List<ProductAdminDTO> productDTOList;
    private BigDecimal reducedValue;
    private BigDecimal maxReduced;
    private Integer discountType;
    private Integer isUpdate = 0;

}
