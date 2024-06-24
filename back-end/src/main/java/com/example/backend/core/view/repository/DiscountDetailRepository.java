package com.example.backend.core.view.repository;

import com.example.backend.core.model.DiscountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

<<<<<<< HEAD
=======

>>>>>>> 7655df1fef7905661c4070e435bd6011f90bbdef
@Repository
@Transactional
public interface DiscountDetailRepository extends JpaRepository<DiscountDetail, Long> {
//    DiscountDetail findByIdDiscountAndIdProduct(Long idDiscount, Long idProduct);
}
