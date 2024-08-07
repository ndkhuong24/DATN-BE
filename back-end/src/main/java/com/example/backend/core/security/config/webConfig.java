package com.example.backend.core.security.config;

import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.security.config.custom.CustomUserDetailService;
import com.example.backend.core.security.config.custom.CustomerUserDetalsService;
import com.example.backend.core.security.jwt.JwtAuthenticationFillter;
import com.example.backend.core.security.jwt.JwtEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class webConfig{

    @Autowired
    public CustomUserDetailService customUserDetailService;

    @Autowired
    public CustomerUserDetalsService customerUserDetalsService;

    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtAuthenticationFillter jwtAuthenticationFilter(){
        return new JwtAuthenticationFillter();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager()throws Exception{
        return new ProviderManager(Arrays.asList(daoAuthenticationProvider()));
    }
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(c -> c.disable())
                .csrf(cf -> cf.disable())
                .authorizeHttpRequests(author -> {
                    try {
                        author
                                .requestMatchers("/view/anh/**").permitAll()
                                .requestMatchers("/api/sales-couter/vnpay-payment/**").permitAll()
                                .requestMatchers("/api/admin/staff/update/**").permitAll()
                                .requestMatchers("/api/admin/images/upload").permitAll()
                                .requestMatchers("/api/admin/images/update").permitAll()
                                .requestMatchers("/view/api/sign-in").permitAll()
                                .requestMatchers("/view/api/sign-up").permitAll()
                                .requestMatchers("/admin/api/sign-in").permitAll()
                                .requestMatchers("/admin/api/sign-up").permitAll()
                                .requestMatchers("/view/api/**").permitAll()
                                .requestMatchers(AppConstant.API_VIEW_PERMIT).permitAll()
//                                .requestMatchers(AppConstant.API_ADMIN).hasAnyAuthority("ADMIN")
//                                .requestMatchers(AppConstant.API_STAFF).hasAnyAuthority("STAFF")
                                .requestMatchers(AppConstant.API_STAFF).permitAll()
                                .requestMatchers(AppConstant.API_ADMIN).permitAll()
                                .anyRequest().authenticated(); // Thay .permitAll() bằng .authenticated() cho các request còn lại
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                })
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.cors(c -> c.disable()).csrf(cf -> cf.disable());
//        http.authorizeHttpRequests(author -> {
//            try {
//                 author.requestMatchers("/view/api/sign-in").permitAll()
//                        .requestMatchers("/view/api/sign-up").permitAll()
//                        .requestMatchers("/admin/api/sign-in").permitAll()
//                        .requestMatchers("/admin/api/sign-up").permitAll()
//                        .requestMatchers(AppConstant.API_VIEW_PERMIT).permitAll()
//                        .requestMatchers(AppConstant.API_ADMIN).permitAll()
//                        .requestMatchers(AppConstant.API_STAFF).permitAll()
//                //.anyRequest().permitAll()
//                .and().exceptionHandling()
//                        .authenticationEntryPoint(jwtEntryPoint).and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//            } catch (Exception e) {
//                e.printStackTrace();
//                throw new RuntimeException(e);
//            }
//        });
//        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }

}
