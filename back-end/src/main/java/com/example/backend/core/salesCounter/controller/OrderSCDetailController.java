package com.example.backend.core.salesCounter.controller;

import com.example.backend.core.salesCounter.dto.OrderSalesDetailDTO;
import com.example.backend.core.salesCounter.service.OrderSalesCounterDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sales-counter/api")
@RestController
@CrossOrigin("*")
public class OrderSCDetailController {
    @Autowired
    private OrderSalesCounterDetailService service;
    @PostMapping("/create-order-detail")
    public ResponseEntity<?> createOrderDetailSC(@RequestBody OrderSalesDetailDTO orderSalesDetailDTO) {
        return ResponseEntity.ok(service.createrOrderDetailSales(orderSalesDetailDTO));
    }
}
