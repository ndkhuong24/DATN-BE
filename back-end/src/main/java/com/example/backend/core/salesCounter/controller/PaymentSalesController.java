package com.example.backend.core.salesCounter.controller;

import com.example.backend.core.salesCounter.service.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentSalesController {
    private final VNPayService vnPayService;

    @Autowired
    public PaymentSalesController(VNPayService vnPayService) {
        this.vnPayService = vnPayService;
    }

//    @GetMapping("/api/sales-couter/vnpay-payment")
//    public String getMapping(HttpServletRequest request, Model model) {
//        int paymentStatus = vnPayService.orderReturn(request);
//
//        String orderInfo = request.getParameter("vnp_OrderInfo");
//        String paymentTime = request.getParameter("vnp_PayDate");
//        String transactionId = request.getParameter("vnp_TransactionNo");
//        String totalPrice = request.getParameter("vnp_Amount");
//
//        model.addAttribute("orderId", orderInfo);
//        model.addAttribute("totalPrice", totalPrice);
//        model.addAttribute("paymentTime", paymentTime);
//        model.addAttribute("transactionId", transactionId);
//
//        return paymentStatus == 1 ? "ordersuccesssales" : "orderfailsales";
//    }

}
