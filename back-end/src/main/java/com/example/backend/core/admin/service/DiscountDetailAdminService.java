package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.ProductDTO;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface DiscountDetailAdminService {
    List<DiscountAdminDTO> getAll();
    ServiceResult<DiscountDetailAdminDTO> createDiscount(DiscountDetailAdminDTO khuyenMaiDTO);
    ServiceResult<DiscountDetailAdminDTO> updateDiscount( DiscountDetailAdminDTO discountDetailAdminDTO);
    ServiceResult<DiscountAdminDTO> KichHoat(Long idDiscount);
    ServiceResult<DiscountAdminDTO> setIdel(Long idDiscount);
    List<ProductAdminDTO> getAllProduct();
    DiscountAdminDTO getDetailDiscount(Long id);
     List<DiscountAdminDTO> getAllKichHoat();
     List<DiscountAdminDTO> getAllKhongKichHoat();
    List<DiscountAdminDTO> getAllByDateRange(String fromDate, String toDate);
    List<DiscountAdminDTO> getAllByCodeOrName(String search);
    List<DiscountAdminDTO> getAllByCategory(String category);
    List<DiscountAdminDTO> getAllByProductNameOrCode(String productNameOrCode);
    List<DiscountAdminDTO> getAllByBrand(String brand);
    ServiceResult<Void> deleteDiscount(Long discountId);
    byte[] exportExcelDiscount() throws IOException;

}
