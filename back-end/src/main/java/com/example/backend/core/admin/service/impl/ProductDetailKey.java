package com.example.backend.core.admin.service.impl;

import java.util.Objects;

public class ProductDetailKey {
    private Long idProduct;
    private Long idColor;
    private Long idSize;

    public ProductDetailKey(Long idProduct, Long idColor, Long idSize) {
        this.idProduct = idProduct;
        this.idColor = idColor;
        this.idSize = idSize;
    }

    public Long getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Long idProduct) {
        this.idProduct = idProduct;
    }

    public Long getIdColor() {
        return idColor;
    }

    public void setIdColor(Long idColor) {
        this.idColor = idColor;
    }

    public Long getIdSize() {
        return idSize;
    }

    public void setIdSize(Long idSize) {
        this.idSize = idSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetailKey that = (ProductDetailKey) o;
        return Objects.equals(idProduct, that.idProduct) &&
                Objects.equals(idColor, that.idColor) &&
                Objects.equals(idSize, that.idSize);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idProduct, idColor, idSize);
    }
}
