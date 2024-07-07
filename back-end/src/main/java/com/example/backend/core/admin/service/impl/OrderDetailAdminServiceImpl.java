package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.*;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.admin.service.OrderDetailAdminService;
import com.example.backend.core.model.OrderHistory;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.dto.OrderHistoryDTO;
import com.example.backend.core.view.mapper.CustomerMapper;
import com.example.backend.core.view.mapper.OrderHistoryMapper;
import com.example.backend.core.view.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailAdminServiceImpl implements OrderDetailAdminService {

    @Autowired
    private OrderDetailAdminRepository orderDetailAdminRepository;

    @Autowired
    private OrderDetailAdminMapper orderDetailAdminMapper;

    @Autowired
    private ProductAdminMapper productAdminMapper;

    private final ProductAdminRepository productAdminRepository;

    private final ProductDetailAdminRepository productDetailAdminRepository;

    @Autowired
    private ImagesAdminRepository imagesAdminRepository;

    @Autowired
    private ImagesAdminMapper imagesAdminMapper;

    @Autowired
    private ColorAdminMapper colorAdminMapper;

    @Autowired
    private ColorAdminRepository colorAdminRepository;

    @Autowired
    private SizeAdminRepository sizeAdminRepository;

    @Autowired
    private SizeAdminMapper sizeAdminMapper;

    @Autowired
    private ProductDetailAdminMapper productDetailAdminMapper;

    @Autowired
    private OrderHistoryAdminRepository orderHistoryAdminRepository;

    @Autowired
    private OrderHistoryAdminMapper orderHistoryAdminMapper;

    @Autowired
    private StaffAdminMapper staffMapper;

    @Autowired
    private StaffAdminRepository staffAdminRepository;

    @Autowired
    private StaffAdminMapper staffAdminMapper;

    @Autowired
    private OrderHistoryMapper orderHistoryMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerRepository customerRepository;

    public OrderDetailAdminServiceImpl(ProductAdminRepository productAdminRepository, ProductDetailAdminRepository productDetailAdminRepository) {
        this.productAdminRepository = productAdminRepository;
        this.productDetailAdminRepository = productDetailAdminRepository;
    }

    @Override
    public Map<String, Object> getAllByOrder(Long idOrder) {
        Map<String, Object> map = new HashMap<>();

        if (idOrder == null) {
            return null;
        }

        List<OrderHistoryAdminDTO> orderHistoryAdminDTOList = new ArrayList<>();
        List<OrderHistoryDTO> orderHistoryViewList = new ArrayList<>();

        List<OrderHistory> orderHistoryList = orderHistoryAdminRepository.getAllOrderHistoryByOrder(idOrder);

        for (int i = 0; i < orderHistoryList.size(); i++) {
            if (null != orderHistoryList.get(i).getIdStaff()) {
                if (staffAdminRepository.findById(orderHistoryList.get(i).getIdStaff()).isPresent()) {
                    StaffAdminDTO staffAdminDTO = staffAdminMapper.toDto(staffAdminRepository.findById(orderHistoryList.get(i).getIdStaff()).orElse(null));
                    OrderHistoryAdminDTO orderHistoryAdminDTO = orderHistoryAdminMapper.toDto(orderHistoryList.get(i));
                    orderHistoryAdminDTO.setStaffDTO(staffAdminDTO);
                    orderHistoryAdminDTOList.add(orderHistoryAdminDTO);
                }
            }
        }

        for (int i = 0; i < orderHistoryList.size(); i++) {
            if (null != orderHistoryList.get(i).getIdCustomer()) {
                if (customerRepository.findById(orderHistoryList.get(i).getIdCustomer()).isPresent()) {
                    CustomerDTO customerDTO = customerMapper.toDto(customerRepository.findById(orderHistoryList.get(i).getIdCustomer()).orElse(null));
                    OrderHistoryDTO orderHistoryAdminDTO = orderHistoryMapper.toDto(orderHistoryList.get(i));
                    orderHistoryAdminDTO.setCustomerDTO(customerDTO);
                    orderHistoryViewList.add(orderHistoryAdminDTO);
                }
            }
        }

        List<OrderDetailAdminDTO> orderDetailAdminDTOList = orderDetailAdminMapper.toDto(orderDetailAdminRepository.findByIdOrder(idOrder));

        for (int i = 0; i < orderDetailAdminDTOList.size(); i++) {
            // Lấy chi tiết sản phẩm từ repository và chuyển đổi sang DTO
            ProductDetailAdminDTO productDetailAdminDTO = productDetailAdminMapper.toDto(productDetailAdminRepository.findById(orderDetailAdminDTOList.get(i).getIdProductDetail()).get());

            // Lấy sản phẩm từ repository và chuyển đổi sang DTO
            ProductAdminDTO productAdminDTO = productAdminMapper.toDto(productAdminRepository.findById(productDetailAdminDTO.getIdProduct()).get());

            // Đặt URL hình ảnh cho sản phẩm
            String imageURL = "http://localhost:8081/view/anh/" + productDetailAdminDTO.getIdProduct();
            productAdminDTO.setImageURL(imageURL);

            // Gán ProductAdminDTO vào ProductDetailAdminDTO
            productDetailAdminDTO.setProductDTO(productAdminDTO);

            // Lấy và gán màu sắc cho ProductDetailAdminDTO
            ColorAdminDTO colorDTO = colorAdminMapper.toDto(colorAdminRepository.findById(productDetailAdminDTO.getIdColor()).get());
            productDetailAdminDTO.setColorDTO(colorDTO);

            // Lấy và gán kích cỡ cho ProductDetailAdminDTO
            SizeAdminDTO sizeDTO = sizeAdminMapper.toDto(sizeAdminRepository.findById(productDetailAdminDTO.getIdSize()).get());
            productDetailAdminDTO.setSizeDTO(sizeDTO);

            // Gán ProductDetailAdminDTO vào OrderDetailAdminDTO tại vị trí i
            orderDetailAdminDTOList.get(i).setProductDetailDTO(productDetailAdminDTO);
        }

        map.put("orderDetail", orderDetailAdminDTOList);
        map.put("orderHistoryAdmin", orderHistoryAdminDTOList);
        map.put("orderHistoryView", orderHistoryViewList);
        return map;
    }
}
