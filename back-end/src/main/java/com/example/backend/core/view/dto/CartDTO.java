package com.example.backend.core.view.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDTO {
    private Long productId;
    private String productName;
    private ProductDetailDTO productDetailDTO;
    private Integer quantity;
    private ProductDTO productDTO;

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public ProductDetailDTO getProductDetailDTO() {
        return productDetailDTO;
    }

    public void setProductDetailDTO(ProductDetailDTO productDetailDTO) {
        this.productDetailDTO = productDetailDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
