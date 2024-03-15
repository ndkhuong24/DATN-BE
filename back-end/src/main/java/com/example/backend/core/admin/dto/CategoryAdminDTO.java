package com.example.backend.core.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryAdminDTO {
    private Long id;
    private String name;
    private Instant createDate;
    private Instant updateDate;
    private Integer status;
    private Integer isDel;
}
