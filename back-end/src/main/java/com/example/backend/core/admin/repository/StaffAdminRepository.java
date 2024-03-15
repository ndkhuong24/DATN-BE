package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffAdminRepository extends JpaRepository<Staff,Long> {
    Optional<Staff> findById(Long id);
    boolean existsByPhone(String phone);
    boolean existsByEmail(String email);
    List<Staff> findByCodeLikeOrPhoneLike(String param, String params);

}
