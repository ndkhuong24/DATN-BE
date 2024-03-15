package com.example.backend.core.security.jwt;

import com.example.backend.core.security.config.custom.CustomUserDetailService;
import com.example.backend.core.security.config.custom.CustomerUserDetalsService;
import com.example.backend.core.security.serivce.CustomerLoginService;
import com.example.backend.core.security.serivce.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
@Slf4j
public class JwtAuthenticationFillter extends OncePerRequestFilter {
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CustomerUserDetalsService customerUserDetailService;

    @Autowired
    private CustomerLoginService customerSPService;

    @Autowired
    private UserService userService;

    private String getJwtFromRequest(HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            return authHeader.replace("Bearer", "");
        }
        return null;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)){
                //lay userName tu chuoi jwt
                String userName = jwtTokenProvider.getUserNameFromJwt(jwt);
                //lay thong tin nguoi dung tu userName
                String uri = request.getRequestURI();
                if (uri.contains("view")){
                    UserDetails userDetails = customerUserDetailService.loadUserByUsername(userName);
                    if(userDetails != null){
                        // Neu nguoi dung hop le set thong tin cho security context
                        UsernamePasswordAuthenticationToken authentication
                                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
                if (uri.contains("admin")){
                    UserDetails userDetails = customUserDetailService.loadUserByUsername(userName);
                    if(userDetails != null){
                        // Neu nguoi dung hop le set thong tin cho security context
                        UsernamePasswordAuthenticationToken authentication
                                = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }

            }
        }catch (Exception ex){
            log.error("fail on set user authentication", ex);
        }
        filterChain.doFilter(request,response);
    }
}
