package com.example.backend.core.view.controller;


import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/view/api")
@CrossOrigin("*")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.createOrder(orderDTO));
    }
    @PostMapping("/create-order/not-login")
    public ResponseEntity<?> createOrderBuyNow(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.createOrderNotLogin(orderDTO));
    }
    @PostMapping("/cancel-order-view")
    public ResponseEntity<?> cancelOrderView(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.cancelOrderView(orderDTO));
    }

    @PostMapping("/get-all-order")
    public ResponseEntity<?> getAllOrder(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.getAll(orderDTO));
    }
    @PostMapping("/tra-cuu-order")
    public ResponseEntity<?> traCuuDonHang(@RequestBody OrderDTO orderDTO){
        return ResponseEntity.ok(orderService.traCuuDonHang(orderDTO));
    }
}
