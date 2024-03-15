package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.VoucherDTO;

import java.util.List;

public interface VoucherService {

    List<VoucherDTO> getAllVoucher(VoucherDTO voucherDTO);

    ServiceResult<VoucherDTO> findByCode(String code);
}
