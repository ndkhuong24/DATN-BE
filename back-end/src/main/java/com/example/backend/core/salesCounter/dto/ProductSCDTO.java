package com.example.backend.core.salesCounter.dto;

import com.example.backend.core.admin.dto.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ProductSCDTO {
    private Long id;
    private String code;
    private String name;
    private Instant createDate;
    private Instant updateDate;
    private String createName;
    private String updateName;
    private Long idBrand;
    private Long idCategory;
    private Long idMaterial;
    private BigDecimal price;
    private Long idSole;
    private String description;
    private Integer status;
    private List<ImagesAdminDTO> imagesDTOList;
    private Integer idel;
    private StaffAdminDTO staffAdminDTO;
    private BigDecimal reducePrice;
    private Integer percentageReduce;
    private String codeDiscount;
    private Integer totalQuantity;
    private List<ProductDetailAdminDTO> productDetailDTOList;
    private BrandAdminDTO brandAdminDTO;
    private CategoryAdminDTO categoryAdminDTO;
    private MaterialAdminDTO materialAdminDTO;
    private SoleAdminDTO soleAdminDTO;
    private ProductDetailAdminDTO productDetailAdminDTO;
    private ImagesAdminDTO imagesAdminDTO;
    private String brandName;
    private String categoryName;
    private String materialName;
    private String soleHeight;
    private String imageNameImport;
    private Integer totalBestSeller;
}
