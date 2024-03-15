package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;

import java.util.Date;
import java.util.List;

public interface DiscountAdminCustomRepository {

    void deleteAllDiscountDetailByDiscount(Long id);
    List<DiscountAdminDTO> getAll();
    List<DiscountAdminDTO> getAllKichHoat();
    List<DiscountAdminDTO> getAllKhongKichHoat();
    List<DiscountAdminDTO> getAllByDateRange(String fromDate, String toDate);
    List<DiscountAdminDTO> getAllByCodeOrName(String search);
    List<DiscountAdminDTO> getAllByCategory(String category);
    List<DiscountAdminDTO> getAllByProductNameOrCode(String productNameOrCode);
    List<DiscountAdminDTO> getAllByBrand(String brand);
    List<ProductAdminDTO> getAllProduct();
    List<ProductAdminDTO> getAllProductKickHoat();
    List<DiscountDetailAdminDTO> discountExport();
}
