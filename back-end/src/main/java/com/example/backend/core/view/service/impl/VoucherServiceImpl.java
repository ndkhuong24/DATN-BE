package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.view.dto.VoucherDTO;
import com.example.backend.core.view.mapper.VoucherMapper;
import com.example.backend.core.view.repository.OrderRepository;
import com.example.backend.core.view.repository.VoucherRepository;
import com.example.backend.core.view.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class VoucherServiceImpl implements VoucherService {

    @Autowired
    private VoucherRepository voucherRepository;
    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<VoucherDTO> getAllVoucher(VoucherDTO voucherDTO) {
        if(null != voucherDTO.getIdCustomerLogin()){
            List<Voucher> lst = voucherRepository.getAllVoucherByCustomer(voucherDTO.getCode(), voucherDTO.getIdCustomerLogin());
            if(lst.isEmpty()){
                return null;
            }
            Iterator<Voucher> iterator = lst.iterator();
            while (iterator.hasNext()) {
                Voucher listItem = iterator.next();

                List<Order> orderList = orderRepository.findByIdCustomerAndCodeVoucher(voucherDTO.getIdCustomerLogin(), listItem.getCode());
                if(listItem.getOptionCustomer() == 1){
                    if (orderList.size() >= listItem.getLimitCustomer()) {
                        iterator.remove();
                    }
                }
            }
            return voucherMapper.toDto(lst);
        }
        return voucherMapper.toDto(voucherRepository.getAllVoucher(voucherDTO.getCode()));
    }

    @Override
    public ServiceResult<VoucherDTO> findByCode(String code) {
        ServiceResult<VoucherDTO> result = new ServiceResult<>();
        Voucher voucher = voucherRepository.findByCode(code);
        result.setData(voucherMapper.toDto(voucher));
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        return result;
    }
}
