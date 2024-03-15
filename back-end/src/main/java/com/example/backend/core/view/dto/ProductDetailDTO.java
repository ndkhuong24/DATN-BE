package com.example.backend.core.view.dto;


import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;


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
    private Instant createDate;
    private Instant updateDate;
    private Integer shoeCollar;
    private ProductDTO productDTO;
    private ColorDTO colorDTO;
    private SizeDTO sizeDTO;

}
