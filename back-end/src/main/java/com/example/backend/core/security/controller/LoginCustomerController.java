package com.example.backend.core.security.controller;

import com.example.backend.core.security.config.custom.CustomerUserDetalsService;
import com.example.backend.core.security.dto.UsersDTO;
import com.example.backend.core.security.dto.request.SignInRequet;
import com.example.backend.core.security.dto.request.SignUpRepquest;
import com.example.backend.core.security.dto.response.JwtResponse;
import com.example.backend.core.security.dto.response.MessageResponse;
import com.example.backend.core.security.entity.CustomerLogin;
import com.example.backend.core.security.entity.ERole;
import com.example.backend.core.security.jwt.JwtEntryPoint;
import com.example.backend.core.security.jwt.JwtUtils;
import com.example.backend.core.security.serivce.CustomerLoginService;
import com.example.backend.core.security.serivce.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Date;

@RestController
@RequestMapping("/view/api")
@CrossOrigin("*")
public class LoginCustomerController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    CustomerLoginService customerSPService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Autowired
    private CustomerUserDetalsService customerUserDetalsService;
    @Autowired
    UserService usersService;
    @Autowired
    CustomerLoginService customerService;
    @PostMapping("/sign-up")
    public ResponseEntity<?> signUpCustomer(@Valid @RequestBody SignUpRepquest signUpFormRequest){
        if (customerSPService.existsByUsername(signUpFormRequest.getUsername())){
            return new ResponseEntity<>(new MessageResponse("The Username is existed"), HttpStatus.OK);
        }
        if (customerSPService.existsByEmail(signUpFormRequest.getEmail())){
            return new ResponseEntity<>(new MessageResponse("The Email is existed"), HttpStatus.OK);
        }
        if (customerSPService.existsByPhone(signUpFormRequest.getPhone())){
            return new ResponseEntity<>(new MessageResponse("The Phone is existed"), HttpStatus.OK);
        }
        CustomerLogin customer = CustomerLogin.builder()
                .code("KH" + Instant.now().getEpochSecond())
                .fullname(signUpFormRequest.getFullname())
                .gender(signUpFormRequest.getGender())
                .phone(signUpFormRequest.getPhone())
                .email(signUpFormRequest.getEmail())
                .birthday(signUpFormRequest.getBirthday())
                .createDate(new Date())
                .username(signUpFormRequest.getUsername())
                .status(0)
                .idel(0)
                .password(passwordEncoder.encode(signUpFormRequest.getPassword())).build();
        customerSPService.saveCustomer(customer);
        return new ResponseEntity<>(new MessageResponse("Create Success"), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> loginCus(@Valid @RequestBody SignInRequet signInRequet, HttpServletRequest request){
        String uri = request.getRequestURI();
        if (uri.contains("view")){
            UserDetails userDetails = customerUserDetalsService.loadUserByUsername(signInRequet.getUsername());
            if(userDetails != null){
                if (passwordEncoder.matches(signInRequet.getPassword(), userDetails.getPassword())) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    UserDetails customerUserDetails = (UserDetails) authentication.getPrincipal();
                    String token = jwtUtils.generateToken(customerUserDetails);
                    CustomerLogin customer = customerService.findByUsername(signInRequet.getUsername());
                    return ResponseEntity.ok(new JwtResponse(token,new UsersDTO(customer.getId(), customer.getFullname(),userDetails.getUsername())));

                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            return ResponseEntity.ok(new MessageResponse("thành công"));
        }else{
            return ResponseEntity.ok(new MessageResponse("Bạn không có quyền truy cập"));
        }

    }
}
