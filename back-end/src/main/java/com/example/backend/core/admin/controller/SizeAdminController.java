package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.admin.service.SizeAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("api/admin")
public class SizeAdminController {
    @Autowired
    private SizeAdminService sizeAdminService;

    @GetMapping("size/hien-thi")
    public ResponseEntity<List<SizeAdminDTO>> hienthi() {
        return ResponseEntity.ok(sizeAdminService.getAll());
    }

    @PostMapping("size/add")
    public ResponseEntity<?> add(@RequestBody SizeAdminDTO sizeAdminDTO) {
        return ResponseEntity.ok(sizeAdminService.add(sizeAdminDTO));
    }

    @PutMapping("size/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody SizeAdminDTO sizeAdminDTO) {
        return ResponseEntity.ok(sizeAdminService.update(sizeAdminDTO, id));
    }

    @DeleteMapping("size/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sizeAdminService.delete(id));
    }
}
