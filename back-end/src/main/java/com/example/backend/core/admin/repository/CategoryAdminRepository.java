package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryAdminRepository extends JpaRepository<Category,Long> {
}
