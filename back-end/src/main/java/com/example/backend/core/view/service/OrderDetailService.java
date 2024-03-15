package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.dto.OrderDetailDTO;

import java.util.List;
import java.util.Map;

public interface OrderDetailService {

    Map<String, Object> getAllByOrder(Long idOrder);

    ServiceResult<OrderDetailDTO> createOrderDetail(OrderDetailDTO orderDetailDTO);
}
