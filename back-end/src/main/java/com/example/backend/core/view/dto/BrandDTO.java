package com.example.backend.core.view.dto;

import java.time.LocalDateTime;

public class BrandDTO {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private int status;
    private int isDel;

    public BrandDTO() {
    }

    public BrandDTO(Long id, String name, LocalDateTime createDate, LocalDateTime updateDate, int status, int isDel) {
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

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDateTime updateDate) {
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
