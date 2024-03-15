package com.example.backend.core.salesCounter.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.VoucherFreeShip;
import com.example.backend.core.salesCounter.dto.VoucherShipSCDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface VoucherShipSCMapper extends EntityMapper<VoucherShipSCDTO, VoucherFreeShip> {
}
