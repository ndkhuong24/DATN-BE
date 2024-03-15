package com.example.backend.core.salesCounter.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.salesCounter.dto.VoucherShipSCDTO;
import com.example.backend.core.view.dto.VoucherFreeShipDTO;

import java.util.List;

public interface VoucherShipSCService {
    List<VoucherShipSCDTO> getAllVoucherShipSC(VoucherShipSCDTO voucherFreeShipDTO);
    ServiceResult<VoucherShipSCDTO> findByCodeSC(String code);
}
