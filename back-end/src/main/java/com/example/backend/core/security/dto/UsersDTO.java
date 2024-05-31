package com.example.backend.core.security.dto;
import com.example.backend.core.security.entity.ERole;
import lombok.*;

import java.util.Date;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class UsersDTO {
    private Long id;
    private String fullname;
    private String username;
}
