package com.example.backend.core.salesCounter.repository;

import com.example.backend.core.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountSCRepository extends JpaRepository<Discount, Long> {
    @Query(value = "SELECT d.*\n" +
            "FROM discount d\n" +
            "WHERE (d.start_date < NOW() AND d.end_date > NOW()) and d.idel = 1  ;\n", nativeQuery = true)
    List<Discount> getDiscountConApDung();

    Discount findByCode(String code);
}
