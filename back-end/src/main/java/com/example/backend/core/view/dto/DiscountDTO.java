package com.example.backend.core.view.dto;


import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DiscountDTO implements Serializable {
    private Long id;
    private String code;
    private String name;
    private LocalDate createDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String createName;
    private Integer status;
    private Integer idel;
    private Integer delete;
    private BigDecimal reducedValue; //giá trị giảm
    private BigDecimal maxReduced; //giá trị giảm tối
    private Integer discountType; //loại giảm giá

    private List<ProductDTO> productDTOList;
    private List<ProductDetailDTO> productDetailDTOList;
}
