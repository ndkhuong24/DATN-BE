package com.example.backend.core.view.service.impl;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.mapper.CustomerMapper;
import com.example.backend.core.view.repository.CustomerRepository;
import com.example.backend.core.view.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository repository;

    private ServiceResult<CustomerDTO> result = new ServiceResult<>();

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public ServiceResult<CustomerDTO> updateInforCustomer(CustomerDTO customerDTO, Customer customer) {
        if (!customer.getPhone().equals(customerDTO.getPhone())){
            if (repository.existsByPhone(customerDTO.getPhone())){
                result.setStatus(HttpStatus.OK);
                result.setMessage("Phone existed");
                result.setData(customerMapper.toDto(customer));
                return result;
            }
        }
        if (!customer.getEmail().equals(customerDTO.getEmail())){
            if (repository.existsByEmail(customerDTO.getEmail())){
                result.setStatus(HttpStatus.OK);
                result.setMessage("Email existed");
                result.setData(customerMapper.toDto(customer));
                return result;
            }
        }
        if (!customer.equals("")){
            customer.setFullname(customerDTO.getFullname());
            customer.setPhone(customerDTO.getPhone());
            customer.setEmail(customerDTO.getEmail());
            customer.setBirthday(customerDTO.getBirthday());
            customer.setGender(customerDTO.getGender());
            customer.setUpdateDate(LocalDateTime.now());
            this.repository.save(customer);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Sua thanh cong");
            result.setData(customerMapper.toDto(customer));
        }else {
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Sua  khong thanh cong");
            result.setData(null);
        }
        return result;
    }

    @Override
    public List<CustomerDTO> findByPhoneLike(String phone) {
        List<CustomerDTO> listCustomer = customerMapper.toDto(repository.findByPhoneLike("%" + phone +"%"));
        return listCustomer;
    }

    @Override
    public CustomerDTO getInfo(Integer id) {
        return customerMapper.toDto(repository.getReferenceById(Long.valueOf(id)));
    }

    @Override
    public CustomerDTO findById(Long id) {
        return customerMapper.toDto(repository.findById(id).get());
    }
}
