package com.example.backend.core.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SizeAdminDTO {
    private Long id;
    private String sizeNumber;
    private Instant createDate;
    private Integer status;
}
