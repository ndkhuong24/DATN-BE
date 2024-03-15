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
    private ColorAdminService clsv;
    @GetMapping("/color/hien-thi")
    public ResponseEntity<List<ColorAdminDTO>> index(){
        return ResponseEntity.ok(clsv.getAll());
    }
    @DeleteMapping("/color/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(clsv.delete(id));
    }
    @PutMapping("/color/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody ColorAdminDTO colorAdminDTO){
        return ResponseEntity.ok(clsv.update(colorAdminDTO,id));
    }
    @PostMapping("/color/add")
    public ResponseEntity<?> add(@RequestBody ColorAdminDTO colorAdminDTO){
        return ResponseEntity.ok(clsv.addColor(colorAdminDTO));
    }

}
