package com.example.backend.core.commons;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ResponseImportDTO {
    private Integer numberErrors;
    private Integer numberSuccess;
    private Integer total;
    private List<?> listSuccess = new ArrayList<>();
    private List<?> listErrors = new ArrayList<>();
    private HttpStatus status;
    private String message;

    public ResponseImportDTO(Integer numberErrors, Integer numberSuccess, Integer total) {
        this.numberErrors = numberErrors;
        this.numberSuccess = numberSuccess;
        this.total = total;
    }
}
