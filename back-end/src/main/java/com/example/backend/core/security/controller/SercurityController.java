package com.example.backend.core.security.controller;

import com.example.backend.core.security.config.custom.CustomUserDetailService;
import com.example.backend.core.security.config.custom.CustomUserDetails;
import com.example.backend.core.security.config.custom.CustomerUserDetalsService;
import com.example.backend.core.security.dto.UsersDTO;
import com.example.backend.core.security.dto.request.SignInRequet;
import com.example.backend.core.security.dto.request.SignUpRepquest;
import com.example.backend.core.security.dto.response.JwtResponse;
import com.example.backend.core.security.dto.response.MessageResponse;
import com.example.backend.core.security.entity.Users;
import com.example.backend.core.security.jwt.JwtEntryPoint;
import com.example.backend.core.security.jwt.JwtTokenProvider;
import com.example.backend.core.security.serivce.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

@RestController
@RequestMapping("/admin/api")
@CrossOrigin("*")
public class SercurityController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService usersService;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    public static int counter = 0;

    @PostMapping("/sign-up")
    public ResponseEntity<?> register(@Valid @RequestBody SignUpRepquest signUpFormRequest) {
        if (usersService.existsByUsername(signUpFormRequest.getUsername())) {
            return new ResponseEntity<>(new MessageResponse("The Username is existed"), HttpStatus.OK);
        }
        if (usersService.existsByEmail(signUpFormRequest.getEmail())) {
            return new ResponseEntity<>(new MessageResponse("The email is existed"), HttpStatus.OK);
        }
        if (usersService.existsByPhone(signUpFormRequest.getPhone())) {
            return new ResponseEntity<>(new MessageResponse("The phone is existed"), HttpStatus.OK);
        }
        counter++;
        Users users = Users.builder()
                .code("NV" + counter)
                .fullname(signUpFormRequest.getFullname())
                .gender(signUpFormRequest.getGender())
                .birthday(signUpFormRequest.getBirthday())
                .phone(signUpFormRequest.getPhone())
                .email(signUpFormRequest.getEmail())
                .createDate(LocalDate.now())
                .description(signUpFormRequest.getDescription())
                .idel(signUpFormRequest.getIdel())
                .username(signUpFormRequest.getUsername())
                .password(passwordEncoder.encode(signUpFormRequest.getPassword())).build();
        String strRoles = signUpFormRequest.getRole();
        String roles;
        try {
            if (strRoles.equalsIgnoreCase("ADMIN")) {
                roles = "ADMIN";
            } else if (strRoles.equalsIgnoreCase("STAFF")) {
                roles = "STAFF";
            } else {
                roles = "CUSTOMER";
            }
            users.setRole(roles);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new MessageResponse("Error occurred during registration"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        usersService.saveOrUpdate(users);
        return new ResponseEntity<>(new MessageResponse("Create Success"), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> login(@Valid @RequestBody SignInRequet signInRequet, HttpServletRequest request) {
        String uri = request.getRequestURI();
        UserDetails userDetails = customUserDetailService.loadUserByUsername(signInRequet.getUsername());
        if (userDetails != null) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            UsersDTO usersDTO = new UsersDTO();
            SecurityContextHolder.getContext().setAuthentication(authentication);
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
            String token = jwtTokenProvider.generateToken(customUserDetails);
            usersDTO = usersDTO.toStaffDTO(customUserDetails);
            System.out.println(usersDTO);
            return ResponseEntity.ok(new JwtResponse(token, usersDTO.toStaffDTO(customUserDetails)));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
