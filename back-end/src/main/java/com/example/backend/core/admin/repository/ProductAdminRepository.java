package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAdminRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameLikeOrCodeLike(String param, String params);

    @Query("SELECT u FROM Product u WHERE u.name LIKE %:name%")
    List<Product> findByName(@Param("name") String keyword);

    Product findByCode(String code);

    @Query("SELECT p FROM Product p WHERE (p.name LIKE %?1% OR p.code LIKE %?1%)")
    List<Product> searchByNameOrCode(String keyword);
}
