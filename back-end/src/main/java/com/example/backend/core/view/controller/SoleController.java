package com.example.backend.core.view.controller;

import com.example.backend.core.model.Sole;
import com.example.backend.core.view.service.SoleSevice;
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
public class SoleController {
    @Autowired
    private SoleSevice sevice;
    @GetMapping("/get-all-sole")
    public ResponseEntity<List<Sole>> getAll(){
        return ResponseEntity.ok(sevice.getAll());
    }
}
