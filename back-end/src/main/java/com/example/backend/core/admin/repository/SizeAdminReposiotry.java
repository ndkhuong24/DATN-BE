package com.example.backend.core.admin.repository;

import com.example.backend.core.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeAdminReposiotry extends JpaRepository<Size,Long> {

    Size findBySizeNumber(String sizeNumber);

}
