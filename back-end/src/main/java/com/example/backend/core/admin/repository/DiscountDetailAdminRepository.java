package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.model.DiscountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface DiscountDetailAdminRepository extends JpaRepository<DiscountDetail,Long> {

    @Query(value = "select d from DiscountDetail d where d.idDiscount = ?1")
    List<DiscountDetail> findAllByDiscount(Long id);
    DiscountDetail findByIdDiscountAndIdProduct(Long idDiscount, Long idProduct);
}
