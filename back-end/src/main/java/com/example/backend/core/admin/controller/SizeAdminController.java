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
    private SizeAdminService ssv;
    @GetMapping("size/hien-thi")
    public ResponseEntity<List<SizeAdminDTO>> hienthi(){
        return ResponseEntity.ok(ssv.getAll());
    }
    @PostMapping("size/add")
    public ResponseEntity<?> add(@RequestBody SizeAdminDTO sizeAdminDTO){
        return ResponseEntity.ok(ssv.add(sizeAdminDTO));
    }
    @PutMapping("size/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id,@RequestBody SizeAdminDTO sizeAdminDTO){
        return ResponseEntity.ok(ssv.update(sizeAdminDTO,id));
    }
    @DeleteMapping("size/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")Long id){
        return ResponseEntity.ok(ssv.delete(id));
    }
}
