package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.admin.dto.StaffAdminDTO;
import com.example.backend.core.admin.mapper.CustomerAdminMapper;
import com.example.backend.core.admin.mapper.OrderAdminMapper;
import com.example.backend.core.admin.mapper.StaffAdminMapper;
import com.example.backend.core.admin.repository.*;
import com.example.backend.core.admin.service.OrderAdminService;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.OrderDetail;
import com.example.backend.core.model.OrderHistory;
import com.example.backend.core.model.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderAdminServiceImpl implements OrderAdminService {
    @Autowired
    private OrderAdminMapper orderAdminMapper;

    @Autowired
    private OrderAdminRepository orderAdminRepository;

    @Autowired
    private CustomerAdminRepository customerAdminRepository;

    @Autowired
    private CustomerAdminMapper customerAdminMapper;

    @Autowired
    private OrderHistoryAdminRepository orderHistoryAdminRepository;

    @Autowired
    private OrderAdminCustomerRepository orderAdminCustomerRepository;

    @Autowired
    private StaffAdminRepository staffAdminRepository;

    @Autowired
    private StaffAdminMapper staffMapper;

    @Autowired
    private ProductDetailAdminRepository productDetailAdminRepository;

    @Autowired
    private OrderDetailAdminRepository orderDetailAdminRepository;

    @Override
    public List<OrderAdminDTO> getAllOrderAdmin(OrderAdminDTO orderAdminDTO) {
        List<OrderAdminDTO> orderAdminDTOSList = orderAdminCustomerRepository.getAllOrderAdmin(orderAdminDTO);

        return orderAdminDTOSList.stream().map(c -> {
            if (c.getIdCustomer() != null) {
                CustomerAdminDTO customerAdminDTO = customerAdminMapper.toDto(
                        customerAdminRepository.findById(c.getIdCustomer())
                                .orElse(null)
                );
                c.setCustomerAdminDTO(customerAdminDTO);
            }

            if (c.getIdStaff() != null) {
                StaffAdminDTO staffAdminDTO = staffMapper.toDto(staffAdminRepository.findById(c.getIdStaff()).orElse(null));
                c.setStaffAdminDTO(staffAdminDTO);
            }
            return c;

        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, Integer> totalStatusOrderAdmin(OrderAdminDTO orderAdminDTO) {
        return orderAdminCustomerRepository.totalStatusOrder(orderAdminDTO);
    }

    @Override
    public ServiceResult<OrderAdminDTO> updateStatusChoXuLy(OrderAdminDTO orderAdminDTO) {
        ServiceResult<OrderAdminDTO> orderAdminDTOServiceResult = new ServiceResult<>();

        if (orderAdminDTO.getId() == null) {
            orderAdminDTOServiceResult.setData(null);
            orderAdminDTOServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            orderAdminDTOServiceResult.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác");
            return orderAdminDTOServiceResult;
        }

        if (orderAdminDTO.getIdStaff() == null) {
            orderAdminDTOServiceResult.setData(null);
            orderAdminDTOServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            orderAdminDTOServiceResult.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác");
            return orderAdminDTOServiceResult;
        }

        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();

        if (order.getStatus() == AppConstant.CHO_XU_LY) {
            orderAdminDTOServiceResult.setData(null);
            orderAdminDTOServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            orderAdminDTOServiceResult.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác!");
            return orderAdminDTOServiceResult;
        } else {
            order.setStatus(AppConstant.CHO_XU_LY);
            order.setIdStaff(orderAdminDTO.getIdStaff());
            order = orderAdminRepository.save(order);

            if (order != null) {
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setStatus(AppConstant.XU_LY_HISTORY);
                orderHistory.setCreateDate(LocalDateTime.now());
                orderHistory.setIdOrder(order.getId());
                orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
                orderHistory.setNote(orderAdminDTO.getNote());
                orderHistoryAdminRepository.save(orderHistory);

                if (order.getStatusPayment() == AppConstant.CHUA_THANH_TOAN) {
                    List<OrderDetail> orderDetailList = orderDetailAdminRepository.findByIdOrder(orderAdminDTO.getId());

                    for (OrderDetail orderDetail : orderDetailList) {
                        int quantity = orderDetail.getQuantity();
                        Long productId = orderDetail.getIdProductDetail();

                        // Cập nhật số lượng trong kho dựa trên productId và quantity
                        ProductDetail productDetail = productDetailAdminRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
                        productDetail.setQuantity(productDetail.getQuantity() - quantity);
                        productDetailAdminRepository.save(productDetail);
                    }
                }
            }

            orderAdminDTOServiceResult.setData(orderAdminMapper.toDto(order));
            orderAdminDTOServiceResult.setStatus(HttpStatus.OK);
            orderAdminDTOServiceResult.setMessage("Success");
            return orderAdminDTOServiceResult;
        }
    }

    @Override
    public ServiceResult<OrderAdminDTO> huyDonHang(OrderAdminDTO orderAdminDTO) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();

        if (orderAdminDTO.getId() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác");
            return result;
        }

        if (orderAdminDTO.getIdStaff() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác");
            return result;
        }

        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();

        if (order.getStatus() == AppConstant.HUY_DON_HANG) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác!");
            return result;
        } else {
            if (order != null) {
                List<OrderDetail> orderDetailList = orderDetailAdminRepository.findByIdOrder(order.getId());

                for (OrderDetail orderDetail : orderDetailList) {
                    ProductDetail productDetail = productDetailAdminRepository.findById(orderDetail.getIdProductDetail()).orElse(null);

                    if (order.getStatusPayment() == 1) {
                        if (order.getStatus() == 0) {
                            productDetail.setQuantity(productDetail.getQuantity());
                        } else if (order.getStatus() == 1) {
                            productDetail.setQuantity(productDetail.getQuantity() + orderDetail.getQuantity());
                        }
                    } else {
                        productDetail.setQuantity(productDetail.getQuantity() + orderDetail.getQuantity());
                    }

                    productDetailAdminRepository.save(productDetail);
                }

                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setStatus(AppConstant.HUY_HISTORY);
                orderHistory.setCreateDate(LocalDateTime.now());
                orderHistory.setIdOrder(order.getId());
                orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
                orderHistory.setNote(orderAdminDTO.getNote());
                orderHistoryAdminRepository.save(orderHistory);
            }

            order.setStatus(AppConstant.HUY_DON_HANG);
            order.setIdStaff(orderAdminDTO.getIdStaff());
            order = orderAdminRepository.save(order);

            result.setData(orderAdminMapper.toDto(order));
            result.setStatus(HttpStatus.OK);
            result.setMessage("Success");
            result.setSuccess(true);
            return result;
        }
    }

    @Override
    public ServiceResult<OrderAdminDTO> giaoHangDonHang(OrderAdminDTO orderAdminDTO) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();

        if (orderAdminDTO.getId() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }

        if (orderAdminDTO.getIdStaff() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }

        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();

        if (order.getStatus() == AppConstant.DANG_GIAO_HANG) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác!");
            return result;
        } else {
            order.setShipperPhone("0962917587");
            order.setDeliveryDate(LocalDateTime.now());
            order.setMissedOrder(0);
            order.setStatus(AppConstant.DANG_GIAO_HANG);
//        order.setIdStaff(orderAdminDTO.getIdStaff());
            order = orderAdminRepository.save(order);

            if (order != null) {
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setStatus(AppConstant.GIAO_HANG_HISTORY);
                orderHistory.setCreateDate(LocalDateTime.now());
                orderHistory.setIdOrder(order.getId());
                orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
                orderHistory.setNote(orderAdminDTO.getNote());
                orderHistoryAdminRepository.save(orderHistory);
            }

            result.setData(orderAdminMapper.toDto(order));
            result.setStatus(HttpStatus.OK);
            result.setMessage("Success");
            return result;
        }
    }

    @Override
    public ServiceResult<OrderAdminDTO> hoanThanhDonHang(OrderAdminDTO orderAdminDTO) {
        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();

        if (orderAdminDTO.getId() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }
        if (orderAdminDTO.getIdStaff() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Error");
            return result;
        }

        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();

        if (order.getStatus() == AppConstant.HOAN_THANH) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác!");
            return result;
        } else {
            if (order.getPaymentType() == 0) {
                order.setPaymentDate(LocalDateTime.now());
                order.setTotalPayment(order.getTotalPrice().add(order.getShipPrice()));
                order.setStatusPayment(AppConstant.DA_THANH_TOAN);
            }
            order.setReceivedDate(LocalDateTime.now());
            order.setStatus(AppConstant.HOAN_THANH);
//        order.setIdStaff(orderAdminDTO.getIdStaff());
            order = orderAdminRepository.save(order);
            if (order != null) {
                OrderHistory orderHistory = new OrderHistory();
                orderHistory.setStatus(AppConstant.HOAN_THANH_HISTORY);
                orderHistory.setCreateDate(LocalDateTime.now());
                orderHistory.setIdOrder(order.getId());
                orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
                orderHistory.setNote(orderAdminDTO.getNote());
                orderHistoryAdminRepository.save(orderHistory);
            }
            result.setData(orderAdminMapper.toDto(order));
            result.setStatus(HttpStatus.OK);
            result.setMessage("Success");
            return result;
        }
    }

//    @Override
//    public ServiceResult<OrderAdminDTO> boLoDonHang(OrderAdminDTO orderAdminDTO) {
//        ServiceResult<OrderAdminDTO> result = new ServiceResult<>();
//        if (orderAdminDTO.getId() == null) {
//            result.setData(null);
//            result.setStatus(HttpStatus.BAD_REQUEST);
//            result.setMessage("Error");
//            return result;
//        }
//        if (orderAdminDTO.getIdStaff() == null) {
//            result.setData(null);
//            result.setStatus(HttpStatus.BAD_REQUEST);
//            result.setMessage("Error");
//            return result;
//        }
//        Order order = orderAdminRepository.findById(orderAdminDTO.getId()).get();
//        orderAdminDTO.setMissedOrder(order.getMissedOrder());
//        if (order.getMissedOrder() == null || order.getMissedOrder() == 0) {
//            order.setMissedOrder(AppConstant.BO_LO_LAN1);
//        } else if (order.getMissedOrder() == AppConstant.BO_LO_LAN1) {
//            order.setMissedOrder(AppConstant.BO_LO_LAN2);
//        } else {
//            order.setMissedOrder(AppConstant.BO_LO_LAN3);
//            order.setStatus(AppConstant.HUY_DON_HANG);
//        }
//        order.setIdStaff(orderAdminDTO.getIdStaff());
//        order = orderAdminRepository.save(order);
//        if (orderAdminDTO.getMissedOrder() == null || orderAdminDTO.getMissedOrder() == 0) {
//            OrderHistory orderHistory = new OrderHistory();
//            orderHistory.setStatus(AppConstant.BO_LO_LAN1_HISTORY);
//            orderHistory.setCreateDate(LocalDateTime.now());
//            orderHistory.setIdOrder(order.getId());
//            orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
//            orderHistory.setNote(orderAdminDTO.getNote());
//            orderHistoryAdminRepository.save(orderHistory);
//        } else if (orderAdminDTO.getMissedOrder() == AppConstant.BO_LO_LAN1) {
//            OrderHistory orderHistory = new OrderHistory();
//            orderHistory.setStatus(AppConstant.BO_LO_LAN2_HISTORY);
//            orderHistory.setCreateDate(LocalDateTime.now());
//            orderHistory.setIdOrder(order.getId());
//            orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
//            orderHistory.setNote(orderAdminDTO.getNote());
//            orderHistoryAdminRepository.save(orderHistory);
//        } else {
//            OrderHistory orderHistory = new OrderHistory();
//            orderHistory.setStatus(AppConstant.BO_LO_LAN3_HISTORY);
//            orderHistory.setCreateDate(LocalDateTime.now());
//            orderHistory.setIdOrder(order.getId());
//            orderHistory.setIdStaff(orderAdminDTO.getIdStaff());
//            orderHistory.setNote(orderAdminDTO.getNote());
//            orderHistoryAdminRepository.save(orderHistory);
//        }
//        result.setData(orderAdminMapper.toDto(order));
//        result.setStatus(HttpStatus.OK);
//        result.setMessage("Success");
//        return result;
//    }
}
