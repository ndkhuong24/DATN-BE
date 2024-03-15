package com.example.backend.core.view.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailDTO {
    private Long id;
    private Long idOrder;
    private Long idProductDetail;
    private Integer quantity;
    private BigDecimal price;
    private String codeDiscount;
    private Integer status;
    private ProductDetailDTO productDetailDTO;
    private Long productId;
}
