package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.view.dto.CartDTO;
import com.example.backend.core.view.dto.ProductDTO;

import java.util.List;

public interface CartService {

    ServiceResult<CartDTO> getCart(CartDTO cartDTO);
}
