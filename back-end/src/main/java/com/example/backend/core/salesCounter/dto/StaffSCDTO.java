package com.example.backend.core.salesCounter.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StaffSCDTO {
    private Long id;
    private String code;
    private String fullname;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String gender;
    private LocalDate createDate;
    private LocalDate updateDate;
    private String username;
    private String password;
    private Integer status;
    private Integer idel;
}
