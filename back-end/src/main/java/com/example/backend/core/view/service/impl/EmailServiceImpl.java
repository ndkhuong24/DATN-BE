package com.example.backend.core.view.service.impl;

import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.model.Order;
import com.example.backend.core.view.dto.ColorDTO;
import com.example.backend.core.view.dto.ImagesDTO;
import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.dto.OrderDetailDTO;
import com.example.backend.core.view.dto.ProductDTO;
import com.example.backend.core.view.dto.ProductDetailDTO;
import com.example.backend.core.view.dto.SizeDTO;
import com.example.backend.core.view.mapper.ColorMapper;
import com.example.backend.core.view.mapper.ImagesMapper;
import com.example.backend.core.view.mapper.OrderDetailMapper;
import com.example.backend.core.view.mapper.ProductDetailMapper;
import com.example.backend.core.view.mapper.ProductMapper;
import com.example.backend.core.view.mapper.SizeMapper;
import com.example.backend.core.view.repository.ColorRepository;
import com.example.backend.core.view.repository.CustomerRepository;
import com.example.backend.core.view.repository.ImagesRepository;
import com.example.backend.core.view.repository.OrderDetailRepository;
import com.example.backend.core.view.repository.OrderRepository;
import com.example.backend.core.view.repository.ProductDetailRepository;
import com.example.backend.core.view.repository.ProductRepository;
import com.example.backend.core.view.repository.SizeRepository;
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
import org.springframework.scheduling.annotation.Async;
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


    public void sendHtmlEmail(String to, String subject, String htmlBody) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, false, "UTF-8");
        helper.setFrom("hunglqph20358@fpt.edu.vn");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlBody, true);
        javaMailSender.send(message);
    }

    @Override
    public ServiceResult<String> sendMessageUsingThymeleafTemplate(OrderDTO orderDTO) throws MessagingException {
        Context thymeleafContext = new Context();
        ServiceResult<String> result = new ServiceResult<>();
//        Customer customer = customerRepository.findById(orderDTO.getIdCustomer()).get();
        if(orderDTO.getId() == null || StringUtils.isBlank(orderDTO.getEmail())){
            result.setMessage("Error");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData("Lỗi send Email");
            return result;
        }
        String emailTo = orderDTO.getEmail();
        String subject =  " Thông tin đơn hàng ";
        Map<String, Object> map = orderDetailService.getAllByOrder(orderDTO.getId());

        List<OrderDetailDTO> list = (List<OrderDetailDTO>) map.get("orderDetail");
        thymeleafContext.setVariable("order", orderDTO);
        thymeleafContext.setVariable("orderDetail", list);
        String htmlBody = templateEngine.process("sendEmailOrder", thymeleafContext);
        sendHtmlEmail(emailTo, subject, htmlBody);
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        result.setData("Send Mail Thành công!");
        return result;
    }

    @Override
    public ServiceResult<String> sendEmailFromCustomer(OrderDTO orderDTO) throws MessagingException {
        Context thymeleafContext = new Context();
        ServiceResult<String> result = new ServiceResult<>();
//        Customer customer = customerRepository.findById(orderDTO.getIdCustomer()).get();
        if(orderDTO.getId() == null){
            result.setMessage("Error");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData("Lỗi send Email");
            return result;
        }
        Order order = orderRepository.findById(orderDTO.getId()).orElse(null);
        String emailTo = order.getEmail();
        String subject =  " Thông tin đơn hàng";
        Map<String, Object> map = orderDetailService.getAllByOrder(order.getId());

        List<OrderDetailDTO> list = (List<OrderDetailDTO>) map.get("orderDetail");
        thymeleafContext.setVariable("order", orderDTO);
        thymeleafContext.setVariable("orderDetail", list);
        String htmlBody = templateEngine.process("sendEmailOrder", thymeleafContext);
        sendHtmlEmail(emailTo, subject, htmlBody);
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        result.setData("Send Mail Thành công!");
        return result;
    }

    @Override
    public ServiceResult<String> sendMailOrderNotLogin(OrderDTO orderDTO) throws MessagingException {
        Context thymeleafContext = new Context();
        ServiceResult<String> result = new ServiceResult<>();
        if(orderDTO.getId() == null || StringUtils.isBlank(orderDTO.getEmail())){
            result.setMessage("Error");
            result.setStatus(HttpStatus.BAD_REQUEST);
            result.setData("Lỗi send Email");
            return result;
        }
        String emailTo = orderDTO.getEmail();
        String subject =  " Thông tin đơn hàng";
        List<OrderDetailDTO> list = getAllByOrder(orderDTO.getId());
        thymeleafContext.setVariable("order", orderDTO);
        thymeleafContext.setVariable("orderDetail", list);
        String htmlBody = templateEngine.process("sendMailOrderNotLogin", thymeleafContext);
        sendHtmlEmail(emailTo, subject, htmlBody);
        result.setMessage("Success");
        result.setStatus(HttpStatus.OK);
        result.setData("Send Mail Thành công!");
        return result;
    }

    private List<OrderDetailDTO> getAllByOrder(Long idOrder) {
        if(idOrder == null){
            return null;
        }
        List<OrderDetailDTO> lst = orderDetailMapper.toDto(orderDetailRepository.findByIdOrder(idOrder));
        for (int i = 0; i < lst.size() ; i++) {
            ProductDetailDTO productDetailDTO = productDetailMapper.toDto(productDetailRepository.findById(lst.get(i).getIdProductDetail()).get());
            ProductDTO productDTO = productMapper.toDto(productRepository.findById(productDetailDTO.getIdProduct()).get());
            List<ImagesDTO> imagesDTOList = imagesMapper.toDto(imagesRepository.findByIdProduct(productDTO.getId()));
            ColorDTO colorDTO = colorMapper.toDto(colorRepository.findById(productDetailDTO.getIdColor()).get());
            productDetailDTO.setColorDTO(colorDTO);
            SizeDTO sizeDTO = sizeMapper.toDto(sizeRepository.findById(productDetailDTO.getIdSize()).get());
            productDetailDTO.setSizeDTO(sizeDTO);
            productDTO.setImagesDTOList(imagesDTOList);
            productDetailDTO.setProductDTO(productDTO);
            lst.get(i).setProductDetailDTO(productDetailDTO);
            lst.set(i,lst.get(i));
        }
        return lst;
    }
}
