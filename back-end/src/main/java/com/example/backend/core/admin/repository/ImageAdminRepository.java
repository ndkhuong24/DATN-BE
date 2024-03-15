package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ImageAdminRepository extends JpaRepository<Images,Long> {
//    List<Images> findByOrderById();
    List<Images> findByIdProduct(Long idProduct);
    @Modifying
    void deleteByIdProduct(Long idProduct);
}
