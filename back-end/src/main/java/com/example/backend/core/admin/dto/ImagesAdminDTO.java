package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ImagesAdminDTO {
    private Long id;
    private Long idProduct;
    private String imageName;
    private LocalDate createDate;
    private byte[] image;
}
