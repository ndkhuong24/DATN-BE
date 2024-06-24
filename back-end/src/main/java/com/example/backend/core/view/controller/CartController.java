package com.example.backend.core.view.controller;

import com.example.backend.core.view.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/view/api")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

//    @PostMapping("/cart")
//    public ResponseEntity<?> getCart(@RequestBody CartDTO cartDTO){
//        return ResponseEntity.ok(cartService.getCart(cartDTO));
//    }
}
