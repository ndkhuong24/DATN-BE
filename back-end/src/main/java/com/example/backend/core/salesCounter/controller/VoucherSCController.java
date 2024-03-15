package com.example.backend.core.salesCounter.controller;

import com.example.backend.core.salesCounter.dto.VoucherSCDTO;
import com.example.backend.core.salesCounter.service.VoucherSCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sc-voucher")
@CrossOrigin("*")
public class VoucherSCController {
    @Autowired
    private VoucherSCService voucherSCService;

    @PostMapping("/get-all-voucher")
    public ResponseEntity<?> getAllVoucher(@RequestBody VoucherSCDTO voucherDTO){
        return ResponseEntity.ok(voucherSCService.getAllVoucherSC(voucherDTO));
    }
    @GetMapping("/get-voucher")
    public ResponseEntity<?> getVoucher(@RequestParam(name = "code") String code){
        return ResponseEntity.ok(voucherSCService.findByCodeSC(code));
    }
}
