package com.example.backend.core.view.dto;

import java.time.Instant;

public class OtpDTO {
    private final String otp;
    private final Instant creationTime;

    public OtpDTO(String otp, Instant creationTime) {
        this.otp = otp;
        this.creationTime = creationTime;
    }

    public String getOtp() {
        return otp;
    }

    public Instant getCreationTime() {
        return creationTime;
    }

    public boolean isValid() {
        return Instant.now().isBefore(creationTime.plusSeconds(300));
    }
}
