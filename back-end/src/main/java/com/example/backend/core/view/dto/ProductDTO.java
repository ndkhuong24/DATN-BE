package com.example.backend.core.view.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductDTO {
    private Long id;
    private String code;
    private String name;
    private LocalDate createDate;
    private LocalDate updateDate;

    private Long idBrand;
    private Long idCategory;
    private Long idMaterial;
    private Long idSole;
    private String description;
    private Integer status;

    private List<ProductDetailDTO> productDetailDTOList;
    private List<ImagesDTO> imagesDTOList;

    private String imageURL;

    private ProductDetailDTO productDetailDTO;
    private ImagesDTO imagesDTO;

    private BrandDTO brandDTO;
    private CategoryDTO categoryDTO;
    private MaterialDTO materialDTO;
    private SoleDTO soleDTO;

    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private BigDecimal reducePrice;//giảm giá
    private Integer percentageReduce;//phần trăm giảm
    private String codeDiscount;//mã giảm gia
    private Integer totalQuantity;//tong so luong

    private BigDecimal totalSold;//tong luot bat

    public List<ProductDetailDTO> getProductDetailDTOList() {
        return productDetailDTOList;
    }

    public List<ImagesDTO> getImagesDTOList() {
        return imagesDTOList;
    }

    public void setProductDetailDTOList(List<ProductDetailDTO> productDetailDTOList) {
        this.productDetailDTOList = productDetailDTOList;
    }

    public void setImagesDTOList(List<ImagesDTO> imagesDTOList) {
        this.imagesDTOList = imagesDTOList;
    }
}
