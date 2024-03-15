package com.example.backend.core.salesCounter.dto;

import lombok.*;

import java.time.Instant;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class StaffSCDTO {
    private Long id;
    private String code;
    private String fullname;
    private Instant birthday;
    private String phone;
    private String email;
    private String gender;
    private Instant createDate;
    private Instant updateDate;
    private String username;
    private String password;
    private Integer status;
    private Integer idel;
}
