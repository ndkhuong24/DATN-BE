package com.example.backend.core.view.dto;



import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SoleDTO {
    private Long id;
    private String soleHeight;
    private String soleMaterial;
    private Instant createDate;
    private Instant updateDate;
    private String description;
    private Integer status;



}
