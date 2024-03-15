package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.NoArgsConstructor;

import java.time.Instant;
@NoArgsConstructor
public class CategoryDTO {
    private Long id;
    private String name;
    private Instant createDate;
    private Instant updateDate;
    private Integer status;
    private Integer isDel;

    public CategoryDTO(Long id, String name, Instant createDate, Instant updateDate, Integer status, Integer isDel) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.isDel = isDel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }
}
