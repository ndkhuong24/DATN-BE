package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.VoucherFreeShipDTO;

import java.util.List;

public interface VoucherFreeShipService {
    List<VoucherFreeShipDTO> getAllVoucherShip(VoucherFreeShipDTO voucherFreeShipDTO);
    ServiceResult<VoucherFreeShipDTO> findByCode(String code);
}
