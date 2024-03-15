package com.example.backend.core.view.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.VoucherFreeShip;
import com.example.backend.core.view.dto.VoucherFreeShipDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface VoucherFreeShipMapper extends EntityMapper<VoucherFreeShipDTO, VoucherFreeShip> {
}
