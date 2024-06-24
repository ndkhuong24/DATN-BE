package com.example.backend.core.view.dto;

import java.time.LocalDate;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OtpDTO {
    private final String otp;
    private final LocalDateTime creationTime;

    public OtpDTO(String otp, LocalDateTime creationTime) {
        this.otp = otp;
        this.creationTime = creationTime;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public boolean isValid() {
        return LocalDateTime.now().isBefore(creationTime.plusDays(360));
    }
}
