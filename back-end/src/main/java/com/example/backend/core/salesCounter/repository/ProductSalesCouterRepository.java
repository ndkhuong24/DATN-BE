package com.example.backend.core.salesCounter.repository;

import com.example.backend.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductSalesCouterRepository extends JpaRepository<Product, Long> {
    @Query(value = "SELECT p, pd, s, c " +
            "FROM Product p " +
            "JOIN ProductDetail pd ON p.id = pd.product.id " +
            "JOIN Size s ON pd.size.id = s.id " +
            "JOIN Color c ON pd.color.id = c.id " +
            "WHERE p.code LIKE %:keyword% OR p.name LIKE %:keyword%", nativeQuery = true)
    List<Object[]> searchProductDetails(@Param("keyword") String keyword);
}
