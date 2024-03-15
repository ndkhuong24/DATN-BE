package com.example.backend.core.view.repository.impl;

import com.example.backend.core.view.dto.BrandDTO;
import com.example.backend.core.view.repository.BrandCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class BrandCustomRepositoryImpl implements BrandCustomRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<BrandDTO> getAllBrandTop() {
        List<BrandDTO> brandDTOList = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT b.id, b.name, IFNULL(SUM(od.quantity), 0) AS total_sold\n" +
                    "FROM product p\n" +
                    "left JOIN product_detail pd ON p.id = pd.id_product\n" +
                    "join brand b on b.id = p.id_brand\n" +
                        "LEFT JOIN order_detail od ON od.id_product_detail = pd.id where b.idel = 0\n" +
                    "GROUP BY b.id, b.name\n" +
                    "ORDER BY total_sold DESC\n" +
                    "LIMIT 5;");
            Query query = entityManager.createNativeQuery(sql.toString());
            List<Object[]> lst = query.getResultList();
            for (Object[] o: lst) {
                BrandDTO brandDTO = new BrandDTO();
                brandDTO.setId(((Number) o[0]).longValue());
                brandDTO.setName((String) o[1]);
                brandDTOList.add(brandDTO);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return brandDTOList;
    }
}
