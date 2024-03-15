package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.BrandAdminDTO;
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
    private CategoryAdminService ctsv;

    @GetMapping("category/hien-thi")
    public ResponseEntity<List<CategoryAdminDTO>> hienthi(){
        return ResponseEntity.ok(ctsv.getAll());
    }
    @PostMapping("category/add")
    public ResponseEntity<?> add(@RequestBody CategoryAdminDTO categoryAdminDTO){
        return ResponseEntity.ok(ctsv.add(categoryAdminDTO));
    }
    @PutMapping("category/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody CategoryAdminDTO categoryAdminDTO){
        return ResponseEntity.ok(ctsv.update(categoryAdminDTO,id));
    }
    @DeleteMapping("category/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
        return ResponseEntity.ok(ctsv.delete(id));
    }
}
