package com.example.backend.core.salesCounter.repository;

import com.example.backend.core.model.VoucherFreeShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherShipSCRepository extends JpaRepository<VoucherFreeShip, Long> {
    @Query(value = "SELECT v.*\n" +
            "FROM voucher_free_ship v\n" +
            "WHERE v.end_date >= NOW() AND v.quantity > 0 and v.idel = 1\n" +
            " and (v.option_customer = 0 and v.id_customer is null) and (UPPER(v.code) like concat('%', UPPER(:codeSearch), '%'))  \n" +
            "ORDER BY v.end_date ASC" , nativeQuery = true)
    List<VoucherFreeShip> getAllVoucherShipSC(@Param(value = "codeSearch") String codeSearch);

    @Query(value = "SELECT v.*\n" +
            "FROM voucher_free_ship v\n" +
            "WHERE v.end_date >= NOW() AND v.quantity > 0 and v.idel = 1\n" +
            " and (FIND_IN_SET(:idCustomer, v.id_customer) or v.id_customer IS NULL) and (UPPER(v.code) like concat('%', UPPER(:codeSearch), '%'))  \n" +
            "ORDER BY v.end_date ASC" , nativeQuery = true)
    List<VoucherFreeShip> getAllVoucherShipByCustomerSC(@Param(value = "codeSearch") String codeSearch,
                                                      @Param(value = "idCustomer") Long idCustomer);

    VoucherFreeShip findByCode(String code);
}
