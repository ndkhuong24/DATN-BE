package com.example.backend.core.view.dto;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AddressDTO {
    private Long id;
    private Long idCustomer;
    private LocalDate createDate;
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
