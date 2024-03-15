package com.example.backend.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "voucher_free_ship")
public class VoucherFreeShip implements Serializable {
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
    @Column(name = "reduced_value")
    private BigDecimal reducedValue;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "amount_used")
    private Integer amountUsed;
    @Column(name = "option_customer")
    private Integer optionCustomer;
    @Column(name = "limit_customer")
    private Integer limitCustomer;
    @Column(name = "dele")
    private Integer delete;
}
