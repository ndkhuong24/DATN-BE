package com.example.backend.core.security.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.Date;
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder @ToString
@Entity
@Table(name = "customer")
public class CustomerLogin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "fullname")
    private String fullname;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "phone")
    private String phone;
    @Column(name = "gender")
    private String gender;
    @Column(name = "email")
    private String email;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "update_date")
    private Date updateDate;
    @Column(name = "status")
    private Integer status;
    @Column(name = "idel")
    private Integer idel;
}
