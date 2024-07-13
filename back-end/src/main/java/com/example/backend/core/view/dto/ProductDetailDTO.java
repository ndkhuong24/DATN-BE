package com.example.backend.core.view.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDetailDTO {
    private Long id;
    private Long idProduct;
    private Long idColor;
    private Long idSize;
    private Integer quantity;
    private BigDecimal price;
    private Integer shoeCollar;

    private BigDecimal listedPrice;//gia niem yet
    private BigDecimal reducePrice;//giảm giá
    private Integer percentageReduce;//phần trăm giảm
    private String codeDiscount;//mã giảm gia

    private ProductDTO productDTO;
    private ColorDTO colorDTO;
    private SizeDTO sizeDTO;
}
