package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class SizeDTO {
    private Long id;
    private String sizeNumber;
    private LocalDateTime createDate;
    private Integer status;
}
