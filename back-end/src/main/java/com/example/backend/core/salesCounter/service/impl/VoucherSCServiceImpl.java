package com.example.backend.core.salesCounter.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.salesCounter.dto.VoucherSCDTO;
import com.example.backend.core.salesCounter.mapper.VoucherSCMapper;
import com.example.backend.core.salesCounter.repository.VoucherSCRepository;
import com.example.backend.core.salesCounter.service.VoucherSCService;
import com.example.backend.core.view.dto.VoucherDTO;
import com.example.backend.core.view.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class VoucherSCServiceImpl implements VoucherSCService {

    @Autowired
    private VoucherSCRepository voucherSCRepository;

    @Autowired
    private VoucherSCMapper voucherSCMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<VoucherSCDTO> getAllVoucherSC(VoucherSCDTO voucherDTO) {
        if(null != voucherDTO.getIdCustomerLogin()){
            List<Voucher> lst = voucherSCRepository.getAllVoucherByCustomerSC(voucherDTO.getCode(), voucherDTO.getIdCustomerLogin());
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
            return voucherSCMapper.toDto(lst);
        }
        return voucherSCMapper.toDto(voucherSCRepository.getAllVoucherSC(voucherDTO.getCode()));
    }

    @Override
    public ServiceResult<VoucherSCDTO> findByCodeSC(String code) {
        ServiceResult<VoucherSCDTO> result = new ServiceResult<>();
        Voucher voucher = voucherSCRepository.findByCode(code);
        result.setData(voucherSCMapper.toDto(voucher));
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        return result;
    }
}
