package com.example.backend.core.salesCounter.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.OrderDetail;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.salesCounter.dto.OrderSalesDTO;
import com.example.backend.core.salesCounter.dto.OrderSalesDetailDTO;
import com.example.backend.core.salesCounter.mapper.OrderSalesCounterDetailsMapper;
import com.example.backend.core.salesCounter.repository.OrderSalesCounterDetailRepository;
import com.example.backend.core.salesCounter.service.OrderSalesCounterDetailService;
import com.example.backend.core.view.dto.OrderDetailDTO;
import com.example.backend.core.view.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderSalesCounterDetailServiceimpl implements OrderSalesCounterDetailService {

    @Autowired
    private OrderSalesCounterDetailRepository orderSalesCounterDetailRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;
    @Autowired
    private OrderSalesCounterDetailsMapper orderSalesCounterDetailsMapper;

    @Override
    public ServiceResult<OrderSalesDetailDTO> createrOrderDetailSales(OrderSalesDetailDTO orderSalesDetailDTO) {
        ServiceResult<OrderSalesDetailDTO> result = new ServiceResult<>();
        if(orderSalesDetailDTO.getIdOrder() !=null && orderSalesDetailDTO.getIdProductDetail() !=null){
            OrderDetail orderDetail = new OrderDetail();
            Optional<ProductDetail> productDetail = productDetailRepository.findById(orderSalesDetailDTO.getIdProductDetail());
            if(productDetail.isPresent()){
                orderDetail.setIdOrder(orderSalesDetailDTO.getIdOrder());
                orderDetail.setIdProductDetail(orderSalesDetailDTO.getIdProductDetail());
                orderDetail.setPrice(orderSalesDetailDTO.getPrice());
                orderDetail.setQuantity(orderSalesDetailDTO.getQuantity());
                productDetail.get().setQuantity(productDetail.get().getQuantity() - orderSalesDetailDTO.getQuantity());
                orderDetail.setStatus(0);
                orderSalesCounterDetailRepository.save(orderDetail);
                productDetailRepository.save(productDetail.get());
                result.setStatus(HttpStatus.OK);
                result.setMessage("Success");
                result.setData(orderSalesDetailDTO);
            }
        }
        return result;
    }
}
