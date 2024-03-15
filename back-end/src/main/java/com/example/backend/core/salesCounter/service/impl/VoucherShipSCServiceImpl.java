package com.example.backend.core.salesCounter.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.VoucherFreeShip;
import com.example.backend.core.salesCounter.dto.VoucherShipSCDTO;
import com.example.backend.core.salesCounter.mapper.VoucherShipSCMapper;
import com.example.backend.core.salesCounter.repository.VoucherShipSCRepository;
import com.example.backend.core.salesCounter.service.VoucherShipSCService;
import com.example.backend.core.view.dto.VoucherFreeShipDTO;
import com.example.backend.core.view.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class VoucherShipSCServiceImpl implements VoucherShipSCService {

    @Autowired
    private VoucherShipSCRepository voucherShipSCRepository;

    @Autowired
    private VoucherShipSCMapper voucherShipSCMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<VoucherShipSCDTO> getAllVoucherShipSC(VoucherShipSCDTO voucherFreeShipDTO) {
        if(null != voucherFreeShipDTO.getIdCustomerLogin()){
            List<VoucherFreeShip> lst = voucherShipSCRepository.getAllVoucherShipByCustomerSC(voucherFreeShipDTO.getCode(), voucherFreeShipDTO.getIdCustomerLogin());
            if(lst.isEmpty()){
                return null;
            }
            Iterator<VoucherFreeShip> iterator = lst.iterator();
            while (iterator.hasNext()) {
                VoucherFreeShip listItem = iterator.next();

                List<Order> orderList = orderRepository.findByIdCustomerAndCodeVoucher(voucherFreeShipDTO.getIdCustomerLogin(), listItem.getCode());
                if(listItem.getOptionCustomer() == 1){
                    if (orderList.size() >= listItem.getLimitCustomer()) {
                        iterator.remove();
                    }
                }
            }
            return voucherShipSCMapper.toDto(lst);
        }
        return voucherShipSCMapper.toDto(voucherShipSCRepository.getAllVoucherShipSC(voucherFreeShipDTO.getCode()));
    }

    @Override
    public ServiceResult<VoucherShipSCDTO> findByCodeSC(String code) {
        ServiceResult<VoucherShipSCDTO> result = new ServiceResult<>();
        VoucherFreeShip voucherFreeShip = voucherShipSCRepository.findByCode(code);
        result.setData(voucherShipSCMapper.toDto(voucherFreeShip));
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        return result;
    }
}
