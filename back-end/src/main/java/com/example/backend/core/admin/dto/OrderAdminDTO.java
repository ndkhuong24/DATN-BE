package com.example.backend.core.admin.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderAdminDTO {
    private Long id;
    private String code;
    private Long idCustomer;
    private Long idStaff;
    private String codeVoucher;
    private String codeVoucherShip;
    private LocalDateTime createDate;
    private LocalDateTime paymentDate;
    private LocalDateTime deliveryDate;
    private LocalDateTime receivedDate;
    private String addressReceived;
    private String shipperPhone;
    private String receiverPhone;
    private String receiver;
    private BigDecimal shipPrice;
    private BigDecimal totalPrice;
    private BigDecimal totalPayment;
    private Integer paymentType;
    private String description;
    private Integer status;
    private Integer missedOrder;
    private Integer statusPayment;
    private Integer type;
    private CustomerAdminDTO customerAdminDTO;
    private StaffAdminDTO staffAdminDTO;
    private String dateFrom;
    private String dateTo;
    private String note;
    private List<OrderHistoryAdminDTO> orderHistoryAdminDTOList;
    private String email;
}
