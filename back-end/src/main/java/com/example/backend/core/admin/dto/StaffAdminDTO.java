package com.example.backend.core.admin.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StaffAdminDTO {
    private Long id;
    private String code;
    private String fullname;
    private Date birthday;
    private String gender;
    private String address;
    private String phone;
    private String email;
    private Instant createDate;
    private String description;
    private String username;
    private String passwword;
    private String role;
    private Integer status;
    private Integer idel;
    private String isdn;
}
