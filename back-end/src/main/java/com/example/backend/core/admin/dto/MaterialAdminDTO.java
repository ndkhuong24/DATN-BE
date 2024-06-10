package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MaterialAdminDTO {
    private Long id;
    private String name;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String description;
    private Integer status;
}
