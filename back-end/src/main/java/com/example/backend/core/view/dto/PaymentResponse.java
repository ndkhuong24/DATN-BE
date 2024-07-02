package com.example.backend.core.view.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.http.HttpStatus;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentResponse {
    private HttpStatus status;
    private String message;
    private String url;
}
