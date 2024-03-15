package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String code;
    private String fullname;
    private Date birthday;
    private String phone;
    private String email;
    private String gender;
    private Instant createDate;
    private Instant updateDate;
    private String username;
    private String password;
    private Integer status;
    private Integer idel;
    private String newPass;
    private String otp;
}
