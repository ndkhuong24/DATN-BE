package com.example.backend.core.salesCounter.repository;

import com.example.backend.core.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface VoucherSCRepository extends JpaRepository<Voucher, Long> {
    //    @Query(value = "SELECT v.*\n" +
//            "FROM Voucher v\n" +
//            "WHERE v.end_date >= NOW() AND v.quantity > 0 and v.idel = 1\n" +
//            "and (v.apply = 0 or v.apply = 2) and (v.option_customer = 0 and v.id_customer is null) and (UPPER(v.code) like concat('%', UPPER(:codeSearch), '%'))  \n" +
//            "ORDER BY v.end_date ASC", nativeQuery = true)
//    List<Voucher> getAllVoucherSC(@Param(value = "codeSearch") String codeSearch);

//    @Query(value = "SELECT v.*\n" +
//            "FROM Voucher v\n" +
//            "WHERE v.end_date >= NOW() AND v.quantity > 0 and v.idel = 1\n" +
//            "and (v.apply = 0 or v.apply = 2) and (FIND_IN_SET(:idCustomer, v.id_customer) or v.id_customer IS NULL) and (UPPER(v.code) like concat('%', UPPER(:codeSearch), '%'))  \n" +
//            "ORDER BY v.end_date ASC", nativeQuery = true)
//    List<Voucher> getAllVoucherByCustomerSC(@Param(value = "codeSearch") String codeSearch,
//                                            @Param(value = "idCustomer") Long idCustomer);

    @Query(value = "SELECT " +
            "    v.id, " +
            "    v.code, " +
            "    v.name, " +
            "    v.start_date, " +
            "    v.end_date, " +
            "    v.conditions, " +
            "    v.voucher_type, " +
            "    v.reduced_value, " +
            "    v.description, " +
            "    v.idel, " +
            "    v.quantity, " +
            "    v.max_reduced, " +
            "    v.allow, " +
            "    v.create_name, " +
            "    COUNT(CASE WHEN o.status = 3 THEN o.id ELSE NULL END) AS use_voucher, " +
//            "    COUNT(o.id) AS use_voucher, " +
            "    v.create_date " +
            "FROM " +
            "    voucher v " +
            "LEFT JOIN " +
            "    `order` o ON o.code_voucher = v.code " +
            "WHERE " +
            "    v.end_date >= NOW() AND " +
            "    v.quantity > 0 AND " +
            "    v.idel = 1 AND " +
            "    (v.apply = 0 OR v.apply = 2) AND " +
            "    (v.option_customer = 0 OR v.id_customer IS NULL) AND " +
            "    UPPER(v.code) LIKE CONCAT('%', UPPER(:codeSearch), '%') " +
            "GROUP BY " +
            "    v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
            "    v.voucher_type, v.reduced_value, v.description, v.idel, " +
            "    v.quantity, v.max_reduced, v.allow, v.create_name, v.create_date " +
            "ORDER BY " +
            "    v.end_date ASC", nativeQuery = true)
    List<Object[]> getAllVouchersWithUseCount(@Param("codeSearch") String codeSearch);


    @Query(value = "SELECT " +
            "    v.id, " +
            "    v.code, " +
            "    v.name, " +
            "    v.start_date, " +
            "    v.end_date, " +
            "    v.conditions, " +
            "    v.voucher_type, " +
            "    v.reduced_value, " +
            "    v.description, " +
            "    v.idel, " +
            "    v.quantity, " +
            "    v.max_reduced, " +
            "    v.allow, " +
            "    v.create_name, " +
            "    COUNT(CASE WHEN o.status = 3 THEN o.id ELSE NULL END) AS use_voucher, " +
//            "    COUNT(o.id) AS use_voucher, " +
            "    v.create_date " +
            "FROM " +
            "    voucher v " +
            "LEFT JOIN " +
            "    `order` o ON o.code_voucher = v.code " +
            "WHERE " +
            "    v.end_date >= NOW() AND " +
            "    v.quantity > 0 AND " +
            "    v.idel = 1 AND " +
            "    (v.apply = 0 OR v.apply = 2) AND " +
            "    (FIND_IN_SET(:idCustomer, v.id_customer) OR v.id_customer IS NULL) AND " +
            "    UPPER(v.code) LIKE CONCAT('%', UPPER(:codeSearch), '%') " +
            "GROUP BY " +
            "    v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
            "    v.voucher_type, v.reduced_value, v.description, v.idel, " +
            "    v.quantity, v.max_reduced, v.allow, v.create_name, v.create_date " +
            "ORDER BY " +
            "    v.end_date ASC", nativeQuery = true)
    List<Object[]> getAllVouchersByCustomerWithUseCount(@Param("codeSearch") String codeSearch, @Param("idCustomer") Long idCustomer);

    Voucher findByCode(String code);
}
