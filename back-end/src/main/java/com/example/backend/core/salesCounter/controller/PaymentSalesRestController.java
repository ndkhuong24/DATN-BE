package com.example.backend.core.salesCounter.controller;

import com.example.backend.core.salesCounter.service.VNPayService;
import com.example.backend.core.view.dto.PaymentResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequestMapping("api/sales-couter")
public class PaymentSalesRestController {
    @Autowired
    private VNPayService vnPayService;


    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(@RequestParam(name = "amount") String amount, HttpServletRequest request) throws UnsupportedEncodingException {
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createPayment(amount, baseUrl);

        PaymentResponse paymentResponse = new PaymentResponse();
        paymentResponse.setStatus(HttpStatus.OK);
        paymentResponse.setMessage("Thành công!");
        paymentResponse.setUrl(vnpayUrl);

        return ResponseEntity.ok(paymentResponse);
    }

    @GetMapping("/vnpay-payment")
    public ResponseEntity<Map<String, Object>> getMapping(HttpServletRequest request) {
        int paymentStatus = vnPayService.orderReturn(request);

        String orderInfo = request.getParameter("vnp_OrderInfo");
        String paymentTime = request.getParameter("vnp_PayDate");
        String transactionId = request.getParameter("vnp_TransactionNo");
        String totalPrice = request.getParameter("vnp_Amount");

        Map<String, Object> response = new HashMap<>();
        response.put("orderId", orderInfo);
        response.put("totalPrice", totalPrice);
        response.put("paymentTime", paymentTime);
        response.put("transactionId", transactionId);
        response.put("paymentStatus", paymentStatus == 1 ? "success" : "fail");

        return ResponseEntity.ok(response);
    }
}
