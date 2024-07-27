package com.example.backend.core.view.repository.impl;

import com.example.backend.core.view.dto.ProductDTO;
import com.example.backend.core.view.repository.DiscountRepository;
import com.example.backend.core.view.repository.ProductCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class ProductCustomRepositoryImpl implements ProductCustomRepository {
    @Autowired
    EntityManager entityManager;

    @Autowired
    private DiscountRepository discountRepository;

    @Override
    public List<ProductDTO> getProductNoiBatByBrand(Long thuongHieu) {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "    p.id,\n" +
                    "    p.code,\n" +
                    "    p.name,\n" +
                    "    MIN(pd.price) AS minPrice,\n" +
                    "    MAX(pd.price) AS maxPrice,\n" +
                    "    IFNULL(SUM(od.quantity), 0) AS total_sold\n" +
                    "FROM\n" +
                    "    product p\n" +
                    "    JOIN\n" +
                    "    product_detail pd ON p.id = pd.id_product\n" +
                    "    LEFT JOIN\n" +
                    "    order_detail od ON od.id_product_detail = pd.id\n" +
                    "    LEFT JOIN\n" +
                    "    brand b ON b.id = p.id_brand\n" +
                    "{1}\n" +
//                    " WHERE\n" +
//                    "    p.status = 0\n" +
                    "GROUP BY \n" +
                    "    p.id, \n" +
                    "    p.code, \n" +
                    "    p.name\n" +
                    "ORDER BY \n" +
                    "    total_sold DESC\n" +
                    "LIMIT 8;\n");

            String sqlStr = sql.toString();
            if (null != thuongHieu && thuongHieu > 0) {
                sqlStr = sqlStr.replace("{1}", "  where b.id = :idBrand and p.status = 0");
            } else {
                sqlStr = sqlStr.replace("{1}", " where p.status = 0");
            }
            Query query = entityManager.createNativeQuery(sqlStr);

            if (null != thuongHieu && thuongHieu > 0) {
                query.setParameter("idBrand", thuongHieu);
            }
            List<Object[]> lst = query.getResultList();
            List<ProductDTO> lstProduct = new ArrayList<>();

            for (Object[] obj : lst) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setId(((Number) obj[0]).longValue());
                productDTO.setCode((String) obj[1]);
                productDTO.setName((String) obj[2]);
                productDTO.setMinPrice((BigDecimal) obj[3]);
                productDTO.setMaxPrice((BigDecimal) obj[4]);
                productDTO.setTotalSold((BigDecimal) obj[5]);
                String imageURL = "http://localhost:8081/view/anh/" + ((Number) obj[0]).longValue();
                productDTO.setImageURL(imageURL);
                lstProduct.add(productDTO);
            }
            return lstProduct;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ProductDTO> getProductTuongTu(Long idProduct, Long idCategory) {
        List<ProductDTO> lstProduct = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ");
            sql.append("p.id, p.code, p.name, ");
            sql.append("MIN(pd.price) AS minPrice, ");
            sql.append("MAX(pd.price) AS maxPrice ");
            sql.append("FROM product p ");
            sql.append("JOIN category c ON c.id = p.id_category ");
            sql.append("JOIN product_detail pd ON pd.id_product = p.id ");
            sql.append(" WHERE p.status = 0 ");

//            if (idProduct != null && idCategory != null) {
//                sql.append("WHERE (p.id != :idProduct AND c.id = :idCategory) ");
//                sql.append("OR (:idProduct IS NULL OR :idCategory IS NULL) ");
//            }
            if (idProduct != null && idCategory != null) {
                sql.append("AND (p.id != :idProduct AND c.id = :idCategory) ");
            } else {
                sql.append("AND (:idProduct IS NULL OR :idCategory IS NULL) ");
            }

            sql.append("GROUP BY p.id, p.code, p.name ");
            sql.append("LIMIT 4;");

            Query query = entityManager.createNativeQuery(sql.toString());

            if (idProduct != null && idCategory != null) {
                query.setParameter("idProduct", idProduct);
                query.setParameter("idCategory", idCategory);
            }
            List<Object[]> lst = query.getResultList();

            for (Object[] obj : lst) {
                ProductDTO productDTO = new ProductDTO();

                productDTO.setId(((Number) obj[0]).longValue());
                productDTO.setCode((String) obj[1]);
                productDTO.setName((String) obj[2]);
                productDTO.setMinPrice((BigDecimal) obj[3]);
                productDTO.setMaxPrice((BigDecimal) obj[4]);

                String imageURL = "http://localhost:8081/view/anh/" + ((Number) obj[0]).longValue();
                productDTO.setImageURL(imageURL);

                lstProduct.add(productDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lstProduct;
    }
}
