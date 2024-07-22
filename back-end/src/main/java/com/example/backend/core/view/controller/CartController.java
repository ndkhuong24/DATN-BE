package com.example.backend.core.view.controller;

import com.example.backend.core.model.Cart;
import com.example.backend.core.view.dto.CartDTO;
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
    public ResponseEntity<?> getCart(@RequestBody CartDTO cartDTO) {
        return ResponseEntity.ok(cartService.getCart(cartDTO));
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@RequestBody Cart cart) {
        return ResponseEntity.ok(cartService.addToCart(cart));
    }

    @GetMapping("/get-cart-to-customer/{id}")
    public ResponseEntity<?> getCartByCustomer(@PathVariable Long id) {
        return ResponseEntity.ok(cartService.getCartByCustomer(id));
    }

    @PostMapping("/giam-so-luong")
    public ResponseEntity<?> giamSoLuong(@RequestBody Cart cart) {
        return ResponseEntity.ok(cartService.giamSoLuong(cart));
    }

    @PostMapping("/tang-so-luong")
    public ResponseEntity<?> tangSoLuong(@RequestBody Cart cart) {
        return ResponseEntity.ok(cartService.tangSoLuong(cart));
    }

//    @DeleteMapping("/xoa")
//    public ResponseEntity<?> xoa(@RequestBody Cart cart){
//        return ResponseEntity.ok(cartService.xoa(cart));
//    }

    @DeleteMapping("/xoa")
    public ResponseEntity<?> xoa(
            @RequestParam Long idProduct,
            @RequestParam Long idColor,
            @RequestParam Long idSize,
            @RequestParam Long idCustomer) {
        Cart cart = new Cart();
        cart.setIdProduct(idProduct);
        cart.setIdColor(idColor);
        cart.setIdSize(idSize);
        cart.setIdCustomer(idCustomer);
        return ResponseEntity.ok(cartService.xoa(cart));
    }
}
