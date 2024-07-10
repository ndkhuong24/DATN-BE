package com.example.backend.core.view.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ColorDTO {
    private Long id;
    private String code;
    private String name;
    private LocalDateTime createDate;
}
