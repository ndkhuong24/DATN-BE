package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.OrderDetailAdminDTO;
import com.example.backend.core.admin.service.OrderDetailAdminService;
import com.example.backend.core.commons.ServiceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class OrderDetailAdminController {
    @Autowired
    private OrderDetailAdminService orderDetailAdminService;

    @GetMapping("/get-order-detail/by-order/{idOrder}")
    public ResponseEntity<?> getAllOrderDetailAdminByOrder(@PathVariable(name = "idOrder") Long idOrder) {
        return ResponseEntity.ok(orderDetailAdminService.getAllByOrder(idOrder));
    }

    @DeleteMapping("/delete-order-detail/{id}")
    public ResponseEntity<?> deleteOrderDetail(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(orderDetailAdminService.deleteOrderDetail(id));
    }

    @DeleteMapping("/delete-order-detail-by-id-order/{id}")
    public ResponseEntity<?> deleteOrderDetailByIdOrder(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(orderDetailAdminService.deleteOrderDetailByIdOrder(id));
    }

    @PutMapping("/update-order-detail/{id}")
    public ResponseEntity<?> updateOrderDetail(@PathVariable(name = "id") Long id, @RequestBody OrderDetailAdminDTO orderDetailAdminDTO) {
        try {
            // Kiểm tra sự tồn tại của đơn hàng
            if (!orderDetailAdminService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order detail not found.");
            }

            // Cập nhật thông tin chi tiết đơn hàng
            ServiceResult<OrderDetailAdminDTO> updatedOrderDetail = orderDetailAdminService.updateOrderDetail(orderDetailAdminDTO);
            return ResponseEntity.ok(updatedOrderDetail);

        } catch (Exception e) {
            // Xử lý ngoại lệ và trả về thông báo lỗi
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the order detail.");
        }
    }
}
