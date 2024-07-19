package com.example.backend.core.view.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerDTO {
    private Long id;
    private String code;
    private String fullname;
    private LocalDate birthday;
    private String phone;
    private String email;
    private String gender;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String username;
    private String password;
    private Integer status;
    private Integer idel;
    private String newPass;
    private String otp;
}
