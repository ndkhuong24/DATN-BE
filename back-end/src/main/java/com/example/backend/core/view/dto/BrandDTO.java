package com.example.backend.core.view.dto;

import jakarta.persistence.Column;

import java.time.Instant;
import java.time.LocalDate;

public class BrandDTO {
    private Long id;
    private String name;
    private LocalDate createDate;
    private LocalDate updateDate;
    private int status;
    private int isDel;

    public BrandDTO() {
    }

    public BrandDTO(Long id, String name, LocalDate createDate, LocalDate updateDate, int status, int isDel) {
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

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }
}
