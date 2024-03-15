package com.example.backend.core.salesCounter.dto;

import lombok.*;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomerSCDTO {
    private Long id;
    private String code;
    private String fullname;
    private Instant birthday;
    private String phone;
    private String email;
    private String gender;
    private String username;
}
