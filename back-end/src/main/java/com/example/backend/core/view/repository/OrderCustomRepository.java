package com.example.backend.core.view.repository;

import com.example.backend.core.view.dto.OrderDTO;

import java.util.List;

public interface OrderCustomRepository {

    List<OrderDTO> getAllOrderByCustomerSearch(OrderDTO orderDTO);
}
