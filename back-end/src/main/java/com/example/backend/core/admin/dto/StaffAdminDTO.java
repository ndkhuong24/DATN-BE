package com.example.backend.core.admin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffAdminDTO {
    private Long id;
    private String code;
    private String fullname;
    private LocalDate birthday;
    private String gender;
    //    private String address;
    private String phone;
    private String email;
    private LocalDateTime createDate;
    private String description;
    private String username;
    private String password;
    private String role;
    private Integer idel;
}
