package com.example.backend.core.view.dto;

import jakarta.persistence.Column;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressDTO {
    private Long id;
    private Long idCustomer;
    private Instant createDate;
    private Integer provinceId;
    private String province;
    private Integer districtId;
    private String district;
    private String wardCode;
    private String wards;
    private String specificAddress;
    private Integer config;

    private CustomerDTO customerDTO;
}
