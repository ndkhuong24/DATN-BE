package com.example.backend.core.view.repository;

import com.example.backend.core.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface MaterialRepository extends JpaRepository<Material, Long> {
}
