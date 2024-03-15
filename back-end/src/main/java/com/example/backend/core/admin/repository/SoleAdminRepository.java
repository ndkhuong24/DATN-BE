package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Sole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SoleAdminRepository extends JpaRepository<Sole,Long> {
}
