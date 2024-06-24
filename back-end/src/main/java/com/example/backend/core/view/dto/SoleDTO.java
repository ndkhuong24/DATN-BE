package com.example.backend.core.view.dto;



import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String description;
    private Integer status;



}
