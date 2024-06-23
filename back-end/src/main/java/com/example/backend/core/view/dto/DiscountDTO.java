package com.example.backend.core.view.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DiscountDTO implements Serializable {
    private Long id;
    private String code;
    private String name;
    private LocalDate createDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private Integer status;
    private Integer idel;
    private Integer delete;
}
