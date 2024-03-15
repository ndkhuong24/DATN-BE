package com.example.backend.core.salesCounter.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.salesCounter.dto.VoucherSCDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface VoucherSCMapper extends EntityMapper<VoucherSCDTO, Voucher> {
}
