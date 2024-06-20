package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "discount")
public class Discount {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "description")
    private String description;

    @Column(name = "create_name")
    private String createName;

    @Column(name = "status")
    private Integer status;

    @Column(name = "idel")
    private Integer idel;

    @Column(name = "dele")
    private Integer delete;

    @Column(name="reduced_value")
    private BigDecimal reducedValue; //giá trị giảm

    @Column(name = "max_reduced")
    private BigDecimal maxReduced; //giá trị giảm tối

    @Column(name = "discount_type")
    private Integer discountType; //loại giảm giá
}
