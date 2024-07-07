package com.example.backend.core.admin.repository.impl;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.dto.ProductAdminDTO;
import com.example.backend.core.admin.dto.ProductDetailAdminDTO;
import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.admin.mapper.ColorAdminMapper;
import com.example.backend.core.admin.mapper.ProductAdminMapper;
import com.example.backend.core.admin.mapper.SizeAdminMapper;
import com.example.backend.core.admin.repository.ColorAdminRepository;
import com.example.backend.core.admin.repository.ProductAdminCustomRepository;
import com.example.backend.core.admin.repository.ProductAdminRepository;
import com.example.backend.core.admin.repository.SizeAdminRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ProductAdminCustomRepositoryImpl implements ProductAdminCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ColorAdminRepository colorAdminRepository;

    @Autowired
    private ColorAdminMapper colorAdminMapper;

    @Autowired
    private SizeAdminRepository sizeAdminRepository;

    @Autowired
    private SizeAdminMapper sizeAdminMapper;

    @Autowired
    private ProductAdminRepository productAdminRepository;

    @Autowired
    private ProductAdminMapper productAdminMapper;

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
                productAdminDTO.setCreateDate(((LocalDateTime) obj[2]));
                productAdminDTO.setBrandName((String) obj[3]);
                productAdminDTO.setCategoryName((String) obj[4]);
                productAdminDTO.setMaterialName((String) obj[5]);
//                productAdminDTO.setSoleHeight((String) obj[6]);
//                productAdminDTO.setPriceExport(new BigDecimal(obj[6].toString()).toString());
                productAdminDTO.setDescription((String) obj[7]);
                productAdminDTO.setStatus((Integer) obj[8]);
//                productAdminDTO.setTotalQuantity(((BigDecimal) obj[9]).intValue());
//                productAdminDTO.setSizeExport((String) obj[10]);
//                productAdminDTO.setColorExport((String) obj[11]);
                lstProduct.add(productAdminDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lstProduct;
    }

    @Override
    public List<ProductDetailAdminDTO> topProductBestSeller() {
        List<ProductDetailAdminDTO> productDetailAdminDTOList = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT \n" +
                    "    pd.id,\n" +
                    "    pd.id_product,\n" +
                    "    pd.id_color,\n" +
                    "    pd.id_size,\n" +
                    "    pd.shoe_collar,\n" +
                    "    pd.price,\n" +
                    "    IFNULL(SUM(od.quantity), 0) AS total_sold\n" +
                    "FROM\n" +
                    "    datn.product_detail pd\n" +
                    "        JOIN\n" +
                    "    datn.product p ON p.id = pd.id_product\n" +
                    "        LEFT JOIN\n" +
                    "    datn.order_detail od ON od.id_product_detail = pd.id\n" +
                    "        LEFT JOIN\n" +
                    "    datn.order o ON o.id = od.id_order\n" +
                    "        AND MONTH(o.payment_date) = MONTH(NOW())\n" +
                    "        AND o.status = 3\n" +
                    "GROUP BY pd.id, pd.id_product, pd.id_color, pd.id_size, pd.shoe_collar, pd.price\n" +
                    "ORDER BY total_sold DESC\n" +
                    "LIMIT 5;\n");
            Query query = entityManager.createNativeQuery(sql.toString());

            List<Object[]> queryResultList = query.getResultList();

            for (Object[] obj : queryResultList) {
                ProductDetailAdminDTO productDetailAdminDTO = new ProductDetailAdminDTO();

                productDetailAdminDTO.setId(((Number) obj[0]).longValue());
                productDetailAdminDTO.setIdProduct(((Number) obj[1]).longValue());
                productDetailAdminDTO.setIdColor(((Number) obj[2]).longValue());
                productDetailAdminDTO.setIdSize(((Number) obj[3]).longValue());
                productDetailAdminDTO.setShoeCollar((Integer) obj[4]);
                productDetailAdminDTO.setPrice((BigDecimal) obj[5]);
                productDetailAdminDTO.setTotalBestSeller((BigDecimal) obj[6]);
//                if (obj[6] instanceof BigDecimal) {
//                    productDetailAdminDTO.setTotalBestSeller(((BigDecimal) obj[6]).intValue());
//                } else if (obj[6] instanceof Integer) {
//                    productDetailAdminDTO.setTotalBestSeller((Integer) obj[6]);
//                } else if (obj[6] instanceof Long) {
//                    productDetailAdminDTO.setTotalBestSeller(((Long) obj[6]).intValue());
//                } else if (obj[6] instanceof Number) {
//                    productDetailAdminDTO.setTotalBestSeller(((Number) obj[6]).intValue());
//                } else {
//                    throw new IllegalArgumentException("Unexpected data type for total_sold: " + obj[6].getClass().getName());
//                }
//                System.out.println("Type of obj[6]: " + obj[6].getClass().getName());

                ColorAdminDTO colorAdminDTO = colorAdminMapper.toDto(colorAdminRepository.findById(((Number) obj[2]).longValue()).orElse(null));
                productDetailAdminDTO.setColorDTO(colorAdminDTO);

                SizeAdminDTO sizeAdminDTO = sizeAdminMapper.toDto(sizeAdminRepository.findById(((Number) obj[3]).longValue()).orElse(null));
                productDetailAdminDTO.setSizeDTO(sizeAdminDTO);

                ProductAdminDTO productAdminDTO = productAdminMapper.toDto(productAdminRepository.findById(((Number) obj[1]).longValue()).orElse(null));
                String imageURL = "http://localhost:8081/view/anh/" + ((Number) obj[1]).longValue();
                productAdminDTO.setImageURL(imageURL);
                productDetailAdminDTO.setProductDTO(productAdminDTO);

                productDetailAdminDTOList.add(productDetailAdminDTO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return productDetailAdminDTOList;
    }

}
