package com.example.backend.core.security.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsersDTO {
    private Long id;
    private String fullname;
    private String username;
    private String role;
    private String phone;
    private String email;
}
