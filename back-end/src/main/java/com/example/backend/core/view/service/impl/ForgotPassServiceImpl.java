package com.example.backend.core.view.service.impl;

import com.example.backend.core.model.Customer;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.dto.OtpDTO;
import com.example.backend.core.view.repository.CustomerInforRepository;
import com.example.backend.core.view.service.ForgotPassService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import javax.naming.Context;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ForgotPassServiceImpl implements ForgotPassService {
    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    private String otp = "";

    private final Map<String, OtpDTO> otpMap = new ConcurrentHashMap<>();

   @Autowired
   private CustomerInforRepository customerInforRepository;

    public ForgotPassServiceImpl(JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }
    public void sendOTPEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        helper.setFrom("hunglqph20358@fpt.edu.vn");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, false);
        javaMailSender.send(message);
    }
    @Override
    @Async
    public void sendMessageMailOTP(CustomerDTO customerDTO) throws MessagingException{
        Customer customer = customerInforRepository.findByEmail(customerDTO.getEmail());
        if (customer != null){
            String emailTo = customer.getEmail();
            String subject = "OPT đặt lại mật khẩu của bạn là";
            this.otp = String.valueOf(Instant.now().getEpochSecond());
            otpMap.put(emailTo, new OtpDTO(this.otp, Instant.now()));
            sendOTPEmail(emailTo,subject,this.otp);
        }else {
            System.out.println("không có mail trong dữ liệu");
        }
    }
    @Override
    public boolean verifyOTP(CustomerDTO customerDTO) {
        OtpDTO otpData = otpMap.get(customerDTO.getEmail());
        if (otpData != null && otpData.isValid() && customerDTO.getOtp().equals(otpData.getOtp())) {
            otpMap.remove(customerDTO.getEmail());
            return true;
        }
        return false;
    }

}
