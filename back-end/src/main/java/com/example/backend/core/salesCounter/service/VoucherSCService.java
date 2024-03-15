package com.example.backend.core.salesCounter.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.salesCounter.dto.VoucherSCDTO;
import com.example.backend.core.view.dto.VoucherDTO;

import java.util.List;

public interface VoucherSCService {
    List<VoucherSCDTO> getAllVoucherSC(VoucherSCDTO voucherDTO);

    ServiceResult<VoucherSCDTO> findByCodeSC(String code);
}
