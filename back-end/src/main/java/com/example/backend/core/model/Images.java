package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "images")
public class Images implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_product")
    private Long idProduct;

    @Column(name = "image_name")
    private String imageName;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "image", unique = false, nullable = false, length = 10000000)
    private byte[] image;
}
