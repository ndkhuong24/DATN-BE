package com.example.backend.core.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "voucher")
public class Voucher {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "code")
    private String code;
    @Column(name = "name")
    private String name;
    @Column(name = "id_customer")
    private String idCustomer;
    @Column(name = "create_date")
    private Date createDate;
    @Column(name = "start_date")
    private Date startDate;
    @Column(name = "end_date")
    private Date endDate;
    @Column(name = "description")
    private String description;
    @Column(name = "conditions")
    private BigDecimal conditionApply;
    @Column(name = "status")
    private int status;
    @Column(name = "idel")
    private int idel;
    @Column(name = "create_name")
    private String createName;
    @Column(name = "voucher_type")
    private int voucherType;
    @Column(name = "reduced_value")
    private BigDecimal reducedValue;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "amount_used")
    private Integer amountUsed;
    @Column(name = "apply")
    private Integer apply;
    @Column(name = "option_customer")
    private Integer optionCustomer;
    @Column(name = "max_reduced")
    private BigDecimal maxReduced;
    @Column(name = "limit_customer")
    private Integer limitCustomer;
    @Column(name = "allow")
    private Integer allow;
    @Column(name = "dele")
    private Integer delete;

}
