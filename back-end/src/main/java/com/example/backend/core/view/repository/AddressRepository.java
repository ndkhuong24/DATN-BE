package com.example.backend.core.view.repository;

import com.example.backend.core.model.Address;
import com.example.backend.core.view.dto.AddressDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByIdCustomerOrderByCreateDateDesc(Long idCustomer);

    @Query(value = "select  a from  Address a where a.config = 0 and a.idCustomer = ?1")
    Address getAddressByCustomer(Long id);

    @Modifying
    @Query(value = "update Address set config = 1 where id_customer = ?1" , nativeQuery = true)
    void updateConfigByCustomer(Long idCustomer);
}
