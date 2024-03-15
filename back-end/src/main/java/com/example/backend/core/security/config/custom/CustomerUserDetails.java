package com.example.backend.core.security.config.custom;

import com.example.backend.core.model.Customer;
import com.example.backend.core.security.entity.CustomerLogin;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUserDetails implements UserDetails {
    private Long id;
    private String code;
    private String fullname;
    private Date birthday;
    private String gender;
    private String phone;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    public Collection<?extends GrantedAuthority> authorities;

    public CustomerUserDetails(Long id, String code, String fullname, Date birthday, String gender, String phone, String email, String username, String password) {
        this.id = id;
        this.code = code;
        this.fullname = fullname;
        this.gender = gender;
        this.birthday = birthday;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public static CustomerUserDetails mapCustomerToUserDetail(CustomerLogin customer){
        return new CustomerUserDetails(
               customer.getId(),
                customer.getCode(),
                customer.getFullname(),
                customer.getBirthday(),
                customer.getGender(),
                customer.getPhone(),
                customer.getEmail(),
                customer.getUsername(),
                customer.getPassword()
        );
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
