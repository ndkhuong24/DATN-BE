package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Address;
import com.example.backend.core.security.dto.UsersDTO;
import com.example.backend.core.view.dto.AddressDTO;

import java.util.List;

public interface AddressService {
    List<AddressDTO> getAllAddress(AddressDTO addressDTO);

    ServiceResult<AddressDTO> getAddress(AddressDTO addressDTO);

    ServiceResult<AddressDTO> save(AddressDTO addressDTO);

    ServiceResult<AddressDTO> updateConfig(AddressDTO addressDTO);

    ServiceResult<AddressDTO> detailAddressConfig(Long id);
}
