package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SoleAdminDTO {
    private Long id;
    private String soleHeight;
    private String soleMaterial;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String description;
    private Integer status;
}
