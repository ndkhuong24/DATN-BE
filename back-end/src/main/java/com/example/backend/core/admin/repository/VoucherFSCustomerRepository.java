package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;

import java.util.List;

public interface VoucherFSCustomerRepository {
    List<VoucherFreeShipDTO> getAllVouchers();
    List<CustomerAdminDTO> getAllCustomer();
    List<VoucherFreeShipDTO> getVouchersByTimeRange(String fromDate, String toDate);
    List<VoucherFreeShipDTO> getVouchersByKeyword(String keyword);
    List<VoucherFreeShipDTO> getVouchersByCustomer(String searchTerm);
    List<VoucherFreeShipDTO> getAllKhongKH();
    List<VoucherFreeShipDTO> getAllKichHoat();
    List<VoucherFreeShipDTO> getAllVoucherFSsExport();
}
