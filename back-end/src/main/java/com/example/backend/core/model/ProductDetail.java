package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "product_detail")
public class ProductDetail implements Serializable {
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
    @Column(name = "shoe_collar")
    private Integer shoeCollar;

}
