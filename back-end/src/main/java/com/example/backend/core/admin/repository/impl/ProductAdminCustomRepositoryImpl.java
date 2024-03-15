package com.example.backend.core.admin.repository.impl;

import com.example.backend.core.admin.dto.ImagesAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.repository.ProductAdminCustomRepository;
import com.example.backend.core.view.dto.ImagesDTO;
import com.example.backend.core.view.dto.ProductDTO;
import com.mysql.cj.util.SaslPrep;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ProductAdminCustomRepositoryImpl implements ProductAdminCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<ProductAdminDTO> getAllProductExport() {
        List<ProductAdminDTO> lstProduct = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "    p.code AS code,\n" +
                    "    p.name AS name,\n" +
                    "    p.create_date AS createDate,\n" +
                    "    b.name AS brandName,\n" +
                    "    c.name AS categoryName,\n" +
                    "    m.name AS materialName,\n" +
                    "    p.price AS price,\n" +
                    "    p.description AS description,\n" +
                    "    p.status,\n" +
                    "    ifnull(SUM(detailInfo.quantity), 0) AS totalQuantity,\n" +
                    "    GROUP_CONCAT(DISTINCT detailInfo.sizeName ORDER BY detailInfo.sizeName SEPARATOR ', ') AS sizes,\n" +
                    "    GROUP_CONCAT(DISTINCT detailInfo.colorName ORDER BY detailInfo.colorName SEPARATOR ', ') AS colors\n" +
                    "FROM product p\n" +
                    "LEFT JOIN (\n" +
                    "    SELECT \n" +
                    "        pd.id_product,\n" +
                    "        sizeCheck.sizeName,\n" +
                    "        colorCheck.colorName,\n" +
                    "        SUM(pd.quantity) AS quantity\n" +
                    "    FROM product_detail pd\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT s.id AS id, GROUP_CONCAT(s.size_number) AS sizeName\n" +
                    "        FROM size s\n" +
                    "        GROUP BY s.id\n" +
                    "    ) AS sizeCheck ON sizeCheck.id = pd.id_size\n" +
                    "    LEFT JOIN (\n" +
                    "        SELECT c.id AS id, GROUP_CONCAT(c.name) AS colorName\n" +
                    "        FROM color c\n" +
                    "        GROUP BY c.id\n" +
                    "    ) AS colorCheck ON colorCheck.id = pd.id_color\n" +
                    "    GROUP BY pd.id_product, sizeCheck.sizeName, colorCheck.colorName\n" +
                    ") AS detailInfo ON p.id = detailInfo.id_product\n" +
                    "LEFT JOIN brand b ON p.id_brand = b.id\n" +
                    "LEFT JOIN category c ON p.id_category = c.id\n" +
                    "LEFT JOIN material m ON p.id_material = m.id\n" +
                    "GROUP BY p.id, code, name, createDate, brandName, categoryName, materialName, price, description, status\n" +
                    "LIMIT 0, 1000;\n");
            Query query = entityManager.createNativeQuery(sql.toString());
            List<Object[]> lstObj = query.getResultList();
            for (Object[] obj : lstObj) {
                ProductAdminDTO productAdminDTO = new ProductAdminDTO();
                productAdminDTO.setCode((String) obj[0]);
                productAdminDTO.setName((String) obj[1]);
                productAdminDTO.setCreateDate(((Timestamp) obj[2]).toInstant());
                productAdminDTO.setBrandName((String) obj[3]);
                productAdminDTO.setCategoryName((String) obj[4]);
                productAdminDTO.setMaterialName((String) obj[5]);
//                productAdminDTO.setSoleHeight((String) obj[6]);
                productAdminDTO.setPriceExport(new BigDecimal(obj[6].toString()).toString());
                productAdminDTO.setDescription((String) obj[7]);
                productAdminDTO.setStatus((Integer) obj[8]);
                productAdminDTO.setTotalQuantity(((BigDecimal) obj[9]).intValue());
                productAdminDTO.setSizeExport((String) obj[10]);
                productAdminDTO.setColorExport((String) obj[11]);
                lstProduct.add(productAdminDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lstProduct;
    }

    @Override
    public List<ProductAdminDTO> topProductBestSeller() {
        List<ProductAdminDTO> lstProduct = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT p.id, p.code, p.name, p.price, images.image_names, b.name, IFNULL(SUM(od.quantity), 0) AS total_sold\n" +
                    "FROM product p\n" +
                    "left JOIN product_detail pd ON p.id = pd.id_product\n" +
                    "join brand b on b.id = p.id_brand\n" +
                    "LEFT JOIN order_detail od ON od.id_product_detail = pd.id\n" +
                    "LEFT JOIN (\n" +
                    "    SELECT id_product, GROUP_CONCAT(image_name) AS image_names\n" +
                    "    FROM images\n" +
                    "    GROUP BY id_product\n" +
                    ") images ON images.id_product = p.id\n" +
                    "left join `order` o on o.id = od.id_order\n" +
                    "where month(o.payment_date) = month(now()) and o.status = 3\n" +
                    "GROUP BY p.id, p.code, p.name, p.price, images.image_names, b.name\n" +
                    "ORDER BY total_sold DESC\n" +
                    "LIMIT 5;");
            Query query  = entityManager.createNativeQuery(sql.toString());
            List<Object[]> lst = query.getResultList();
            for (Object[] obj: lst) {
                ProductAdminDTO dto = new ProductAdminDTO();
                List<ImagesAdminDTO> imagesAdminDTOLis = new ArrayList<>();
                dto.setId(((Number) obj[0]).longValue());
                dto.setCode((String) obj[1]);
                dto.setName((String) obj[2]);
                dto.setPrice((BigDecimal) obj[3]);
                dto.setBrandName((String) obj[5]);
                dto.setTotalBestSeller(((Number) obj[6]).intValue());
                String imagesString = (String) obj[4];
                if (imagesString != null && !imagesString.isEmpty()) {
                    for (String str : imagesString.split(",")) {
                        if (!str.trim().isEmpty()) { // Kiểm tra và bỏ qua chuỗi trống
                            ImagesAdminDTO imagesAdminDTO = new ImagesAdminDTO();
                            imagesAdminDTO.setImageName(str.trim());
                            imagesAdminDTOLis.add(imagesAdminDTO);
                        }
                    }
                }
                dto.setImagesDTOList(imagesAdminDTOLis);
                lstProduct.add(dto);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return lstProduct;
    }
}
