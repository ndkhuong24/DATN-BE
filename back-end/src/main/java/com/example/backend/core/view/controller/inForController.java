package com.example.backend.core.view.controller;

import com.example.backend.core.model.Customer;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.service.CustomerInforSerivce;
import com.example.backend.core.view.service.CustomerService;
import com.example.backend.core.view.service.ForgotPassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("/view/api")
public class inForController {
    @Autowired
    private CustomerService  customerService;

    @Autowired
    private ForgotPassService forgotPassService;

    @Autowired
    private CustomerInforSerivce customerInforSerivce;
    @PutMapping("/update-infor/{id}")
    public ResponseEntity<?> updateinFor(
            @PathVariable("id") Customer  customer,
            @RequestBody CustomerDTO customerDTO
            ){
        return ResponseEntity.ok(this.customerService.updateInforCustomer(customerDTO, customer));
    }
    @PutMapping("/changePass/{id}")
    public ResponseEntity<?> changePass(
            @PathVariable("id") Customer customer,
            @RequestBody CustomerDTO customerDTO
    ){
        return ResponseEntity.ok(this.customerInforSerivce.updatePassword(customerDTO, customer));
    }
    @PostMapping("/send-mail-otp")
    public ResponseEntity<Map<String, String>> sendMailOtpl(@RequestBody CustomerDTO customerDTO) {
        Map<String, String> response = new HashMap<>();

        try {
            forgotPassService.sendMessageMailOTP(customerDTO);
            response.put("message", "send mail successfully");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<Map<String, String>> verifyOTP(
            @RequestBody CustomerDTO customerDTO
    ) {
        Map<String, String> response = new HashMap<>();
        if (forgotPassService.verifyOTP(customerDTO)) {
            response.put("message", "send mail successfully");
            response.put("status", "200");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            response.put("message", "Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PostMapping("/reset-pass")
    public ResponseEntity<?> resetPass(
        @RequestBody CustomerDTO customerDTO
    ){
        return ResponseEntity.ok(this.customerInforSerivce.resetPassword(customerDTO));
    }
}
