package com.example.backend.core.admin.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderHistoryAdminDTO {
    private Long id;
    private Long idOrder;
    private Long idStaff;
    private Long idCustomer;
    private Integer status;
    private LocalDateTime createDate;
    private String note;
    private StaffAdminDTO staffDTO;
    private CustomerAdminDTO customerAdminDTO;

    public String getStatus() {
        if (status == 1) {
            return "Xác nhận đơn hàng";
        } else if (status == 2) {
            return "Đơn hàng đã được giao đi";
        } else if (status == 3) {
            return "Đơn hàng bị bỏ lỡ lần 1";
        } else if (status == 4) {
            return "Đơn hàng bị bỏ lỡ lần 2";
        } else if (status == 5) {
            return "Đơn hàng bị bỏ lỡ lần 3 \n" +
                    "=> Đơn hàng đã tự động hủy";
        } else if (status == 6) {
            return "Đơn hàng đã bị hủy";
        } else {
            return "Xác nhận hoàn thành đơn hàng";
        }
    }
}
