package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SizeAdminDTO {
    private Long id;
    private String sizeNumber;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private Integer status;
}
