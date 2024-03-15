package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderDTO {
    private Long id;
    private String code;
    private Long idCustomer;
    private Long idStaff;
    private String codeVoucher;
    private String codeVoucherShip;
    private Instant createDate;
    private Instant paymentDate;
    private Date deliveryDate;
    private Date receivedDate;
    private String addressReceived;
    private String shipperPhone;
    private String receiverPhone;
    private String receiver;
    private BigDecimal shipPrice;
    private BigDecimal totalPrice;
    private BigDecimal totalPayment;
    private Integer type;
    private Integer paymentType;
    private String description;
    private Integer status;
    private Integer missedOrder;
    private CustomerDTO customerDTO;
    private String email;
    private Integer statusPayment;
    private String dateFrom;
    private String dateTo;
    private String note;
    private List<OrderDetailDTO> orderDetailDTOList;

    public String getStatusSendEmail(){
        if(status == 0){
            return "Chờ xác nhận";
        }else if(status == 1){
            return "Chờ xử lý";
        }else if(status == 2){
            return "Đang giao hàng";
        }else if(status == 3){
            return "Hoàn thành";
        }else{
            return "Đã hủy";
        }
    }
}
