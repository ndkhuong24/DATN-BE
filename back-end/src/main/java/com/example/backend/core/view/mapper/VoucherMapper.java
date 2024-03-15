package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.view.dto.VoucherDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface VoucherMapper extends EntityMapper<VoucherDTO, Voucher> {
}
