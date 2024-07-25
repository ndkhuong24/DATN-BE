package com.example.backend.core.view.controller;

import com.example.backend.core.model.Material;
import com.example.backend.core.view.service.MaterialService;
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
public class MaterialController {
    @Autowired
    private MaterialService service;

    @GetMapping("/get-all-material")
    public ResponseEntity<List<Material>>getAll(){
        return ResponseEntity.ok(service.getAll());
    }
}
