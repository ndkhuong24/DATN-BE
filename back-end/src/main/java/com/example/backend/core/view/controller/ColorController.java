package com.example.backend.core.view.controller;

import com.example.backend.core.view.dto.ColorDTO;
import com.example.backend.core.view.service.ColorService;
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
public class ColorController {

    @Autowired
    private ColorService colorService;

    @GetMapping("/get-all-color")
    public ResponseEntity<List<ColorDTO>> getAllColor(){
        return ResponseEntity.ok(colorService.getAll());
    }
}
