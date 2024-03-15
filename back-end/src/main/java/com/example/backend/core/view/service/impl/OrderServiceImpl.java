package com.example.backend.core.view.service.impl;

import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Address;
import com.example.backend.core.model.Customer;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.OrderDetail;
import com.example.backend.core.model.OrderHistory;
import com.example.backend.core.model.ProductDetail;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.model.VoucherFreeShip;
import com.example.backend.core.security.dto.UsersDTO;
import com.example.backend.core.view.dto.AddressDTO;
import com.example.backend.core.view.dto.ColorDTO;
import com.example.backend.core.view.dto.CustomerDTO;
import com.example.backend.core.view.dto.ImagesDTO;
import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.dto.OrderDetailDTO;
import com.example.backend.core.view.dto.ProductDTO;
import com.example.backend.core.view.dto.ProductDetailDTO;
import com.example.backend.core.view.dto.SizeDTO;
import com.example.backend.core.view.mapper.ColorMapper;
import com.example.backend.core.view.mapper.CustomerMapper;
import com.example.backend.core.view.mapper.ImagesMapper;
import com.example.backend.core.view.mapper.OrderDetailMapper;
import com.example.backend.core.view.mapper.OrderMapper;
import com.example.backend.core.view.mapper.ProductDetailMapper;
import com.example.backend.core.view.mapper.ProductMapper;
import com.example.backend.core.view.mapper.SizeMapper;
import com.example.backend.core.view.repository.ColorRepository;
import com.example.backend.core.view.repository.CustomerRepository;
import com.example.backend.core.view.repository.ImagesRepository;
import com.example.backend.core.view.repository.OrderCustomRepository;
import com.example.backend.core.view.repository.OrderDetailRepository;
import com.example.backend.core.view.repository.OrderHistoryRepository;
import com.example.backend.core.view.repository.OrderRepository;
import com.example.backend.core.view.repository.ProductDetailRepository;
import com.example.backend.core.view.repository.ProductRepository;
import com.example.backend.core.view.repository.SizeRepository;
import com.example.backend.core.view.repository.VoucherFreeShipRepository;
import com.example.backend.core.view.repository.VoucherRepository;
import com.example.backend.core.view.service.OrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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
            order.setCreateDate(Instant.now());
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
                order.setPaymentDate(Instant.now());
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
            orderHistory.setCreateDate(Instant.now());
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
        order.setCreateDate(Instant.now());
        order.setReceiver(orderDTO.getReceiver());
        order.setPaymentType(orderDTO.getPaymentType());
        order.setShipPrice(orderDTO.getShipPrice());
        order.setTotalPrice(orderDTO.getTotalPrice());
//        order.setTotalPayment(orderDTO.getTotalPayment());
        order.setReceiverPhone(orderDTO.getReceiverPhone());
        order.setAddressReceived(orderDTO.getAddressReceived());
        order.setEmail(orderDTO.getEmail());
        order.setType(0);
        if (orderDTO.getPaymentType() == 1) {
            order.setPaymentType(orderDTO.getPaymentType());
            order.setTotalPayment(orderDTO.getTotalPayment());
            order.setStatusPayment(AppConstant.DA_THANH_TOAN);
            order.setPaymentDate(Instant.now());
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
        ServiceResult<OrderDTO> result = new ServiceResult<>();
        if(StringUtils.isBlank(orderDTO.getCode())){
            result.setData(null);
            result.setMessage("Vui lòng nhập mã đơn hàng");
            result.setStatus(HttpStatus.BAD_REQUEST);
            return result;
        }
        OrderDTO dto = new OrderDTO();
        Order order = orderRepository.findByCode(orderDTO.getCode());
        if(null != order){
            dto = orderMapper.toDto(order);
            List<OrderDetailDTO> orderDetailDTOList = orderDetailMapper.toDto(orderDetailRepository.findByIdOrder(dto.getId()));
            for (int i = 0; i < orderDetailDTOList.size() ; i++) {
                ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetailRepository.findById(orderDetailDTOList.get(i).getIdProductDetail()).get());
                ProductDTO productDTO = productMapper.toDto(productRepository.findById(productDetailDTO.getIdProduct()).get());
                List<ImagesDTO> imagesDTOList = imagesMapper.toDto(imagesRepository.findByIdProduct(productDTO.getId()));
                ColorDTO colorDTO = colorMapper.toDto(colorRepository.findById(productDetailDTO.getIdColor()).get());
                productDetailDTO.setColorDTO(colorDTO);
                SizeDTO sizeDTO = sizeMapper.toDto(sizeRepository.findById(productDetailDTO.getIdSize()).get());
                productDetailDTO.setSizeDTO(sizeDTO);
                productDTO.setImagesDTOList(imagesDTOList);
                productDetailDTO.setProductDTO(productDTO);
                orderDetailDTOList.get(i).setProductDetailDTO(productDetailDTO);
                orderDetailDTOList.set(i,orderDetailDTOList.get(i));
            }
            dto.setOrderDetailDTOList(orderDetailDTOList);
            result.setData(dto);
            result.setMessage("Success");
            result.setStatus(HttpStatus.OK);
        }else {
            result.setData(null);
            result.setMessage("Mã đơn hàng không tồn tại!");
            result.setStatus(HttpStatus.BAD_REQUEST);
            return result;
        }
        return result;
    }
}
