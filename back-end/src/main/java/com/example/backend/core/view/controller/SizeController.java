package com.example.backend.core.view.controller;

import com.example.backend.core.view.dto.SizeDTO;
import com.example.backend.core.view.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/view/api")
@CrossOrigin("*")
public class SizeController {

    @Autowired
    private SizeService sizeService;

    @GetMapping("/get-all-size")
    public ResponseEntity<List<SizeDTO>> getAllSize(){
        return ResponseEntity.ok(sizeService.getAll());
    }
}
