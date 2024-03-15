package com.example.backend.core.admin.service;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import jakarta.mail.MessagingException;

import java.io.IOException;
import java.util.List;

public interface VoucherAdminService {

    ServiceResult<VoucherAdminDTO> createVoucher(VoucherAdminDTO voucherDTO);
    ServiceResult<VoucherAdminDTO> updateVoucher(Long id, VoucherAdminDTO updatedVoucherAdminDTO);
    ServiceResult<Void> deleteVoucher(Long voucherId);
    ServiceResult<VoucherAdminDTO> KichHoat(Long idVoucher) throws MessagingException;
    ServiceResult<VoucherAdminDTO> setIdel(Long idVoucher) ;
    VoucherAdminDTO getDetailVoucher(Long id);
//    List<VoucherAdminDTO> detailById(Long voucherId);
    List<VoucherAdminDTO> getAllVouchers();
    List<CustomerAdminDTO> getAllCustomer();
    List<VoucherAdminDTO> getVouchersByTimeRange(String fromDate, String toDate);
    List<VoucherAdminDTO> getVouchersByKeyword(String keyword);
    List<VoucherAdminDTO> getAllKhongKH();
    List<VoucherAdminDTO> getAllKichHoat();
    List<VoucherAdminDTO> getVouchersByCustomer(String searchTerm);
    void sendMessageUsingThymeleafTemplate(VoucherAdminDTO voucherAdminDTO) throws MessagingException;
    byte[] exportExcelVoucher() throws IOException;
}
