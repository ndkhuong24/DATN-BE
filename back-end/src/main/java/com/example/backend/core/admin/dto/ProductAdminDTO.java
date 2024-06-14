package com.example.backend.core.admin.dto;

import com.example.backend.core.commons.ExportDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductAdminDTO extends ExportDTO {
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

    private List<ProductDetailAdminDTO> productDetailAdminDTOList;
    private List<ImagesAdminDTO> imagesDTOList;
    private String imageURL;

    private ProductDetailAdminDTO productDetailAdminDTO;
    private ImagesAdminDTO imagesAdminDTO;

    private BrandAdminDTO brandAdminDTO;
    private CategoryAdminDTO categoryAdminDTO;
    private MaterialAdminDTO materialAdminDTO;
    private SoleAdminDTO soleAdminDTO;

    private String brandName;
    private String categoryName;
    private String materialName;

    private Integer totalBestSeller;

    // Additional constructor
    public ProductAdminDTO(Long id, String code, String name, Integer totalQuantity) {
        this.id = id;
        this.code = code;
        this.name = name;
//        this.totalQuantity = totalQuantity;
    }

    // Getters and setters for the lists
    public List<ProductDetailAdminDTO> getProductDetailAdminDTOList() {
        return productDetailAdminDTOList;
    }

    public void setProductDetailAdminDTOList(List<ProductDetailAdminDTO> productDetailAdminDTOList) {
        this.productDetailAdminDTOList = productDetailAdminDTOList;
    }

    public List<ImagesAdminDTO> getImagesDTOList() {
        return imagesDTOList;
    }

    public void setImagesDTOList(List<ImagesAdminDTO> imagesDTOList) {
        this.imagesDTOList = imagesDTOList;
    }

//    public void setSoleAdminDTO(SoleAdminDTO soleAdminDTO) {
//
//    }

//    private Long id;
//    private String code;
//    private String name;

//    private LocalDate createDate;
//    private LocalDate updateDate;

//    private String createName;
//    private String updateName;

//    private Long idBrand;
//    private Long idCategory;
//    private Long idMaterial;
//    private BigDecimal price;
//    private Long idSole;
//    private String description;
//    private Integer status;

//    private List<ProductDetailAdminDTO> productDetailAdminDTOList;
//    private List<ImagesAdminDTO> imagesDTOList;

//    private Integer idel;

//    private ProductDetailAdminDTO productDetailAdminDTO;

//    private StaffAdminDTO staffAdminDTO;

//    private Integer totalQuantity;

//    private BigDecimal reducePrice;

//    private Integer percentageReduce;

//    private String codeDiscount;

//    private List<ProductDetailAdminDTO> productDetailDTOList;

//    private BrandAdminDTO brandAdminDTO;
//    private CategoryAdminDTO categoryAdminDTO;
//    private MaterialAdminDTO materialAdminDTO;
//    private SoleAdminDTO soleAdminDTO;

//    private ImagesAdminDTO imagesAdminDTO;

//    private String brandName;
//    private String categoryName;
//    private String materialName;

//    private String sizeExport;
//    private String colorExport;
//    private String soleImport;;

//    private String imagesExportErrors;
//    private Set<String> sizeImport;
//    private Set<String> colorImport;

//    private Integer quantity;

//    private String quantityExport;
//    private Integer shoeCollarImport;
//    private String shoeCollarExport;
//    private String priceExport;

//    private ColorAdminDTO colorDTO;
//    private SizeAdminDTO sizeDTO;

//    private String soleHeight;
//
//    private String imageNameImport;
//    private Integer totalBestSeller;
//
//    public ProductAdminDTO(Long id, String code, String name,BigDecimal price,Integer totalQuantity) {
//        this.id = id;
//        this.code = code;
//        this.name = name;
//        this.price = price;
//        this.totalQuantity= totalQuantity;
//    }
//
//    public List<ProductDetailAdminDTO> getProductDetailDTOList() {
//        return productDetailDTOList;
//    }
//
//    public void setProductDetailDTOList(List<ProductDetailAdminDTO> productDetailDTOList) {
//        this.productDetailDTOList = productDetailDTOList;
//    }
}
