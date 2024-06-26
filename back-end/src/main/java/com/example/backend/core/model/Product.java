package com.example.backend.core.model;

import com.example.backend.core.admin.dto.ProductAdminDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "product")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "create_date")
    private LocalDateTime createDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "id_brand")
    private Long idBrand;

    @Column(name = "id_category")
    private Long idCategory;

    @Column(name = "id_material")
    private Long idMaterial;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status;

    @Column(name = "id_sole")
    private Long idSole;

    public Product(ProductAdminDTO dto) {
        this.id = dto.getId();
        this.code = dto.getCode();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.createDate = dto.getCreateDate();
        this.idBrand = dto.getIdBrand();
        this.idCategory = dto.getIdCategory();
        this.idMaterial = dto.getIdMaterial();
        this.idSole = dto.getIdSole();
        this.status = 0;
    }
}
