package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;

import java.util.Date;
import java.util.List;

public interface VoucherAdminCustomRepository {
    List<VoucherAdminDTO> getAllVouchers();
    List<CustomerAdminDTO> getAllCustomer();
    List<VoucherAdminDTO> getVouchersByTimeRange(String fromDate, String toDate);
    List<VoucherAdminDTO> getVouchersByKeyword(String keyword);
    List<VoucherAdminDTO> getAllKhongKH();
    List<VoucherAdminDTO> getAllKichHoat();
    List<VoucherAdminDTO> getVouchersByCustomer(String searchTerm);
    List<VoucherAdminDTO> getAllVouchersExport();
}
