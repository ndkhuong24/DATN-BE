package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Order;
import com.example.backend.core.view.dto.*;
import com.example.backend.core.view.mapper.*;
import com.example.backend.core.view.repository.*;
import com.example.backend.core.view.service.EmailService;
import com.example.backend.core.view.service.OrderDetailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.List;
import java.util.Map;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    @Autowired
    private OrderDetailService orderDetailService;

    @Autowired
    private CustomerRepository customerRepository;

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

    @Autowired
    private OrderRepository orderRepository;

    public EmailServiceImpl(JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    @Override
    public ServiceResult<String> sendMessageUsingThymeleafTemplate(OrderDTO orderDTO) throws MessagingException {
        Context thymeleafContext = new Context();

        ServiceResult<String> result = new ServiceResult<>();

        if (orderDTO.getId() == null || StringUtils.isBlank(orderDTO.getEmail())) {
            result.setMessage("Error");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData("Lỗi send Email");
            return result;
        }

        String emailTo = orderDTO.getEmail();
        String subject = " Thông tin đơn hàng ";
        Map<String, Object> map = orderDetailService.getAllByOrder(orderDTO.getId());

        List<OrderDetailDTO> list = (List<OrderDetailDTO>) map.get("orderDetail");
        thymeleafContext.setVariable("order", orderDTO);
        thymeleafContext.setVariable("orderDetail", list);
        String htmlBody = templateEngine.process("sendEmailOrder", thymeleafContext);
        sendHtmlEmail(emailTo, subject, htmlBody);
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        result.setData("Send Mail thành công");
        return result;
    }

    @Override
    public ServiceResult<String> sendEmailFromCustomer(OrderDTO orderDTO) throws MessagingException {
        Context thymeleafContext = new Context();

        ServiceResult<String> result = new ServiceResult<>();

        if (orderDTO.getId() == null) {
            result.setMessage("Error");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData("Lỗi send Email");
            return result;
        }

        Order order = orderRepository.findById(orderDTO.getId()).orElse(null);

        if (order.getStatus() == AppConstant.DANG_GIAO_HANG) {
            String emailTo = order.getEmail();
            String subject = "Thông tin đơn hàng";

            // Đoạn thông báo yêu cầu người dùng xác nhận khi nhận hàng
            String confirmationLink = "http://localhost:4000/tra-cuu-don-hang?code=" + order.getCode();
            String messageBody = "Đơn hàng của bạn đã  được giao cho shipper. Khi bạn nhận được hàng vui lòng truy cập vào link sau và xác nhận là đã nhận được hàng: "
                    + confirmationLink + ". Cảm ơn bạn đã mua hàng ở cửa hàng chúng tôi. Trân trọng !";

            sendHtmlEmail(emailTo, subject, messageBody);
        } else {
            String emailTo = order.getEmail();
            String subject = " Thông tin đơn hàng";
            Map<String, Object> map = orderDetailService.getAllByOrder(order.getId());

            List<OrderDetailDTO> list = (List<OrderDetailDTO>) map.get("orderDetail");
            thymeleafContext.setVariable("order", orderDTO);
            thymeleafContext.setVariable("orderDetail", list);
            String htmlBody = templateEngine.process("sendEmailOrder", thymeleafContext);
            sendHtmlEmail(emailTo, subject, htmlBody);
        }

        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        result.setData("Send Mail thành công");
        result.setSuccess(true);
        return result;
    }

    @Override
    public ServiceResult<?> sendMailOrderNotLogin(OrderDTO orderDTO) throws MessagingException {
        Context thymeleafContext = new Context();

        ServiceResult<String> result = new ServiceResult<>();

        if (orderDTO.getId() == null || StringUtils.isBlank(orderDTO.getEmail())) {
            result.setMessage("Error");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData("Lỗi send Email");
            return result;
        }

        String emailTo = orderDTO.getEmail();
        String subject = " Thông tin đơn hàng";
        List<OrderDetailDTO> list = getAllByOrder(orderDTO.getId());
        thymeleafContext.setVariable("order", orderDTO);
        thymeleafContext.setVariable("orderDetail", list);
        String htmlBody = templateEngine.process("sendMailOrderNotLogin", thymeleafContext);
        sendHtmlEmail(emailTo, subject, htmlBody);
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        result.setData("Send Mail thành công");
        return result;
    }

    void sendHtmlEmail(String emailTo, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        helper.setFrom("kn134646@gmail.com");
        helper.setTo(emailTo);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    private List<OrderDetailDTO> getAllByOrder(Long id) {
        if (id == null) {
            return null;
        }
        List<OrderDetailDTO> orderDetailDTOList = orderDetailMapper.toDto(orderDetailRepository.findByIdOrder(id));

        for (int i = 0; i < orderDetailDTOList.size(); i++) {
            ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetailRepository.findById(orderDetailDTOList.get(i).getIdProductDetail()).get());

            ProductDTO productDTO = productMapper.toDto(productRepository.findById(productDetailDTO.getIdProduct()).get());
            String imageURL = "http://localhost:8081/view/anh/" + productDetailDTO.getIdProduct();
            productDTO.setImageURL(imageURL);
            productDetailDTO.setProductDTO(productDTO);

            ColorDTO colorDTO = colorMapper.toDto(colorRepository.findById(productDetailDTO.getIdColor()).get());
            productDetailDTO.setColorDTO(colorDTO);

            SizeDTO sizeDTO = sizeMapper.toDto(sizeRepository.findById(productDetailDTO.getIdSize()).get());
            productDetailDTO.setSizeDTO(sizeDTO);

            orderDetailDTOList.get(i).setProductDetailDTO(productDetailDTO);
        }
        return orderDetailDTOList;
    }

}
