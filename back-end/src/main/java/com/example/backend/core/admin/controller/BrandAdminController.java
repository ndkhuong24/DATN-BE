package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.service.BrandAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class BrandAdminController {
    @Autowired
    private BrandAdminService brandAdminService;

    @GetMapping("brand/hien-thi")
    public ResponseEntity<List<BrandAdminDTO>> hienthi() {
        return ResponseEntity.ok(brandAdminService.getAll());
    }

    @PostMapping("brand/add")
    public ResponseEntity<?> add(@RequestBody BrandAdminDTO brandAdminDTO) {
        return ResponseEntity.ok(brandAdminService.add(brandAdminDTO));
    }

    @PutMapping("brand/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody BrandAdminDTO brandAdminDTO) {
        return ResponseEntity.ok(brandAdminService.update(brandAdminDTO, id));
    }

    @DeleteMapping("brand/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(brandAdminService.delete(id));
    }
}
