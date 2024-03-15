package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Brand;
import com.example.backend.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandAdminRepository extends JpaRepository<Brand,Long> {
    List<Brand> findByNameLike(String param);
}
