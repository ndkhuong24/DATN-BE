package com.example.backend.core.salesCounter.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.salesCounter.dto.VoucherSCDTO;
import com.example.backend.core.salesCounter.mapper.VoucherSCMapper;
import com.example.backend.core.salesCounter.repository.VoucherSCRepository;
import com.example.backend.core.salesCounter.service.VoucherSCService;
import com.example.backend.core.view.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherSCServiceImpl implements VoucherSCService {

    @Autowired
    private VoucherSCRepository voucherSCRepository;

    @Autowired
    private VoucherSCMapper voucherSCMapper;

    @Autowired
    private OrderRepository orderRepository;

//    @Override
//    public List<VoucherSCDTO> getAllVoucherSC(VoucherSCDTO voucherDTO) {
//        List<Voucher> voucherList;
//
//        if (voucherDTO.getIdCustomerLogin() != null) {
//            voucherList = voucherSCRepository.getAllVoucherByCustomerSC(voucherDTO.getCode(), voucherDTO.getIdCustomerLogin());
//        } else {
//            voucherList = voucherSCRepository.getAllVoucherSC(voucherDTO.getCode());
//        }
//
//        if (voucherList.isEmpty()) {
//            return Collections.emptyList();
//        }
//
//        List<VoucherSCDTO> voucherSCDTOList = voucherSCMapper.toDto(voucherList);
//
//        for (VoucherSCDTO voucherSCDTO : voucherSCDTOList) {
//            int orderCount = getOrderCountForVoucher(voucherSCDTO.getId(), voucherDTO.getIdCustomerLogin());
//            voucherSCDTO.setUseVoucher(orderCount);
//        }
//
//        return voucherSCDTOList;
//    }

    @Override
    public List<VoucherSCDTO> getAllVoucherSC(VoucherSCDTO voucherDTO) {
        List<Object[]> voucherObjects;

        if (voucherDTO.getIdCustomerLogin() != null) {
            voucherObjects = voucherSCRepository.getAllVouchersByCustomerWithUseCount(voucherDTO.getCode(), voucherDTO.getIdCustomerLogin());
        } else {
            voucherObjects = voucherSCRepository.getAllVouchersWithUseCount(voucherDTO.getCode());
        }

        if (voucherObjects.isEmpty()) {
            return Collections.emptyList();
        }

        return voucherObjects.stream()
                .map(this::mapToVoucherSCDTO)
                .collect(Collectors.toList());
    }

    private VoucherSCDTO mapToVoucherSCDTO(Object[] row) {
        VoucherSCDTO voucherSCDTO = new VoucherSCDTO();
        voucherSCDTO.setId(Long.parseLong(row[0].toString()));
        voucherSCDTO.setCode(row[1].toString());
        voucherSCDTO.setName(row[2].toString());
//        voucherSCDTO.setStartDate((LocalDateTime) row[3]);
//        voucherSCDTO.setEndDate((LocalDateTime) row[4]);
        voucherSCDTO.setConditionApply(new BigDecimal(row[5].toString()));
        voucherSCDTO.setVoucherType(Integer.valueOf(row[6].toString()));
        voucherSCDTO.setReducedValue(new BigDecimal(row[7].toString()));
        voucherSCDTO.setDescription(row[8].toString());
        voucherSCDTO.setIsdel(Integer.valueOf(row[9].toString()));
        voucherSCDTO.setQuantity(Integer.valueOf(row[10].toString()));
        voucherSCDTO.setMaxReduced(row[11] != null ? new BigDecimal(row[11].toString()) : null);
        voucherSCDTO.setAllow(row[12] != null ? Integer.valueOf(row[12].toString()) : null);
        voucherSCDTO.setCreateName(row[13].toString());
        voucherSCDTO.setUseVoucher(Integer.parseInt(row[14].toString()));
//        voucherSCDTO.setCreateDate((LocalDateTime) row[15]);

        Timestamp startTimestamp = (Timestamp) row[3];
        Timestamp endTimestamp = (Timestamp) row[4];
        Timestamp createDateTimestamp = (Timestamp) row[15];

        LocalDateTime startDate = startTimestamp.toLocalDateTime();
        LocalDateTime endDate = endTimestamp.toLocalDateTime();
        LocalDateTime createDate = createDateTimestamp.toLocalDateTime();

        voucherSCDTO.setStartDate(startDate);
        voucherSCDTO.setEndDate(endDate);
        voucherSCDTO.setCreateDate(createDate);

        if (LocalDateTime.now().isAfter(endDate)) {
            voucherSCDTO.setStatus(1);
        } else {
            voucherSCDTO.setStatus(0);
        }

//        LocalDateTime endDate = voucherSCDTO.getEndDate();
//        if (LocalDateTime.now().isAfter(endDate)) {
//            voucherSCDTO.setStatus(1);
//        } else {
//            voucherSCDTO.setStatus(0);
//        }

        return voucherSCDTO;
    }

    private int getOrderCountForVoucher(Long voucherId, Long customerId) {
        List<Order> orderList = orderRepository.findByIdCustomerAndCodeVoucher(customerId, voucherId.toString());
        return orderList.size();
    }


//    @Override
//    public List<VoucherSCDTO> getAllVoucherSC(VoucherSCDTO voucherDTO) {
//        if (null != voucherDTO.getIdCustomerLogin()) {
//            List<Voucher> voucherList = voucherSCRepository.getAllVoucherByCustomerSC(voucherDTO.getCode(), voucherDTO.getIdCustomerLogin());
//
//            if (voucherList.isEmpty()) {
//                return null;
//            }
//
//            Iterator<Voucher> iterator = voucherList.iterator();
//
//            while (iterator.hasNext()) {
//                Voucher listItem = iterator.next();
//
//                List<Order> orderList = orderRepository.findByIdCustomerAndCodeVoucher(voucherDTO.getIdCustomerLogin(), listItem.getCode());
//                if (listItem.getOptionCustomer() == 1) {
//                    if (orderList.size() >= listItem.getLimitCustomer()) {
//                        iterator.remove();
//                    }
//                }
//            }
//            return voucherSCMapper.toDto(voucherList);
//        }
//
//        return voucherSCMapper.toDto(voucherSCRepository.getAllVoucherSC(voucherDTO.getCode()));
//    }

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
