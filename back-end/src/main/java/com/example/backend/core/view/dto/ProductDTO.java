package com.example.backend.core.view.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
@Getter
@Setter
public class ProductDTO {

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
    private Long idSole;
    private String description;
    private Integer status;
    private List<ImagesDTO> imagesDTOList;
    private Integer idel;
    private BrandDTO brandDTO;
    private CategoryDTO categoryDTO;
    private MaterialDTO materialDTO;
    private SoleDTO soleDTO;
    private BigDecimal price;
    private BigDecimal reducePrice;
    private Integer percentageReduce;
    private String codeDiscount;
    private Integer totalQuantity;
    private List<ProductDetailDTO> productDetailDTOList;
    private BigDecimal totalSold;

    public List<ProductDetailDTO> getProductDetailDTOList() {
        return productDetailDTOList;
    }

    public void setProductDetailDTOList(List<ProductDetailDTO> productDetailDTOList) {
        this.productDetailDTOList = productDetailDTOList;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public Long getIdBrand() {
        return idBrand;
    }

    public void setIdBrand(Long idBrand) {
        this.idBrand = idBrand;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public Long getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Long idMaterial) {
        this.idMaterial = idMaterial;
    }

    public Long getIdSole() {
        return idSole;
    }

    public void setIdSole(Long idSole) {
        this.idSole = idSole;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    public BrandDTO getBrandDTO() {
        return brandDTO;
    }

    public void setBrandDTO(BrandDTO brandDTO) {
        this.brandDTO = brandDTO;
    }

    public CategoryDTO getCategoryDTO() {
        return categoryDTO;
    }

    public void setCategoryDTO(CategoryDTO categoryDTO) {
        this.categoryDTO = categoryDTO;
    }

    public MaterialDTO getMaterialDTO() {
        return materialDTO;
    }

    public void setMaterialDTO(MaterialDTO materialDTO) {
        this.materialDTO = materialDTO;
    }

    public SoleDTO getSoleDTO() {
        return soleDTO;
    }

    public void setSoleDTO(SoleDTO soleDTO) {
        this.soleDTO = soleDTO;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }


    public ProductDTO() {
    }

    public ProductDTO(Long id, String code, String name, Instant createDate, Instant updateDate, String description, Integer status, List<ImagesDTO> imagesDTOList, Integer idel) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.description = description;
        this.status = status;
        this.imagesDTOList = imagesDTOList;
        this.idel = idel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<ImagesDTO> getImagesDTOList() {
        return imagesDTOList;
    }

    public void setImagesDTOList(List<ImagesDTO> imagesDTOList) {
        this.imagesDTOList = imagesDTOList;
    }

    public Integer getIdel() {
        return idel;
    }

    public void setIdel(Integer idel) {
        this.idel = idel;
    }
}
