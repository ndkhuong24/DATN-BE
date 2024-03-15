package com.example.backend.core.view.controller;

import com.example.backend.core.view.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/view/api")
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/get-product-noi-bat")
    public ResponseEntity<?> getProductNoiBatByBrand(@RequestParam(name = "idBrand", required = false) Long thuongHieu){
        return ResponseEntity.ok(productService.getProductNoiBatByBrand(thuongHieu));
    }
    @GetMapping("/get-product-tuong-tu")
    public ResponseEntity<?> getProductTuongTu(@RequestParam(name = "idProduct") Long idProduct,@RequestParam(name = "idCategory") Long idCategory){
        return ResponseEntity.ok(productService.getProductTuongTu(idProduct, idCategory));
    }
    @GetMapping("/get-detail-product/{id}")
    public ResponseEntity<?> getDetailProduct(@PathVariable(name = "id")Long id){
        return ResponseEntity.ok(productService.getDetailProduct(id));
    }
}
