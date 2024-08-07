package com.example.backend.core.view.controller;


import com.example.backend.core.view.service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/view/api")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/get-order-detail/by-order/{idOrder}")
    public ResponseEntity<?> getAllOrderDetailByOrder(@PathVariable(name = "idOrder")Long idOrder){
        return ResponseEntity.ok(orderDetailService.getAllByOrder(idOrder));
    }
}
