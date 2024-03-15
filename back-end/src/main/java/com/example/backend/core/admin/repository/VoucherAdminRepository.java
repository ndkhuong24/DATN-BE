package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Voucher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoucherAdminRepository extends JpaRepository<Voucher,Long> {
}
