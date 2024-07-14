package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.*;
import com.example.backend.core.view.dto.*;
import com.example.backend.core.view.mapper.*;
import com.example.backend.core.view.repository.*;
import com.example.backend.core.view.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private VoucherRepository voucherRepository;

    @Autowired
    private VoucherFreeShipRepository voucherFreeShipRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private OrderCustomRepository orderCustomRepository;

    @Autowired
    private OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ProductDetailMapper productDetailMapper;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ImagesRepository imagesRepository;
    @Autowired
    private ImagesMapper imagesMapper;

    @Autowired
    private ColorRepository colorRepository;
    @Autowired
    private ColorMapper colorMapper;

    @Autowired
    private SizeRepository sizeRepository;
    @Autowired
    private SizeMapper sizeMapper;


    @Override
    public ServiceResult<OrderDTO> createOrder(OrderDTO orderDTO) {
        ServiceResult<OrderDTO> result = new ServiceResult<>();
        OrderDTO dto = new OrderDTO();
        Order order = new Order();
        CustomerDTO customerDTO = customerMapper.toDto(customerRepository.findByCode(orderDTO.getCustomerDTO().getCode()));
        if (customerDTO != null) {
            order.setCode("HD" + Instant.now().getEpochSecond());
            order.setCreateDate(LocalDateTime.now());
            order.setReceiver(orderDTO.getReceiver());
            order.setIdCustomer(customerDTO.getId());
            order.setShipPrice(orderDTO.getShipPrice());
            order.setTotalPrice(orderDTO.getTotalPrice());
            order.setReceiverPhone(orderDTO.getReceiverPhone());
            order.setAddressReceived(orderDTO.getAddressReceived());
            order.setDescription(orderDTO.getDescription());
            order.setEmail(orderDTO.getEmail());
            order.setType(0);
            if (orderDTO.getPaymentType() == 1) {
                order.setPaymentType(orderDTO.getPaymentType());
                order.setTotalPayment(orderDTO.getTotalPayment());
                order.setPaymentDate(LocalDateTime.now());
                order.setStatusPayment(AppConstant.DA_THANH_TOAN);
            } else {
                order.setPaymentType(orderDTO.getPaymentType());
                order.setTotalPayment(orderDTO.getTotalPayment());
                order.setStatusPayment(AppConstant.CHUA_THANH_TOAN);
            }
            order.setStatus(AppConstant.CHO_XAC_NHAN);
            if (StringUtils.isNotBlank(orderDTO.getCodeVoucher())) {
                Voucher voucher = voucherRepository.findByCode(orderDTO.getCodeVoucher());
                if (null != voucher) {
                    voucher.setAmountUsed(voucher.getAmountUsed() + 1);
                    if (voucher.getAmountUsed() == voucher.getQuantity()) {
                        voucher.setIdel(0);
                    }
                    order.setCodeVoucher(voucher.getCode());
                    voucherRepository.save(voucher);
                }
            }
            if (StringUtils.isNotBlank(orderDTO.getCodeVoucherShip())) {
                VoucherFreeShip voucherFreeShip = voucherFreeShipRepository.findByCode(orderDTO.getCodeVoucherShip());
                if (null != voucherFreeShip) {
                    voucherFreeShip.setAmountUsed(voucherFreeShip.getAmountUsed() + 1);
                    if (voucherFreeShip.getAmountUsed() == voucherFreeShip.getQuantity()) {
                        voucherFreeShip.setIdel(0);
                    }
                    order.setCodeVoucherShip(voucherFreeShip.getCode());
                    voucherFreeShipRepository.save(voucherFreeShip);
                }
            }
            order = orderRepository.save(order);
            dto = orderMapper.toDto(order);
            result.setData(dto);
            result.setStatus(HttpStatus.OK);
            result.setMessage("Success");
        }
        return result;
    }

    @Override
    public ServiceResult<OrderDTO> cancelOrderView(OrderDTO orderDTO) {
        ServiceResult<OrderDTO> result = new ServiceResult<>();
        if (orderDTO.getId() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác");
            return result;
        }
        if (orderDTO.getIdCustomer() == null) {
            result.setData(null);
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setMessage("Đã có lỗi xảy ra khi bạn đang thao tác");
            return result;
        }
        Order order = orderRepository.findById(orderDTO.getId()).orElse(null);
        if (order != null) {
            order.setStatus(AppConstant.HUY_DON_HANG);
            order = orderRepository.save(order);
            List<OrderDetail> orderDetailList = orderDetailRepository.findByIdOrder(order.getId());
            for (int i = 0; i < orderDetailList.size(); i++) {
                ProductDetail productDetail = productDetailRepository.findById(orderDetailList.get(i).getIdProductDetail()).orElse(null);
                productDetail.setQuantity(productDetail.getQuantity() + orderDetailList.get(i).getQuantity());
                productDetailRepository.save(productDetail);
            }
            OrderHistory orderHistory = new OrderHistory();
            orderHistory.setStatus(AppConstant.HUY_HISTORY);
            orderHistory.setCreateDate(LocalDateTime.now());
            orderHistory.setIdOrder(order.getId());
            orderHistory.setIdCustomer(orderDTO.getIdCustomer());
            orderHistory.setNote(orderDTO.getNote());
            orderHistoryRepository.save(orderHistory);
            result.setData(orderMapper.toDto(order));
            result.setStatus(HttpStatus.OK);
            result.setMessage("Success");
        }
        return result;
    }

    @Override
    public List<OrderDTO> getAll(OrderDTO orderDTO) {
        if (orderDTO.getIdCustomer() == null) {
            return null;
        }
        return orderCustomRepository.getAllOrderByCustomerSearch(orderDTO);
    }

    @Override
    public ServiceResult<OrderDTO> createOrderNotLogin(OrderDTO orderDTO) {
        ServiceResult<OrderDTO> result = new ServiceResult<>();
        OrderDTO dto = new OrderDTO();
        Order order = new Order();

        order.setCode("HD" + Instant.now().getEpochSecond());
        order.setCreateDate(LocalDateTime.now());
        order.setReceiver(orderDTO.getReceiver());
        order.setPaymentType(orderDTO.getPaymentType());
        order.setShipPrice(orderDTO.getShipPrice());
        order.setTotalPrice(orderDTO.getTotalPrice());
        order.setReceiverPhone(orderDTO.getReceiverPhone());
        order.setAddressReceived(orderDTO.getAddressReceived());
        order.setEmail(orderDTO.getEmail());
        order.setType(0);
        if (orderDTO.getPaymentType() == 1) {
            order.setPaymentType(orderDTO.getPaymentType());
            order.setTotalPayment(orderDTO.getTotalPayment());
            order.setStatusPayment(AppConstant.DA_THANH_TOAN);
            order.setPaymentDate(LocalDateTime.now());
        } else {
            order.setPaymentType(orderDTO.getPaymentType());
            order.setTotalPayment(orderDTO.getTotalPayment());
            order.setStatusPayment(AppConstant.CHUA_THANH_TOAN);
        }
        order.setStatus(AppConstant.CHO_XAC_NHAN);
        if (StringUtils.isNotBlank(orderDTO.getCodeVoucher())) {
            Voucher voucher = voucherRepository.findByCode(orderDTO.getCodeVoucher());
            if (null != voucher) {
                voucher.setAmountUsed(voucher.getAmountUsed() + 1);
                if (voucher.getAmountUsed() == voucher.getQuantity()) {
                    voucher.setIdel(0);
                }
                order.setCodeVoucher(voucher.getCode());
                voucherRepository.save(voucher);
            }
        }
        if (StringUtils.isNotBlank(orderDTO.getCodeVoucherShip())) {
            VoucherFreeShip voucherFreeShip = voucherFreeShipRepository.findByCode(orderDTO.getCodeVoucherShip());
            if (null != voucherFreeShip) {
                voucherFreeShip.setAmountUsed(voucherFreeShip.getAmountUsed() + 1);
                if (voucherFreeShip.getAmountUsed() == voucherFreeShip.getQuantity()) {
                    voucherFreeShip.setIdel(0);
                }
                order.setCodeVoucherShip(voucherFreeShip.getCode());
                voucherFreeShipRepository.save(voucherFreeShip);
            }
        }
        order = orderRepository.save(order);
        dto = orderMapper.toDto(order);
        result.setData(dto);
        result.setStatus(HttpStatus.OK);
        result.setMessage("Success");
        return result;
    }

    @Override
    public ServiceResult<OrderDTO> traCuuDonHang(OrderDTO orderDTO) {
        ServiceResult<OrderDTO> orderDTOServiceResult = new ServiceResult<>();

        // Kiểm tra nếu mã đơn hàng trống
        if (StringUtils.isBlank(orderDTO.getCode())) {
            orderDTOServiceResult.setData(null);
            orderDTOServiceResult.setMessage("Vui lòng nhập mã đơn hàng");
            orderDTOServiceResult.setStatus(HttpStatus.BAD_REQUEST);
            return orderDTOServiceResult;
        }

        // Tìm đơn hàng theo mã
        Order order = orderRepository.findByCode(orderDTO.getCode());

        // Nếu đơn hàng tồn tại
        if (order != null) {
            OrderDTO dto = orderMapper.toDto(order);
            List<OrderDetailDTO> orderDetailDTOList = orderDetailMapper.toDto(orderDetailRepository.findByIdOrder(dto.getId()));

            for (OrderDetailDTO orderDetailDTO : orderDetailDTOList) {
                // Lấy chi tiết sản phẩm
                Optional<ProductDetail> optionalProductDetail = productDetailRepository.findById(orderDetailDTO.getIdProductDetail());

                if (optionalProductDetail.isPresent()) {
                    ProductDetailDTO productDetailDTO = productDetailMapper.toDto(optionalProductDetail.get());

                    Optional<Product> productOptional = productRepository.findById(productDetailDTO.getIdProduct());

                    if (productOptional.isPresent()) {
                        ProductDTO productDTO = productMapper.toDto(productOptional.get());
                        String imageURL = "http://localhost:8081/view/anh/" + productDTO.getId();

                        Optional<Color> optionalColor = colorRepository.findById(productDetailDTO.getIdColor());

                        if (optionalColor.isPresent()) {
                            ColorDTO colorDTO = colorMapper.toDto(optionalColor.get());
                            productDetailDTO.setColorDTO(colorDTO);
                        }

                        Optional<Size> optionalSize = sizeRepository.findById(productDetailDTO.getIdSize());

                        if (optionalSize.isPresent()) {
                            SizeDTO sizeDTO = sizeMapper.toDto(optionalSize.get());
                            productDetailDTO.setSizeDTO(sizeDTO);
                        }

                        productDTO.setImageURL(imageURL);
                        productDetailDTO.setProductDTO(productDTO);
                        orderDetailDTO.setProductDetailDTO(productDetailDTO);
                    }
                }
            }

            dto.setOrderDetailDTOList(orderDetailDTOList);
            orderDTOServiceResult.setData(dto);
            orderDTOServiceResult.setMessage("Success");
            orderDTOServiceResult.setStatus(HttpStatus.OK);
        } else {
            orderDTOServiceResult.setData(null);
            orderDTOServiceResult.setMessage("Mã đơn hàng không tồn tại!");
            orderDTOServiceResult.setStatus(HttpStatus.BAD_REQUEST);
        }
        return orderDTOServiceResult;
    }
}
