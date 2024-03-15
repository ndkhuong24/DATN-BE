package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ColorAdminDTO {

    private Long id;
    private String code;
    private String name;
    private Instant createDate;

}
