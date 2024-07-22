package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "id_color")
    private Long idColor;

    @Column(name = "id_size")
    private Long idSize;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "id_customer")
    private Long idCustomer;

}
