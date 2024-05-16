package com.example.backend.core.salesCounter.dto;

import lombok.*;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerSCDTO {
    private Long id;
    private String code;
    private String fullname;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String gender;
    private String username;
}
