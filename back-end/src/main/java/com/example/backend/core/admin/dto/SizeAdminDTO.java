package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SizeAdminDTO {
    private Long id;
    private String sizeNumber;
    private LocalDate createDate;
    private LocalDate updateDate;
    private Integer status;
}
