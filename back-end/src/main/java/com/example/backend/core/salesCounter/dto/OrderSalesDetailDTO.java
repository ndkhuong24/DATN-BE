package com.example.backend.core.salesCounter.dto;

import com.example.backend.core.view.dto.ProductDetailDTO;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderSalesDetailDTO {
    private Long id;
    private Long idOrder;
    private Long idProductDetail;
    private Integer quantity;
    private BigDecimal price;
    private String codeDiscount;
    private Integer status;
    private ProductDetailDTO productDetailDTO;
}
