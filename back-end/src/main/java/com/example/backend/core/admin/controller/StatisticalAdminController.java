package com.example.backend.core.admin.controller;


import com.example.backend.core.admin.dto.StatisticalAdminDTO;
import com.example.backend.core.admin.service.StatisticalAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("api/admin")
public class StatisticalAdminController {

    @Autowired
    private StatisticalAdminService statisticalAdminService;

    @PostMapping("/get-statistical-by-year")
    public ResponseEntity<?> getStatisticalByYear(@RequestBody StatisticalAdminDTO statisticalAdminDTO){
        return ResponseEntity.ok(statisticalAdminService.getTotalStatisticalByYear(statisticalAdminDTO));
    }
}
