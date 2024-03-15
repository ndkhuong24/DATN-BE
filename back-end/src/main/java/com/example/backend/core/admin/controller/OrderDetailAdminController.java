package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.service.OrderDetailAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class OrderDetailAdminController {

    @Autowired
    private OrderDetailAdminService orderDetailAdminService;

    @GetMapping("/get-order-detail/by-order/{idOrder}")
    public ResponseEntity<?> getAllOrderDetailAdminByOrder(@PathVariable(name = "idOrder")Long idOrder){
        return ResponseEntity.ok(orderDetailAdminService.getAllByOrder(idOrder));
    }
}
