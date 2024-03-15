package com.example.backend.core.view.repository;

import com.example.backend.core.model.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    @Query(value = "select oh from OrderHistory oh where oh.idOrder = :idOrder order by oh.createDate")
    List<OrderHistory> getAllOrderHistoryByOrder(@Param("idOrder") Long idOrder);
}
