package com.example.backend.core.view.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class PaymentResponse {
    private HttpStatus status;
    private String message;
    private String url;
}
