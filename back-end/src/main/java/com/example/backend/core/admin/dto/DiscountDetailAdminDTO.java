package com.example.backend.core.admin.dto;

import com.example.backend.core.commons.ExportDTO;
import com.example.backend.core.model.Product;
import com.example.backend.core.view.dto.ProductDTO;
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
    private Long idProduct;
    private Long idDiscount;
    private BigDecimal reducedValue;
    private Integer discountType;
    private BigDecimal maxReduced;
    private int status;
    private DiscountAdminDTO discountAdminDTO;
    private ProductAdminDTO productDTO;
    private List<ProductAdminDTO> productDTOList;
    private String discountTypeStr;
    private Integer isUpdate = 0;



}
