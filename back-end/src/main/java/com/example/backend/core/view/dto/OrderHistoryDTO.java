package com.example.backend.core.view.dto;

import com.example.backend.core.admin.dto.StaffAdminDTO;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class OrderHistoryDTO implements Serializable {
    private Long id;
    private Long idOrder;
    private Long idStaff;
    private Long idCustomer;
    private Integer status;
    private Instant createDate;
    private String note;
    private StaffAdminDTO staffAdminDTO;
    private CustomerDTO customerDTO;

    public String getStatus(){
        if(status == 1) {
            return "Xác nhận đơn hàng";
        }else if(status == 2){
            return "Đơn hàng đã được giao đi";
        }else if(status == 3){
            return "Đơn hàng bị bỏ lỡ lần 1";
        }else if(status == 4){
            return "Đơn hàng bị bỏ lỡ lần 2";
        }else if(status == 5){
            return "Đơn hàng bị bỏ lỡ lần 3 \n" +
                    "=> Đơn hàng đã tự động hủy";
        }else if(status == 6){
            return "Đơn hàng đã bị hủy";
        }else{
            return "Hoàn thành đơn hàng";
        }
    }

}
