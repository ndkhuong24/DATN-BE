package com.example.backend.core.view.repository;

import com.example.backend.core.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface VoucherRepository extends JpaRepository<Voucher, Long> {

    @Query(value = "SELECT v.*\n" +
            "FROM Voucher v\n" +
            "WHERE v.end_date >= NOW() AND v.quantity > 0 and v.idel = 1\n" +
            "and (v.apply = 1 or v.apply = 2) and (v.option_customer = 0 and v.id_customer is null) and (UPPER(v.code) like concat('%', UPPER(:codeSearch), '%'))  \n" +
            "ORDER BY v.end_date ASC" , nativeQuery = true)
    List<Voucher> getAllVoucher(@Param(value = "codeSearch") String codeSearch);

    @Query(value = "SELECT v.*\n" +
            "FROM Voucher v\n" +
            "WHERE v.end_date >= NOW() AND v.quantity > 0 and v.idel = 1\n" +
            "and (v.apply = 1 or v.apply = 2) and (FIND_IN_SET(:idCustomer, v.id_customer) or v.id_customer IS NULL) and (UPPER(v.code) like concat('%', UPPER(:codeSearch), '%'))  \n" +
            "ORDER BY v.end_date ASC" , nativeQuery = true)
    List<Voucher> getAllVoucherByCustomer(@Param(value = "codeSearch") String codeSearch,
                                          @Param(value = "idCustomer") Long idCustomer);

    Voucher findByCode(String code);
}
