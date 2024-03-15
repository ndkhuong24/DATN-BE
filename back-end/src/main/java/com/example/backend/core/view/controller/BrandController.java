package com.example.backend.core.view.controller;

import com.example.backend.core.view.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/view/api")
@CrossOrigin("*")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @GetMapping("/get-brand-top")
    public ResponseEntity<?> brandTop(){
        return ResponseEntity.ok(brandService.getAllBrandTop());
    }
}
