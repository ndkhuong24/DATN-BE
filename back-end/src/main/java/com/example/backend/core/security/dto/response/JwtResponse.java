package com.example.backend.core.security.dto.response;

import com.example.backend.core.security.dto.UsersDTO;
import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private UsersDTO usersDTO;

    public JwtResponse(String token, UsersDTO usersDTO) {
        this.token = token;
        this.usersDTO = usersDTO;
    }
}
