package com.example.backend.core.salesCounter.repository;


import com.example.backend.core.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSalesCounterDetailRepository extends JpaRepository<OrderDetail, Long> {
}
