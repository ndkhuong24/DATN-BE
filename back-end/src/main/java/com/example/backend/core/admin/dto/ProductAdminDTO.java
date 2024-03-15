package com.example.backend.core.admin.dto;

import com.example.backend.core.commons.ExportDTO;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.core.commons.ExportDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductAdminDTO extends ExportDTO {

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
    private List<ProductDetailAdminDTO> productDetailAdminDTOList;
    private List<ImagesAdminDTO> imagesDTOList;
    private Integer idel;
    private ProductDetailAdminDTO productDetailAdminDTO;
    private StaffAdminDTO staffAdminDTO;
    private Integer totalQuantity;
    private BigDecimal reducePrice;
    private Integer percentageReduce;
    private String codeDiscount;
    private List<ProductDetailAdminDTO> productDetailDTOList;
    private BrandAdminDTO brandAdminDTO;
    private CategoryAdminDTO categoryAdminDTO;
    private MaterialAdminDTO materialAdminDTO;
    private SoleAdminDTO soleAdminDTO;
    private ImagesAdminDTO imagesAdminDTO;
    private String brandName;
    private String categoryName;
    private String materialName;
    private String sizeExport;
    private String colorExport;
    private String soleImport;;
    private String imagesExportErrors;
    private Set<String> sizeImport;
    private Set<String> colorImport;
    private Integer quantity;
    private String quantityExport;
    private Integer shoeCollarImport;
    private String shoeCollarExport;
    private String priceExport;
    private ColorAdminDTO colorDTO;
    private SizeAdminDTO sizeDTO;
    private String soleHeight;

    private String imageNameImport;
    private Integer totalBestSeller;

    public ProductAdminDTO(Long id, String code, String name,BigDecimal price,Integer totalQuantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.totalQuantity= totalQuantity;
    }

    public List<ProductDetailAdminDTO> getProductDetailDTOList() {
        return productDetailDTOList;
    }

    public void setProductDetailDTOList(List<ProductDetailAdminDTO> productDetailDTOList) {
        this.productDetailDTOList = productDetailDTOList;
    }
}
