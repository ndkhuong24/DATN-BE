package com.example.backend.core.view.controller;

import com.example.backend.core.view.dto.VoucherDTO;
import com.example.backend.core.view.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/view/api")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @PostMapping("/get-all-voucher")
    public ResponseEntity<?> getAllVoucher(@RequestBody VoucherDTO voucherDTO){
        return ResponseEntity.ok(voucherService.getAllVoucher(voucherDTO));
    }
    @GetMapping("/get-voucher")
    public ResponseEntity<?> getVoucher(@RequestParam(name = "code") String code){
        return ResponseEntity.ok(voucherService.findByCode(code));
    }
}
