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

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
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
        helper.setFrom("kn134646@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, false);
        javaMailSender.send(message);
    }

    @Override
    @Async
    public void sendMessageMailOTP(CustomerDTO customerDTO) throws MessagingException {
        Customer customer = customerInforRepository.findByEmail(customerDTO.getEmail());

        if (customer != null) {
            String emailTo = customer.getEmail();
            String subject = "Mã OTP (Vui lòng không cung cấp mã này cho ai khác)";
            String otp = generateRandomOTP(6); // Tạo mã OTP ngẫu nhiên gồm 6 chữ số
            LocalDateTime creationTime = LocalDateTime.now();
            otpMap.put(emailTo, new OtpDTO(otp, creationTime));

            // Tạo nội dung email
            String messageBody = String.format(
                    "Mã OTP của bạn là: %s%n" +
                            "Mã có hiệu lực trong vòng 15 phút xin hãy thực hiện trước khi hết hạn.",
                    otp
            );

            // Gửi email
            sendOTPEmail(emailTo, subject, messageBody);
        } else {
            System.out.println("không có mail trong dữ liệu");
        }
    }

//    @Override
//    @Async
//    public void sendMessageMailOTP(CustomerDTO customerDTO) throws MessagingException {
//        Customer customer = customerInforRepository.findByEmail(customerDTO.getEmail());
//
//        if (customer != null) {
//            String emailTo = customer.getEmail();
//            String subject = "Mã OTP (Vui lòng không cung cấp mã này cho ai khác)";
//            String otp = generateRandomOTP(6); // Tạo mã OTP ngẫu nhiên gồm 6 chữ số
//            LocalDateTime creationTime = LocalDateTime.now();
//            otpMap.put(emailTo, new OtpDTO(otp, creationTime));
//            sendOTPEmail(emailTo, subject, "Mã OTP của bạn là: " + otp);
//        } else {
//            System.out.println("không có mail trong dữ liệu");
//        }
//    }

    private String generateRandomOTP(int length) {
        String digits = "0123456789";
        Random random = new Random();
        StringBuilder otp = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            otp.append(digits.charAt(random.nextInt(digits.length())));
        }
        return otp.toString();
    }

    @Override
    public boolean verifyOTP(CustomerDTO customerDTO) {
        OtpDTO otpDTO = otpMap.get(customerDTO.getEmail());

        if (otpDTO != null && otpDTO.isValid() && customerDTO.getOtp().equals(otpDTO.getOtp())) {
            otpMap.remove(customerDTO.getEmail());
            return true;
        }

        return false;
    }
}
