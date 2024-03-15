package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialAdminRepository extends JpaRepository<Material,Long> {
}
