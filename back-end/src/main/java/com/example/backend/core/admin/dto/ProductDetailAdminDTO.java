package com.example.backend.core.admin.dto;

import lombok.*;

import java.math.BigDecimal;

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
    private BigDecimal price;
    private Integer shoeCollar;

    private BigDecimal listedPrice;//gia niem yet

    private BigDecimal totalBestSeller;

    private ProductAdminDTO productDTO;
    private ColorAdminDTO colorDTO;
    private SizeAdminDTO sizeDTO;

    public ProductDetailAdminDTO(Long id, Long idProduct, Long idColor, Long idSize, Integer quantity, BigDecimal price, Integer shoeCollar, ProductAdminDTO productDTO, ColorAdminDTO colorDTO, SizeAdminDTO sizeDTO) {
        this.id = id;
        this.idProduct = idProduct;
        this.idColor = idColor;
        this.idSize = idSize;
        this.quantity = quantity;
        this.price = price;
        this.shoeCollar = shoeCollar;
        this.productDTO = productDTO;
        this.colorDTO = colorDTO;
        this.sizeDTO = sizeDTO;
    }
}
