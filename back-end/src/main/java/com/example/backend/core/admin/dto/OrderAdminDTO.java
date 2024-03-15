package com.example.backend.core.admin.dto;



import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;
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
