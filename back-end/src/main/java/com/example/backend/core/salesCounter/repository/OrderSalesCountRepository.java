package com.example.backend.core.salesCounter.repository;

import com.example.backend.core.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderSalesCountRepository extends JpaRepository<Order, Long> {
}
