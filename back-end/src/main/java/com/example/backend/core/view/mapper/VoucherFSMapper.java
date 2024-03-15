package com.example.backend.core.view.mapper;

import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.VoucherFreeShip;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface VoucherFSMapper extends EntityMapper<VoucherFreeShipDTO, VoucherFreeShip> {
}
