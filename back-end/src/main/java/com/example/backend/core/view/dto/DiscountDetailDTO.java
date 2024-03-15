package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class DiscountDetailDTO {
    private Long id;
    private Long idProduct;
    private Long idDiscount;
    private BigDecimal reducedValue;
    private Integer discountType;
    private Integer status;
}
