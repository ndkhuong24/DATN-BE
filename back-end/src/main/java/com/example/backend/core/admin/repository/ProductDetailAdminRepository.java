package com.example.backend.core.admin.repository;

import com.example.backend.core.model.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ProductDetailAdminRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findByIdProduct(Long idProduct);

    @Modifying
    void deleteByIdProduct(Long idProduct);

//    List<ProductDetail> findByNameLikeOrCodeLike(String s, String s1);

    @Query("SELECT pd FROM ProductDetail pd WHERE pd.idProduct IN (SELECT p.id FROM Product p WHERE p.name LIKE %?1% OR p.code LIKE %?2%)")
    List<ProductDetail> findByNameLikeOrCodeLike(String nameParam, String codeParam);
}
