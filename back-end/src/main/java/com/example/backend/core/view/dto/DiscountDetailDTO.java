package com.example.backend.core.view.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DiscountDetailDTO {
    private Long id;
    private Long idProductDetail;
    private Long idDiscount;
    private BigDecimal reducedValue;
    private Integer discountType;
    private BigDecimal maxReduced;
    private Integer status;

    private DiscountDTO discountDTO;

    private ProductDTO productDTO;
    private List<ProductDTO> productDTOList;

    private ProductDetailDTO productDetailDTO;
    private List<ProductDetailDTO> productDetailDTOList;
}
