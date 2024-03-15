package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "`order`")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "id_customer")
    private Long idCustomer;
    @Column(name = "id_staff")
    private Long idStaff;
    @Column(name = "code_voucher")
    private String codeVoucher;
    @Column(name = "code_voucher_ship")
    private String codeVoucherShip;
    @Column(name = "create_date")
    private Instant createDate;
    @Column(name = "payment_date")
    private Instant paymentDate;
    @Column(name = "delivery_date")
    private Instant deliveryDate;
    @Column(name = "received_date")
    private Instant receivedDate;
    @Column(name = "address_received")
    private String addressReceived;
    @Column(name = "shipper_phone")
    private String shipperPhone;
    @Column(name = "receiver_phone")
    private String receiverPhone;
    @Column(name = "receiver")
    private String receiver;
    @Column(name = "ship_price")
    private BigDecimal shipPrice;
    @Column(name = "total_price")
    private BigDecimal totalPrice;
    @Column(name = "total_payment")
    private BigDecimal totalPayment;
    @Column(name = "type")
    private Integer type;
    @Column(name = "payment_type")
    private Integer paymentType;
    @Column(name = "description")
    private String description;
    @Column(name = "status")
    private Integer status;
    @Column(name = "missed_order")
    private Integer missedOrder;
    @Column(name = "status_payment")
    private Integer statusPayment;
    @Column(name = "email")
    private String email;
}
