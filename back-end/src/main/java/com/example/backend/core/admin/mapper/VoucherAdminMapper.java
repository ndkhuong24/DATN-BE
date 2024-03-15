package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Discount;
import com.example.backend.core.model.Voucher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface VoucherAdminMapper extends EntityMapper<VoucherAdminDTO, Voucher> {
}
