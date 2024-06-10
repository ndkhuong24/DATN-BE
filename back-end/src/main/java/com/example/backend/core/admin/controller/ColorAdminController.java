package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.service.ColorAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class ColorAdminController {
    @Autowired
    private ColorAdminService colorAdminService;

    @GetMapping("/color/hien-thi")
    public ResponseEntity<List<ColorAdminDTO>> index() {
        return ResponseEntity.ok(colorAdminService.getAll());
    }

    @PostMapping("/color/add")
    public ResponseEntity<?> add(@RequestBody ColorAdminDTO colorAdminDTO) {
        return ResponseEntity.ok(colorAdminService.addColor(colorAdminDTO));
    }

    @PutMapping("/color/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ColorAdminDTO colorAdminDTO) {
        return ResponseEntity.ok(colorAdminService.update(colorAdminDTO, id));
    }

    @DeleteMapping("/color/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(colorAdminService.delete(id));
    }
}
