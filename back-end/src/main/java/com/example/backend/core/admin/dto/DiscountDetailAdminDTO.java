package com.example.backend.core.admin.dto;

import com.example.backend.core.commons.ExportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DiscountDetailAdminDTO extends ExportDTO {
    private Long id;
    private Long idProductDetail;
    private Long idDiscount;
    private BigDecimal reducedValue;
    private Integer discountType;
    private BigDecimal maxReduced;
    private int status;

    private DiscountAdminDTO discountAdminDTO;

    private ProductAdminDTO productAdminDTO;
    private List<ProductAdminDTO> productAdminDTOList;

    private ProductDetailAdminDTO productDetailAdminDTO;
    private List<ProductDetailAdminDTO> productDetailAdminDTOList;

    private String discountTypeStr;

    private Integer isUpdate = 0;
}
