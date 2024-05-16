package com.example.backend.core.view.dto;


import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MaterialDTO {
    private Long id;
    private String name;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String description;
    private Integer status;

}
