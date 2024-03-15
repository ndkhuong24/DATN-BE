package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SoleAdminDTO {
    private Long id;
    private String soleHeight;
    private String soleMaterial;
    private Instant createDate;
    private Instant updateDate;
    private String description;
    private Integer status;
}
