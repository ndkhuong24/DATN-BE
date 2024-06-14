package com.example.backend.core.admin.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ProductDetailAdminDTO {
    //    private Long id;
//    private Long idProduct;
//    private Long idColor;
//    private Long idSize;
//    private Integer quantity;
//    private BigDecimal listedPrice;
//    private BigDecimal price;
//    private Integer shoeCollar;
//    private ProductAdminDTO productDTO;
//    private ColorAdminDTO colorDTO;
//    private SizeAdminDTO sizeDTO;
    private Long id;
    private Long idProduct;
    private Long idColor;
    private Long idSize;
    private Integer quantity;
    private BigDecimal price;
    private Integer shoeCollar;

    private String colorName;
    private String sizeName;

    private  ProductAdminDTO productDTO;
    private ColorAdminDTO colorDTO;
    private SizeAdminDTO sizeDTO;
}
