package com.example.backend.core.view.controller;

import com.example.backend.core.view.dto.CartDTO;
import com.example.backend.core.view.dto.ProductDTO;
import com.example.backend.core.view.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/view/api")
@CrossOrigin("*")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/cart")
    public ResponseEntity<?> getCart(@RequestBody CartDTO cartDTO){
        return ResponseEntity.ok(cartService.getCart(cartDTO));
    }
}
