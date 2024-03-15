package com.example.backend.core.admin.repository;

import com.example.backend.core.model.VoucherFreeShip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoucherFreeShipAdminRepository extends JpaRepository<VoucherFreeShip,Long> {
}
