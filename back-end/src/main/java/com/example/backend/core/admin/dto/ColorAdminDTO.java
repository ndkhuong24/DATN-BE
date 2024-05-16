package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ColorAdminDTO {

    private Long id;
    private String code;
    private String name;
    private LocalDate createDate;

}
