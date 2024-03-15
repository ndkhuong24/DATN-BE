package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.admin.service.SoleAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class SoleAdminController {
    @Autowired
    private SoleAdminService slsv;

    @GetMapping("sole/hien-thi")
    public ResponseEntity<List<SoleAdminDTO>> hienthi(){
        return ResponseEntity.ok(slsv.getAll());
    }
    @PostMapping("sole/add")
    public ResponseEntity<?> add(@RequestBody SoleAdminDTO soleAdminDTO){
        return ResponseEntity.ok(slsv.add(soleAdminDTO));
    }
    @PutMapping("sole/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody SoleAdminDTO soleAdminDTO){
        return ResponseEntity.ok(slsv.update(soleAdminDTO,id));
    }
    @DeleteMapping("sole/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
        return ResponseEntity.ok(slsv.delete(id));
    }
}
