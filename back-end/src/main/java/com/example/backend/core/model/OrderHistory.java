package com.example.backend.core.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "order_history")
public class OrderHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "id_order")
    private Long idOrder;
    @Column(name = "id_staff")
    private Long idStaff;
    @Column(name = "id_customer")
    private Long idCustomer;
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_date")
    private Instant createDate;
    @Column(name = "note")
    private String note;
}
