package com.example.backend.core.security.jwt;

import com.example.backend.core.security.config.custom.CustomUserDetailService;
import com.example.backend.core.security.config.custom.CustomerUserDetalsService;
import com.example.backend.core.security.serivce.CustomerLoginService;
import com.example.backend.core.security.serivce.UserService;
import io.jsonwebtoken.ExpiredJwtException;
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
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private CustomerUserDetalsService customerUserDetailService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization"); // lấy tiêu đề authorization từ request
        String username = null;
        String jwtToken = null;

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            try {
                //lấy tên người dùng từ token
                username = jwtUtils.getUsernameFromToken(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        }
        //kiểm tra xem người dùng đã được xác thực và được đặt vào SecurityContextHolder chưa
        // không hợp lệ sẽ chuyển tiếp đến doFilter lọc tiếp theo
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //tìm kiếm thông tin người dùng  từ csdl
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(username);

            System.out.println(userDetails.getAuthorities());

            //kiểm tra xem token có hợp lệ khoong
            if (jwtUtils.validateToken(jwtToken, userDetails)) {
                //tạo đối tượng với userdetail và danh sách quyền truy cập của người dùng
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                //đặt đối tượng vào SecurityContextHolder để xác thực
                //SecurityContextHolder chứa thông tin về người dùng đang được xác thực
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response); //chuyển tiếp request đến các filter khác
    }
}
