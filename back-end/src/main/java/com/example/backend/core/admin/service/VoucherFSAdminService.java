package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.commons.ServiceResult;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface VoucherFSAdminService {

    ServiceResult<VoucherFreeShipDTO> createVoucher(VoucherFreeShipDTO voucherDTO);
    ServiceResult<VoucherFreeShipDTO> updateVoucher(Long id, VoucherFreeShipDTO updatedVoucherAdminDTO);
    ServiceResult<Void> deleteVoucher(Long voucherId);
//    List<VoucherFreeShipDTO> detailById(Long voucherId);
    List<VoucherFreeShipDTO> getAllVouchers();
    List<CustomerAdminDTO> getAllCustomer();
    List<VoucherFreeShipDTO> getVouchersByTimeRange(String fromDate, String toDate);
    List<VoucherFreeShipDTO> getVouchersByKeyword(String keyword);
    ServiceResult<VoucherFreeShipDTO> KichHoat(Long idVoucher);
    ServiceResult<VoucherFreeShipDTO> setIdel(Long idVoucher);
    VoucherFreeShipDTO getDetailVoucher(Long id);
    List<VoucherFreeShipDTO> getVouchersByCustomer(String searchTerm);
    List<VoucherFreeShipDTO> getAllKhongKH();
    List<VoucherFreeShipDTO> getAllKichHoat();
    void sendMessageUsingThymeleafTemplate(VoucherFreeShipDTO voucherAdminDTO) throws MessagingException;
    byte[] exportExcelVoucher() throws IOException;
}
