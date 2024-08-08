package com.example.backend.core.view.controller;

import com.example.backend.core.view.dto.VoucherFreeShipDTO;
import com.example.backend.core.view.service.VoucherFreeShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/view/api")
@CrossOrigin("*")
public class VoucherFreeShipController {

    @Autowired
    private VoucherFreeShipService voucherFreeShipService;

    @PostMapping("/get-all-voucher-ship")
    public ResponseEntity<?> getAllVoucherShip(@RequestBody VoucherFreeShipDTO voucherFreeShipDTO) {
        return ResponseEntity.ok(voucherFreeShipService.getAllVoucherShip(voucherFreeShipDTO));
    }

    @GetMapping("/get-voucher-ship")
    public ResponseEntity<?> getVoucherShip(@RequestParam(name = "code") String code) {
        return ResponseEntity.ok(voucherFreeShipService.findByCode(code));
    }
}
