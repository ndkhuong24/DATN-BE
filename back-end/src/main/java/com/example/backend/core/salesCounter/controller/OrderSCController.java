package com.example.backend.core.salesCounter.controller;

import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.admin.service.OrderAdminService;
import com.example.backend.core.salesCounter.dto.OrderSalesDTO;
import com.example.backend.core.salesCounter.service.OrderSalesCounterDetailService;
import com.example.backend.core.salesCounter.service.OrderSalesCounterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sales-counter/api")
@CrossOrigin("*")
public class OrderSCController {
    @Autowired
    private OrderSalesCounterService service;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrderSC(@RequestBody OrderSalesDTO orderSalesDTO){
        return ResponseEntity.ok(service.createOrderSales(orderSalesDTO));
    }
    @GetMapping("/list-bill-all")
    public ResponseEntity<?> getAllBill(){
        return ResponseEntity.ok(service.getAllOrder());
    }

    @PostMapping("/get-all-order")
    public ResponseEntity<?> getAllOrderAdmin(@RequestBody OrderAdminDTO orderAdminDTO){
        return ResponseEntity.ok(service.getAllOrderSalesAdmin(orderAdminDTO));
    }
}
