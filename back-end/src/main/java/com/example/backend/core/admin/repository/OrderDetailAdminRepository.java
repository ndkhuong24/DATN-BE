package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Order;
import com.example.backend.core.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderDetailAdminRepository extends JpaRepository<OrderDetail, Long> {

    List<OrderDetail> findByIdOrder(Long idOrder);
    List<OrderDetail> findByCodeDiscount(String code);
}
