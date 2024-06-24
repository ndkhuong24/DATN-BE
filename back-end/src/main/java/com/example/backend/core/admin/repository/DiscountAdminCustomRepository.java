package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
<<<<<<< HEAD
import com.example.backend.core.admin.dto.ProductAdminDTO;
=======
>>>>>>> 7655df1fef7905661c4070e435bd6011f90bbdef

import java.util.List;

public interface DiscountAdminCustomRepository {
    List<DiscountAdminDTO> getAll();

    List<DiscountAdminDTO> getAllKichHoat();

    List<DiscountAdminDTO> getAllKhongKichHoat();

    List<ProductAdminDTO> getAllProductKickHoat();
//    void deleteAllDiscountDetailByDiscount(Long id);
//    List<DiscountAdminDTO> getAll();
//    List<DiscountAdminDTO> getAllKichHoat();
//    List<DiscountAdminDTO> getAllKhongKichHoat();
//    List<DiscountAdminDTO> getAllByDateRange(String fromDate, String toDate);
//    List<DiscountAdminDTO> getAllByCodeOrName(String search);
//    List<DiscountAdminDTO> getAllByCategory(String category);
//    List<DiscountAdminDTO> getAllByProductNameOrCode(String productNameOrCode);
//    List<DiscountAdminDTO> getAllByBrand(String brand);
//    List<ProductAdminDTO> getAllProduct();
//    List<ProductAdminDTO> getAllProductKickHoat();
//    List<DiscountDetailAdminDTO> discountExport();

    List<DiscountAdminDTO> getAll();

    List<DiscountAdminDTO> getAllKichHoat();

    List<DiscountAdminDTO> getAllKhongKichHoat();
}
