package com.example.backend.core.view.repository;

import com.example.backend.core.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface SizeRepository extends JpaRepository<Size, Long> {
}
