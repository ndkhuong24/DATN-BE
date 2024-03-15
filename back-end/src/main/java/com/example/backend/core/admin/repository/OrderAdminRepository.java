package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Order;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderAdminRepository extends JpaRepository<Order, Long> {

    List<Order> getAllByOrderByCreateDateDesc();

    List<Order> findByStatusOrderByCreateDateDesc(Integer status);

    List<Order> findByCodeVoucher(String voucherCode);
    List<Order> findByCodeVoucherShip(String voucherCode);
}
