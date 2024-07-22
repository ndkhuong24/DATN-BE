package com.example.backend.core.view.repository;

import com.example.backend.core.model.Brand;
import com.example.backend.core.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByIdCustomer(Long id);

    Optional<Cart> findByIdProductAndIdColorAndIdSizeAndIdCustomer(Long idProduct, Long idColor, Long idSize, Long idCustomer);
}
