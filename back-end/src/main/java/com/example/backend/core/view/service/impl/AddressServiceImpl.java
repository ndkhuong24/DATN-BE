package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Address;
import com.example.backend.core.security.dto.UsersDTO;
import com.example.backend.core.view.dto.AddressDTO;
import com.example.backend.core.view.mapper.AddressMapper;
import com.example.backend.core.view.repository.AddressRepository;
import com.example.backend.core.view.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AddressMapper addressMapper;

    @Override
    public List<AddressDTO> getAllAddress(AddressDTO addressDTO) {
        List<AddressDTO> lst = new ArrayList<>();
        if (addressDTO.getIdCustomer() != null) {
            lst = addressMapper.toDto(addressRepository.findByIdCustomerOrderByCreateDateDesc(addressDTO.getIdCustomer()));
        }
        return lst;
    }

    @Override
    public ServiceResult<AddressDTO> getAddress(AddressDTO addressDTO) {
        ServiceResult<AddressDTO> result = new ServiceResult<>();
        Address address = addressRepository.getAddressByCustomer(addressDTO.getIdCustomer());
        if (null != address) {
            result.setMessage("Success");
            result.setData(addressMapper.toDto(address));
            result.setStatus(HttpStatus.OK);
        }
        return result;
    }

    @Override
    public ServiceResult<AddressDTO> save(AddressDTO addressDTO) {
        ServiceResult<AddressDTO> result = new ServiceResult<>();
        Address address = addressMapper.toEntity(addressDTO);
        address.setCreateDate(Instant.now());
        if(addressDTO.getConfig() == null){
            address.setConfig(1);
        }
        address = addressRepository.save(address);
        if (address != null) {
            result.setMessage("Success!");
            result.setStatus(HttpStatus.OK);
            result.setData(addressDTO);
        }
        return result;
    }

    @Override
    public ServiceResult<AddressDTO> updateConfig(AddressDTO addressDTO) {
        ServiceResult<AddressDTO> result = new ServiceResult<>();
        Address address = addressRepository.findById(addressDTO.getId()).orElse(null);
        if (null == address) {
            result.setMessage("Error!");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData(null);
            return result;
        }
        if (null == addressDTO.getIdCustomer()) {
            result.setMessage("Error!");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData(null);
            return result;
        }
        addressRepository.updateConfigByCustomer(addressDTO.getIdCustomer());
        address.setConfig(0);
        address = addressRepository.save(address);
        result.setMessage("Success!");
        result.setStatus(HttpStatus.OK);
        result.setData(addressMapper.toDto(address));
        return result;
    }

    @Override
    public ServiceResult<AddressDTO> detailAddressConfig(Long id) {
        ServiceResult<AddressDTO> result = new ServiceResult<>();
        Address address = addressRepository.findById(id).orElse(null);
        if (null == address) {
            result.setMessage("Error!");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData(null);
            return result;
        }
        result.setMessage("Success!");
        result.setStatus(HttpStatus.OK);
        result.setData(addressMapper.toDto(address));
        return result;
    }
}
