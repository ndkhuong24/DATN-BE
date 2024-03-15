package com.example.backend.core.salesCounter.controller;

import com.example.backend.core.salesCounter.service.ProductSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("sales-counter/product")
@CrossOrigin("*")
public class ProductSalesController {
    @Autowired
    private ProductSCService service;
    @GetMapping("get-all")
    public ResponseEntity<?> getAllProduct(){
        return ResponseEntity.ok(service.getAllProductDetail());
    }
}
