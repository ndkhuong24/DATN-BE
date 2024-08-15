package com.example.backend.core.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@ToString
public class UsersDTO {
    private Long id;
    private String fullname;
    private String username;
    private String role;
    private String phone;
    private String email;
    private Integer status;

    public UsersDTO(Long id, String fullname, String username, String role, String phone, String email, Integer status) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.role = role;
        this.phone = phone;
        this.email = email;
        this.status = status;
    }

    public UsersDTO(Long id, String fullname, String username, String role, String phone, String email) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.role = role;
        this.phone = phone;
        this.email = email;
    }
}
