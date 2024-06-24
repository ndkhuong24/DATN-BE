package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.CategoryAdminDTO;
import com.example.backend.core.admin.service.CategoryAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class CategoryAdminController {
    @Autowired
    private CategoryAdminService categoryAdminService;

    @GetMapping("category/hien-thi")
    public ResponseEntity<List<CategoryAdminDTO>> hienthi() {
        return ResponseEntity.ok(categoryAdminService.getAll());
    }

    @PostMapping("category/add")
    public ResponseEntity<?> add(@RequestBody CategoryAdminDTO categoryAdminDTO) {
        return ResponseEntity.ok(categoryAdminService.add(categoryAdminDTO));
    }

    @PutMapping("category/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CategoryAdminDTO categoryAdminDTO) {
        return ResponseEntity.ok(categoryAdminService.update(categoryAdminDTO, id));
    }

    @DeleteMapping("category/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryAdminService.delete(id));
    }
}
