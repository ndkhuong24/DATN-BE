package com.example.backend.core.salesCounter.dto;

import com.example.backend.core.view.dto.CustomerDTO;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderSalesDTO {
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
    private Integer statusPayment;

    private CustomerSCDTO customerDTO;
    private StaffSCDTO staffSCDTO;
    private String email;

    public OrderSalesDTO(CustomerSCDTO customerDTO) {
        this.customerDTO = customerDTO;
    }
}
