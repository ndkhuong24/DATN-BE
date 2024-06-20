package com.example.backend.core.admin.dto;
import com.example.backend.core.commons.ExportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
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
    private LocalDate createDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String createName;
    private Integer status;
    private Integer idel;
    private Integer  delete;
    private BigDecimal reducedValue; //giá trị giảm
    private BigDecimal maxReduced; //giá trị giảm tối
    private Integer discountType; //loại giảm giá

    private Integer  used_count;
    private List<ProductAdminDTO> productDTOList;
    private List<ProductDetailAdminDTO> productDetailAdminDTOList;
    private Integer isUpdate = 0;

}
