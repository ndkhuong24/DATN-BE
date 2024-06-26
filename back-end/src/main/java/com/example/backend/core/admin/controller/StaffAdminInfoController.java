package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.admin.service.StaffAdminService;
import com.example.backend.core.model.Staff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class StaffAdminInfoController {
    @Autowired
    private StaffAdminService service;

    @GetMapping("/staff-getall")
    public ResponseEntity<?> getAllStaff() {
        return ResponseEntity.ok(service.getAllStaff());
    }

    @PutMapping("/staff-update/{id}")
    public ResponseEntity<?> updateInfoStaff(@PathVariable("id") Long id, @RequestBody StaffAdminDTO staffAdminDTO) {
        return ResponseEntity.ok(service.updateStaff(id,staffAdminDTO));
    }

    @PutMapping("/staff/update/{id}")
    public ResponseEntity<?> updateStaff(@PathVariable("id") Long id, @RequestBody StaffAdminDTO staffAdminDTO) {
        return ResponseEntity.ok(service.updateStaff(id,staffAdminDTO));
    }

    @GetMapping("/staff-search/{params}")
    public ResponseEntity<?> finByCodeOrPhone(@PathVariable("params") String params) {
        return ResponseEntity.ok(service.findByFullnameOrPhoneLike(params));
    }
}
