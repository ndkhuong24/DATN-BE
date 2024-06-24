package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.commons.ServiceResult;

import java.util.List;

public interface DiscountDetailAdminService {
    //    ServiceResult<DiscountAdminDTO> KichHoat(Long idDiscount);
//    ServiceResult<DiscountAdminDTO> setIdel(Long idDiscount);
//    List<DiscountAdminDTO> getAll();
//    ServiceResult<DiscountDetailAdminDTO> createDiscount(DiscountDetailAdminDTO khuyenMaiDTO);
//    ServiceResult<DiscountDetailAdminDTO> updateDiscount( DiscountDetailAdminDTO discountDetailAdminDTO);
//    List<ProductAdminDTO> getAllProduct();
//    DiscountAdminDTO getDetailDiscount(Long id);
//     List<DiscountAdminDTO> getAllKichHoat();
//     List<DiscountAdminDTO> getAllKhongKichHoat();
//    List<DiscountAdminDTO> getAllByDateRange(String fromDate, String toDate);
//    List<DiscountAdminDTO> getAllByCodeOrName(String search);
//    List<DiscountAdminDTO> getAllByCategory(String category);
//    List<DiscountAdminDTO> getAllByProductNameOrCode(String productNameOrCode);
//    List<DiscountAdminDTO> getAllByBrand(String brand);
//    ServiceResult<Void> deleteDiscount(Long discountId);
//    byte[] exportExcelDiscount() throws IOException;
    List<DiscountAdminDTO> getAll();

    List<DiscountAdminDTO> getAllKichHoat();

    List<DiscountAdminDTO> getAllKhongKichHoat();

    ServiceResult<Void> deleteDiscountById(Long idDiscount);

    DiscountAdminDTO getDetailDiscountById(Long idDiscount);

    ServiceResult<DiscountAdminDTO> KichHoat(Long id);

    ServiceResult<DiscountAdminDTO> setIdel(Long id);
}
