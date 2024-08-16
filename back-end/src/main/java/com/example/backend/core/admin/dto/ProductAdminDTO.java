package com.example.backend.core.admin.dto;

import com.example.backend.core.commons.ExportDTO;
import com.example.backend.core.model.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductAdminDTO extends ExportDTO {
    private Long id;
    private String code;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    private Long idBrand;
    private Long idCategory;
    private Long idMaterial;
    private Long idSole;
    private String description;
    private Integer status;

    private List<ProductDetailAdminDTO> productDetailAdminDTOList;
    private List<ProductDetail>productDetailList;

    private String imageURL;

    private BrandAdminDTO brandAdminDTO;
    private CategoryAdminDTO categoryAdminDTO;
    private MaterialAdminDTO materialAdminDTO;
    private SoleAdminDTO soleAdminDTO;

    private String brandName;
    private String categoryName;
    private String materialName;

    private Integer totalBestSeller;

    public ProductAdminDTO(Long id, String code, String name, Integer totalQuantity) {
        this.id = id;
        this.code = code;
        this.name = name;
    }

    public List<ProductDetailAdminDTO> getProductDetailAdminDTOList() {
        return productDetailAdminDTOList;
    }

    public void setProductDetailAdminDTOList(List<ProductDetailAdminDTO> productDetailAdminDTOList) {
        this.productDetailAdminDTOList = productDetailAdminDTOList;
    }

    public List<ProductDetail> getProductDetailList() {
        return productDetailList;
    }

    public void setProductDetailList(List<ProductDetail> productDetailList) {
        this.productDetailList = productDetailList;
    }
}
