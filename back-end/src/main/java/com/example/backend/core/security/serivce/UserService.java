package com.example.backend.core.security.serivce;


import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.security.dto.request.SignUpRepquest;
import com.example.backend.core.security.entity.Users;



public interface UserService {
    public Users findByUsername(String userName);
    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    String findByRole(String role);
    Users saveOrUpdate(Users users);
    boolean isUser(String username);

}
