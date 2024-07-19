package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.OrderDTO;

import java.util.List;
import java.util.Map;

public interface OrderService {
    ServiceResult<OrderDTO> createOrder(OrderDTO orderDTO);

    ServiceResult<OrderDTO> cancelOrderView(OrderDTO orderDTO);

    List<OrderDTO> getAll(OrderDTO orderDTO);

    ServiceResult<OrderDTO> createOrderNotLogin(OrderDTO orderDTO);

    ServiceResult<OrderDTO> traCuuDonHang(OrderDTO orderDTO);

    ServiceResult<OrderDTO> hoanThanhDonHang(OrderDTO orderDTO);

//    Map<String, Object> getAllByOrder(Long idOrder);
}
