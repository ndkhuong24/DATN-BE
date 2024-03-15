package com.example.backend.core.security.dto;


import com.example.backend.core.security.config.custom.CustomUserDetails;
import com.example.backend.core.security.config.custom.CustomerUserDetails;
import lombok.*;

import java.util.Date;


@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@ToString
public class UsersDTO {
    private Long id;
    private String code;
    private String fullname;
    private Date birthday;
    private String gender;
    private String phone;
    private String email;
    private String username;
    private String isdn;
    private String role;
    public UsersDTO toStaffDTO(CustomUserDetails customUserDetails){
        this.setId(customUserDetails.getId());
        this.setCode(customUserDetails.getCode());
        this.setFullname(customUserDetails.getFullname());
        this.setBirthday(customUserDetails.getBirthday());
        this.setGender(customUserDetails.getGender());
        this.setUsername(customUserDetails.getUsername());
        this.setEmail(customUserDetails.getEmail());
        this.setPhone(customUserDetails.getPhone());
        this.setIsdn(customUserDetails.getIsdn());
        this.setRole(customUserDetails.getRole());
        return  UsersDTO.this;
    }
    public UsersDTO toCustomerDTO(CustomerUserDetails customerUserDetails){
        this.setId(customerUserDetails.getId());
        this.setCode(customerUserDetails.getCode());
        this.setFullname(customerUserDetails.getFullname());
        this.setBirthday(customerUserDetails.getBirthday());
        this.setGender(customerUserDetails.getGender());
        this.setUsername(customerUserDetails.getUsername());
        this.setEmail(customerUserDetails.getEmail());
        this.setPhone(customerUserDetails.getPhone());
        return  UsersDTO.this;
    }
}
