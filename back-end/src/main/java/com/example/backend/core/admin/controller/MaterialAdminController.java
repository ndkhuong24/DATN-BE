package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.MaterialAdminDTO;
import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.admin.service.MaterialAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class MaterialAdminController {
    @Autowired
    private MaterialAdminService mtsv;

    @GetMapping("material/hien-thi")
    public ResponseEntity<List<MaterialAdminDTO>> hienthi(){
        return ResponseEntity.ok(mtsv.getAll());
    }
    @PostMapping("material/add")
    public ResponseEntity<?> add(@RequestBody MaterialAdminDTO materialAdminDTO){
        return ResponseEntity.ok(mtsv.add(materialAdminDTO));
    }
    @PutMapping("material/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody MaterialAdminDTO materialAdminDTO){
        return ResponseEntity.ok(mtsv.update(materialAdminDTO,id));
    }
    @DeleteMapping("material/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
        return ResponseEntity.ok(mtsv.delete(id));
    }
}
