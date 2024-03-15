package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.service.CustomerAdminService;
import com.example.backend.core.admin.service.StaffAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/view/api")
public class StaffAdminController {
    @Autowired
    private StaffAdminService service;

    @Autowired
    private CustomerAdminService customerService;

    @GetMapping("/staff")
    public ResponseEntity<?> getallStaff(){
        return ResponseEntity.ok(service.getAllStaff());
    }
    @GetMapping("/staff/finbyId/{id}")
    public ResponseEntity<?> findByIdStaff(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.ok(service.findById(id));
    }
    @GetMapping("/customer/finbyId/{id}")
    public ResponseEntity<?> findByIdCustomer(
            @PathVariable("id") String id
    ){
        return ResponseEntity.ok(customerService.findById(id));
    }
}
