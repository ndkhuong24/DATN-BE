package com.example.backend.core.salesCounter.service.impl;

import com.example.backend.core.admin.repository.OrderAdminRepository;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.OrderDetail;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.salesCounter.dto.OrderSalesDetailDTO;
import com.example.backend.core.salesCounter.repository.OrderSalesCounterDetailRepository;
import com.example.backend.core.salesCounter.service.OrderSalesCounterDetailService;
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
    private OrderAdminRepository orderAdminRepository;

    @Override
    public ServiceResult<OrderSalesDetailDTO> createrOrderDetailSales(OrderSalesDetailDTO orderSalesDetailDTO) {
        ServiceResult<OrderSalesDetailDTO> result = new ServiceResult<>();

        if (orderSalesDetailDTO.getIdOrder() != null && orderSalesDetailDTO.getIdProductDetail() != null) {
            OrderDetail orderDetail = new OrderDetail();

            Optional<Order> orderOptional = orderAdminRepository.findById(orderSalesDetailDTO.getIdOrder());

            Optional<ProductDetail> productDetail = productDetailRepository.findById(orderSalesDetailDTO.getIdProductDetail());

            if (productDetail.isPresent()) {
                orderDetail.setIdOrder(orderSalesDetailDTO.getIdOrder());
                orderDetail.setIdProductDetail(orderSalesDetailDTO.getIdProductDetail());
                orderDetail.setPrice(orderSalesDetailDTO.getPrice());

                if (orderOptional.get().getStatusPayment() == AppConstant.DA_THANH_TOAN) {
                    orderDetail.setQuantity(orderSalesDetailDTO.getQuantity());
                    productDetail.get().setQuantity(productDetail.get().getQuantity() - orderSalesDetailDTO.getQuantity());
                } else {
                    orderDetail.setQuantity(orderSalesDetailDTO.getQuantity());
                }

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
