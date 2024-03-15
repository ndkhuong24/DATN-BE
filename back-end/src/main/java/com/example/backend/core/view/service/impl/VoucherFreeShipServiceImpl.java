package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.model.VoucherFreeShip;
import com.example.backend.core.view.dto.VoucherDTO;
import com.example.backend.core.view.dto.VoucherFreeShipDTO;
import com.example.backend.core.view.mapper.VoucherFreeShipMapper;
import com.example.backend.core.view.repository.OrderRepository;
import com.example.backend.core.view.repository.VoucherFreeShipRepository;
import com.example.backend.core.view.service.VoucherFreeShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class VoucherFreeShipServiceImpl implements VoucherFreeShipService {

    @Autowired
    private VoucherFreeShipRepository voucherFreeShipRepository;

    @Autowired
    private VoucherFreeShipMapper voucherFreeShipMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<VoucherFreeShipDTO> getAllVoucherShip(VoucherFreeShipDTO voucherFreeShipDTO) {
        if(null != voucherFreeShipDTO.getIdCustomerLogin()){
            List<VoucherFreeShip> lst = voucherFreeShipRepository.getAllVoucherShipByCustomer(voucherFreeShipDTO.getCode(), voucherFreeShipDTO.getIdCustomerLogin());
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
            return voucherFreeShipMapper.toDto(lst);
        }
        return voucherFreeShipMapper.toDto(voucherFreeShipRepository.getAllVoucherShip(voucherFreeShipDTO.getCode()));
    }

    @Override
    public ServiceResult<VoucherFreeShipDTO> findByCode(String code) {
        ServiceResult<VoucherFreeShipDTO> result = new ServiceResult<>();
        VoucherFreeShip voucherFreeShip = voucherFreeShipRepository.findByCode(code);
        result.setData(voucherFreeShipMapper.toDto(voucherFreeShip));
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        return result;
    }
}
