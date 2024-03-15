package com.example.backend.core.salesCounter.controller;

import com.example.backend.core.salesCounter.service.CustomerSCService;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales-customer")
@CrossOrigin("*")
public class CustomerByPhoneController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerSCService customerSCService;

    @GetMapping("/findByPhone/{phone}")
    public ResponseEntity<?> findCustomerByPhone(
        @PathVariable("phone") String phone
    ){
        return ResponseEntity.ok(customerService.findByPhoneLike(phone));
    }
    @PostMapping("/add-customer")
    public ResponseEntity<?> addCustomer(
            @RequestBody CustomerDTO customerDTO
            ){
        return ResponseEntity.ok(customerSCService.addCustomer(customerDTO));
    }
}
