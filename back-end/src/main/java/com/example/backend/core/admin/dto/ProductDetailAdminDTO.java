package com.example.backend.core.admin.dto;

import com.example.backend.core.view.dto.ColorDTO;
import com.example.backend.core.view.dto.ProductDTO;
import com.example.backend.core.view.dto.SizeDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDetailAdminDTO {
    private Long id;
    private Long idProduct;
    private Long idColor;
    private Long idSize;
    private Integer quantity;
    private BigDecimal listedPrice;
    private BigDecimal price;
    private Integer shoeCollar;
    private ProductAdminDTO productDTO;
    private ColorAdminDTO colorDTO;
    private SizeAdminDTO sizeDTO;
}
