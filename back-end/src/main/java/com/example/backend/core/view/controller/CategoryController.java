package com.example.backend.core.view.controller;

import com.example.backend.core.model.Category;
import com.example.backend.core.view.service.CategoryService;
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
public class CategoryController {
    @Autowired
    private CategoryService service;

    @GetMapping("/get-all-category")
    public ResponseEntity<List<Category>> getAll(){
        return ResponseEntity.ok(service.getAll());
    }
}
