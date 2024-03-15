package com.example.backend.core.view.service;

import com.example.backend.core.view.dto.CustomerDTO;
import jakarta.mail.MessagingException;

public interface ForgotPassService {
    void sendMessageMailOTP(CustomerDTO customerDTO) throws MessagingException;
    public boolean verifyOTP(CustomerDTO customerDTO);
}
