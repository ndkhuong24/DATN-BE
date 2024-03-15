package com.example.backend.core.view.service;


import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.OrderDTO;
import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {
//    void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException;

    ServiceResult<String> sendMessageUsingThymeleafTemplate(OrderDTO orderDTO) throws MessagingException;
    ServiceResult<String> sendEmailFromCustomer(OrderDTO orderDTO) throws MessagingException;
    ServiceResult<?> sendMailOrderNotLogin(OrderDTO orderDTO) throws MessagingException;
}
