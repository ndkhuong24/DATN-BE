package com.example.backend.core.view.repository;

import com.example.backend.core.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByIdCustomerAndCodeVoucher(Long idCustomer, String codeVoucher);

    List<Order> findByIdCustomerOrderByCreateDateDesc(Long idCustomer);

    List<Order> findByIdCustomerAndStatusOrderByCreateDateDesc(Long idCustomer, Integer status);

    Order findByCode(String code);
}
