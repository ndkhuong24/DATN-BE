package com.example.backend.core.view.dto;

import java.time.LocalDate;
import java.time.LocalDate;

public class OtpDTO {
    private final String otp;
    private final LocalDate creationTime;

    public OtpDTO(String otp, LocalDate creationTime) {
        this.otp = otp;
        this.creationTime = creationTime;
    }

    public String getOtp() {
        return otp;
    }

    public LocalDate getCreationTime() {
        return creationTime;
    }

    public boolean isValid() {
        return LocalDate.now().isBefore(creationTime.plusDays(360));
    }
}
