package com.example.backend.core.salesCounter.mapper;

import com.example.backend.core.commons.EntityMapper;
import com.example.backend.core.model.Staff;
import com.example.backend.core.salesCounter.dto.StaffSCDTO;
import org.mapstruct.Mapper;



@Mapper(componentModel = "spring", uses = {})
public interface StaffSCMapper extends EntityMapper<StaffSCDTO, Staff> {
}
