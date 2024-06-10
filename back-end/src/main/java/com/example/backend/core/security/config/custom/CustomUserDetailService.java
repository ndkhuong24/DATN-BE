package com.example.backend.core.security.config.custom;

import com.example.backend.core.security.entity.Users;
import com.example.backend.core.security.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Khong Tim Thay User");
        }
        UserDetails userDetails;
        // lấy quyền của người dùng
        Set<SimpleGrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(user.getRole()));
        userDetails = new User(user.getUsername(), user.getPassword(), authorities);
        System.out.println(userDetails + "hehe");
        return userDetails;
    }
}
