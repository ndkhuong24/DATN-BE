package com.example.backend.core.view.service;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Cart;
import com.example.backend.core.view.dto.CartDTO;

import java.util.List;

public interface CartService {
    ServiceResult<CartDTO> getCart(CartDTO cartDTO);

    ServiceResult<List<Cart>> addToCart(Cart cart);

    ServiceResult<List<Cart>> getCartByCustomer(Long id);

    ServiceResult<List<Cart>> giamSoLuong(Cart cart);

    ServiceResult<List<Cart>> tangSoLuong(Cart cart);

    ServiceResult<List<Cart>> xoa(Cart cart);
}
