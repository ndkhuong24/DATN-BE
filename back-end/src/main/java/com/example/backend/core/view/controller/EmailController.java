package com.example.backend.core.view.controller;

import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/view/api")
public class EmailController {
    @Autowired
    private EmailService emailService;

    //    @PostMapping("/send-email-completeOrder")
//    public ResponseEntity<String> completePurchase(@RequestBody OrderDTO orderDTO) {
//        try {
//            emailService.sendMessageUsingThymeleafTemplate(orderDTO);
//            return ResponseEntity.ok("Purchase completed successfully!");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(500).body("Internal Server Error");
//        }
//    }
//
    @PostMapping("/send-email-from-customer")
    public ResponseEntity<?> sendEmailFromCustomer(@RequestBody OrderDTO orderDTO) {
        try {
            return ResponseEntity.ok(emailService.sendEmailFromCustomer(orderDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }

    @PostMapping("/send-email-completeOrder/not-login")
    public ResponseEntity<?> sendMailOrderNotLogin(@RequestBody OrderDTO orderDTO) {
        try {
            return ResponseEntity.ok(emailService.sendMailOrderNotLogin(orderDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Internal Server Error");
        }
    }
}
