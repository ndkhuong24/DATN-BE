package com.example.backend.core.admin.dto;

import com.example.backend.core.view.dto.ProductDetailDTO;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDetailAdminDTO {
    private Long id;
    private Long idOrder;
    private Long idProductDetail;
    private Integer quantity;
    private BigDecimal price;
    private String codeDiscount;
    private Integer status;
    private ProductDetailAdminDTO productDetailDTO;
    private OrderAdminDTO orderAdminDTO;
}
