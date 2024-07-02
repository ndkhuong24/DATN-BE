package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.service.ProductAdminService;
import com.example.backend.core.admin.service.ProductDetailAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class ProductAdminController {
    @Autowired
    private ProductAdminService productAdminService;

    @Autowired
    private ProductDetailAdminService productDetailAdminService;

//    private static final Logger log = LoggerFactory.getLogger(ProductAdminDTO.class);

    @GetMapping("product/hien-thi")
    public ResponseEntity<List<ProductAdminDTO>> hienthi() {
        return ResponseEntity.ok(productAdminService.getAll());
    }

    @PostMapping("product/add")
    public ResponseEntity<?> add(@RequestBody ProductAdminDTO productAdminDTO) {
        return ResponseEntity.ok(productAdminService.add(productAdminDTO));
    }

    @GetMapping("product/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productAdminService.getById(id));
    }

    @PutMapping("product/{id}/deactivate")
    public void deactivateProduct(@PathVariable("id") Long id) {
        productAdminService.deactivateProduct(id);
    }

    @PutMapping("product/{id}/activate")
    public void activateProduct(@PathVariable("id") Long id) {
        productAdminService.activateProduct(id);
    }

    @PutMapping("product/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ProductAdminDTO productAdminDTO) {
        return ResponseEntity.ok(productAdminService.update(productAdminDTO, id));
    }

    @GetMapping("product/search/{param}")
    public ResponseEntity<?> searchProduct(@PathVariable("param") String param) {
        return ResponseEntity.ok(productDetailAdminService.findByNameLikeOrCodeLike(param));
//        return ResponseEntity.ok(productAdminService.findByNameLikeOrCodeLike(param));
    }
}
