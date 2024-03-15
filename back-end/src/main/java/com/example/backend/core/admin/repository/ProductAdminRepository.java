package com.example.backend.core.admin.repository;

import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAdminRepository extends JpaRepository<Product,Long> {
//    @Query(value = "select prd.id,prd.code,prd.name,prd.createDate,prd.updateDate,prd.createName,prd.updateName,br.id,ct.id,mt.id,prd.description,prd.status,prd.idel,sl.id from Product prd join Brand br on prd.idBrand = br.id join Category ct on prd.idCategory = ct.id join " +
//            "Material  mt on prd.idMaterial = mt.id join Sole sl on prd.idSole = sl.id")
//    List<Product> getall();
//@Query("SELECT prd.id,prd.code,prd.name,prd.createDate,prd.updateDate,prd.createName,prd.updateName,prd.idBrand,prd.idCategory,prd.idMaterial,prd.description,prd.status,prd.idel,prd.idSole FROM Product prd JOIN prd.idBrand br JOIN prd.idCategory ct JOIN prd.idMaterial mt JOIN prd.idSole sl")
//    List<Product> getall();

    List<Product> findByNameLikeOrCodeLike(String param, String params);
    @Query("SELECT u FROM Product u WHERE u.name LIKE %:name%")
    List<Product> findByName(@Param("name") String keyword);
    Product findByCode(String code);
    @Query("SELECT p FROM Product p WHERE (p.name LIKE %?1% OR p.code LIKE %?1%)")
    List<Product> searchByNameOrCode(String keyword);
}
