package com.example.backend.core.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryAdminDTO {
    private Long id;
    private String name;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Integer status;
    private Integer isDel;
}
