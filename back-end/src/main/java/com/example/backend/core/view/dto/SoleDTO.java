package com.example.backend.core.view.dto;



import lombok.*;

import java.time.LocalDate;

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
    private LocalDate createDate;
    private LocalDate updateDate;
    private String description;
    private Integer status;



}
