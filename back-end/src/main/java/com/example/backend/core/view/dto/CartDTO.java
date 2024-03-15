package com.example.backend.core.view.dto;



public class CartDTO {
    private Long productId;
    private String productName;
    private String imageName;
    private ProductDetailDTO productDetailDTO;
    private Integer quantity;
    private ProductDTO productDTO;

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    public CartDTO() {
    }

    public CartDTO(Long productId, String productName, ProductDetailDTO productDetailDTO, Integer quantity) {
        this.productId = productId;
        this.productName = productName;
        this.productDetailDTO = productDetailDTO;
        this.quantity = quantity;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
