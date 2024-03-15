package com.example.backend.core.view.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ImagesDTO {
    private Long id;
    private Long idProduct;
    private String imageName;
    private Instant createDate;


}
