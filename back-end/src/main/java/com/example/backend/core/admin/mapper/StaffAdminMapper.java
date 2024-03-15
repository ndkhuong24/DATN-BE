package com.example.backend.core.admin.mapper;

import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Staff;
import org.mapstruct.Mapper;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {})
public interface StaffAdminMapper extends EntityMapper<StaffAdminDTO, Staff> {
}
