package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Cart;
import com.example.backend.core.model.Product;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.view.dto.*;
import com.example.backend.core.view.mapper.ColorMapper;
import com.example.backend.core.view.mapper.ProductDetailMapper;
import com.example.backend.core.view.mapper.ProductMapper;
import com.example.backend.core.view.mapper.SizeMapper;
import com.example.backend.core.view.repository.*;
import com.example.backend.core.view.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private ColorMapper colorMapper;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private SizeMapper sizeMapper;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public ServiceResult<CartDTO> getCart(CartDTO cartDTO) {
        ServiceResult<CartDTO> cartDTOServiceResult = new ServiceResult<>();

        CartDTO cartDTO1 = new CartDTO();

        Optional<Product> product = productRepository.findById(cartDTO.getProductId());

        if (!product.isPresent()) {
            cartDTOServiceResult.setData(null);
            cartDTOServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            cartDTOServiceResult.setMessage("Không tìm thấy product!");
            return cartDTOServiceResult;
        }

        ProductDTO productDTO = productMapper.toDto(product.get());
        String imageURL = "http://localhost:8081/view/anh/" + cartDTO.getProductId();
        productDTO.setImageURL(imageURL);

        ProductDetail productDetail = productDetailRepository.findByIdSizeAndIdColorAndIdProduct(cartDTO.getProductDetailDTO().getColorDTO().getId(), cartDTO.getProductDetailDTO().getSizeDTO().getId(), cartDTO.getProductId());

        if (productDetail == null) {
            cartDTOServiceResult.setData(null);
            cartDTOServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            cartDTOServiceResult.setMessage("product detail is null");
            return cartDTOServiceResult;
        }

        ColorDTO colorDTO = colorMapper.toDto(colorRepository.findById(productDetail.getIdColor()).get());
        SizeDTO sizeDTO = sizeMapper.toDto(sizeRepository.findById(productDetail.getIdSize()).get());

        ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetail);

        productDetailDTO.setSizeDTO(sizeDTO);
        productDetailDTO.setColorDTO(colorDTO);

        productDTO.setProductDetailDTO(productDetailDTO);

        cartDTO1.setProductId(product.get().getId());
        cartDTO1.setProductName(product.get().getName());
        cartDTO1.setQuantity(cartDTO.getQuantity());

        cartDTO1.setProductDTO(productDTO);
        cartDTO1.setProductDetailDTO(productDetailDTO);

        cartDTOServiceResult.setStatus(HttpStatus.OK);
        cartDTOServiceResult.setMessage("Success");
        cartDTOServiceResult.setData(cartDTO1);
        cartDTOServiceResult.setSuccess(true);
        return cartDTOServiceResult;
    }

    @Override
    public ServiceResult<List<Cart>> addToCart(Cart cart) {
        ServiceResult<List<Cart>> cartServiceResult = new ServiceResult<>();

        // Kiểm tra xem mục giỏ hàng đã tồn tại chưa
        Optional<Cart> existingCart = cartRepository.findByIdProductAndIdColorAndIdSizeAndIdCustomer(
                cart.getIdProduct(),
                cart.getIdColor(),
                cart.getIdSize(),
                cart.getIdCustomer()
        );

        if (existingCart.isPresent()) {
            Cart existing = existingCart.get();
            existing.setQuantity(existing.getQuantity() + cart.getQuantity());
            cartRepository.save(existing);

            List<Cart> cartList = cartRepository.findAllByIdCustomer(existing.getIdCustomer());

            cartServiceResult.setStatus(HttpStatus.OK);
            cartServiceResult.setMessage("Updated existing cart item.");
            cartServiceResult.setData(cartList);
            cartServiceResult.setSuccess(true);
        } else {
            cartRepository.save(cart);

            List<Cart> cartList = cartRepository.findAllByIdCustomer(cart.getIdCustomer());

            cartServiceResult.setStatus(HttpStatus.OK);
            cartServiceResult.setMessage("Added new cart item.");
            cartServiceResult.setData(cartList);
            cartServiceResult.setSuccess(true);
        }
        return cartServiceResult;
    }


    @Override
    public ServiceResult<List<Cart>> getCartByCustomer(Long id) {
        ServiceResult<List<Cart>> cartServiceResult = new ServiceResult<>();

        List<Cart> cartList = cartRepository.findAllByIdCustomer(id);

        if (cartList != null && !cartList.isEmpty()) {
            cartServiceResult.setSuccess(true);
            cartServiceResult.setData(cartList);
            cartServiceResult.setMessage("Success");
            cartServiceResult.setStatus(HttpStatus.OK);
        } else {
            cartServiceResult.setSuccess(false);
            cartServiceResult.setData(Collections.emptyList());
            cartServiceResult.setMessage("No carts found for the given customer ID.");
            cartServiceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return cartServiceResult;
    }

    @Override
    public ServiceResult<List<Cart>> giamSoLuong(Cart cart) {
        ServiceResult<List<Cart>> cartServiceResult = new ServiceResult<>();

        // Tìm mục giỏ hàng hiện có
        Optional<Cart> existingCart = cartRepository.findByIdProductAndIdColorAndIdSizeAndIdCustomer(
                cart.getIdProduct(),
                cart.getIdColor(),
                cart.getIdSize(),
                cart.getIdCustomer()
        );

        if (existingCart.isPresent()) {
            Cart existing = existingCart.get();

            // Giảm số lượng đi 1
            existing.setQuantity(existing.getQuantity() - 1);

            // Nếu số lượng còn lại bằng 1 thì xóa mục giỏ hàng
            if (existing.getQuantity() <= 0) {
                cartRepository.delete(existing);
            } else {
                cartRepository.save(existing);
            }

            // Lấy danh sách giỏ hàng sau khi cập nhật
            List<Cart> cartList = cartRepository.findAllByIdCustomer(existing.getIdCustomer());

            cartServiceResult.setStatus(HttpStatus.OK);
            cartServiceResult.setMessage("Updated or removed cart item.");
            cartServiceResult.setData(cartList);
            cartServiceResult.setSuccess(true);
        } else {
            cartServiceResult.setStatus(HttpStatus.NOT_FOUND);
            cartServiceResult.setMessage("Cart item not found.");
            cartServiceResult.setData(Collections.emptyList());
            cartServiceResult.setSuccess(false);
        }

        return cartServiceResult;
    }

    @Override
    public ServiceResult<List<Cart>> tangSoLuong(Cart cart) {
        ServiceResult<List<Cart>> cartServiceResult = new ServiceResult<>();

        // Tìm mục giỏ hàng hiện có
        Optional<Cart> existingCart = cartRepository.findByIdProductAndIdColorAndIdSizeAndIdCustomer(
                cart.getIdProduct(),
                cart.getIdColor(),
                cart.getIdSize(),
                cart.getIdCustomer()
        );

        if (existingCart.isPresent()) {
            Cart existing = existingCart.get();

            // Giảm số lượng đi 1
            existing.setQuantity(existing.getQuantity() + 1);
            cartRepository.save(existing);

            // Lấy danh sách giỏ hàng sau khi cập nhật
            List<Cart> cartList = cartRepository.findAllByIdCustomer(existing.getIdCustomer());

            cartServiceResult.setStatus(HttpStatus.OK);
            cartServiceResult.setMessage("Updated or removed cart item.");
            cartServiceResult.setData(cartList);
            cartServiceResult.setSuccess(true);
        }

        return cartServiceResult;
    }

    @Override
    public ServiceResult<List<Cart>> xoa(Cart cart) {
        ServiceResult<List<Cart>> cartServiceResult = new ServiceResult<>();

        // Tìm mục giỏ hàng hiện có
        Optional<Cart> existingCart = cartRepository.findByIdProductAndIdColorAndIdSizeAndIdCustomer(
                cart.getIdProduct(),
                cart.getIdColor(),
                cart.getIdSize(),
                cart.getIdCustomer()
        );


        if (existingCart.isPresent()) {
            Cart existing = existingCart.get();

            cartRepository.delete(existing);

            // Lấy danh sách giỏ hàng sau khi cập nhật
            List<Cart> cartList = cartRepository.findAllByIdCustomer(existing.getIdCustomer());

            cartServiceResult.setStatus(HttpStatus.OK);
            cartServiceResult.setMessage("Updated or removed cart item.");
            cartServiceResult.setData(cartList);
            cartServiceResult.setSuccess(true);
        } else {
            cartServiceResult.setStatus(HttpStatus.NOT_FOUND);
            cartServiceResult.setMessage("Cart item not found.");
            cartServiceResult.setData(Collections.emptyList());
            cartServiceResult.setSuccess(false);
        }

        return cartServiceResult;
    }
}
