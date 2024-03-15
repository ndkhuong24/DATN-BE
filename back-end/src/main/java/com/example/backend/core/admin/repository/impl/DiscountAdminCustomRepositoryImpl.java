package com.example.backend.core.admin.repository.impl;
import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.repository.DiscountAdminCustomRepository;
import com.example.backend.core.admin.repository.DiscountAdminRepository;
import com.example.backend.core.admin.repository.DiscountDetailAdminRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class DiscountAdminCustomRepositoryImpl implements DiscountAdminCustomRepository {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private DiscountDetailAdminRepository discountDetailRepository;

    @Override
    public void deleteAllDiscountDetailByDiscount(Long id) {
        try {
            Query query = entityManager.createQuery("delete from DiscountDetail where idDiscount = :idDiscount");
            query.setParameter("idDiscount", id);
            query.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    public List<DiscountDetailAdminDTO> discountExport() {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "    discount.id AS discount_id,\n" +
                    "    discount.code AS discount_code,\n" +
                    "    discount.name AS discount_name,\n" +
                    "    discount.create_date,\n" +
                    "    discount.create_name,\n" +
                    "    discount.start_date,\n" +
                    "    discount.end_date,\n" +
                    "    discount.description,\n" +
                    "    discount_detail.reduced_value ,\n" +
                    "    if(discount_detail.discount_type = 1, 'Theo %' , 'Theo tiền') as type ,\n" +
                    "    discount_detail.max_reduced ,\n" +
                    "    product.name as NameProduct\n" +
                    "FROM discount\n" +
                    "JOIN discount_detail ON discount.id = discount_detail.id_discount\n" +
                    "JOIN product ON discount_detail.id_product = product.id;");


            String sqlStr = sql.toString();
            Query query = entityManager.createNativeQuery(sqlStr);
            List<Object[]> resultList = query.getResultList();

            List<DiscountDetailAdminDTO> discounts = new ArrayList<>(); // Initialize the discounts list

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();

                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setCreateName(row[4].toString());
                discount.setDescription(row[7].toString());


                DiscountDetailAdminDTO discountDetailAdminDTO= new DiscountDetailAdminDTO();
                discountDetailAdminDTO.setReducedValue(new BigDecimal(row[8].toString()));
                discountDetailAdminDTO.setDiscountTypeStr((String) row[9]);
                discountDetailAdminDTO.setMaxReduced(row[10] != null ? new BigDecimal(row[10].toString()) : null);                discountDetailAdminDTO.setDiscountAdminDTO(discount);

                ProductAdminDTO productAdminDTO= new ProductAdminDTO();
                productAdminDTO.setName(row[11].toString());
                discountDetailAdminDTO.setProductDTO(productAdminDTO);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date createDate = dateFormat.parse(row[3].toString());
                    Date startDate = dateFormat.parse(row[5].toString());
                    Date endDate = dateFormat.parse(row[6].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);
                    discount.setCreateDate(createDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
                discounts.add(discountDetailAdminDTO);
            }
            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DiscountAdminDTO> getAll() {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +

                    "   d.id, " +
                    " d.code,\n" +
                    "    d.name,\n" +
                    "    d.start_date,\n" +
                    "    d.end_date,\n" +
                    "    d.description,\n" +
                    "    d.idel, " +
                    "COUNT(od.quantity) AS used_count\n" +
                    "FROM discount d " +
                    "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                    "where dele=0 " +
                    "GROUP BY d.id, d.code, d.name,d.start_date,d.end_date,d.description,d.idel;\n");


            String sqlStr = sql.toString();
            Query query = entityManager.createNativeQuery(sqlStr);
            List<Object[]> resultList = query.getResultList();

            List<DiscountAdminDTO> discounts = new ArrayList<>(); // Initialize the discounts list

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();

                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(row[7] != null ? new Integer(row[7].toString()) : null);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
                discounts.add(discount);

            }
            return discounts;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
        @Override
        public List<DiscountAdminDTO> getAllKichHoat() {
            try {
                StringBuilder sql = new StringBuilder("SELECT \n" +
                        "   d.id,  d.code,\n" +
                        "    d.name,\n" +
                        "    d.start_date,\n" +
                        "    d.end_date,\n" +
                        "    d.description,\n" +
                        "    d.idel," +
                        "COUNT(od.quantity) AS used_count\n" +
                        "FROM discount d LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                        "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                        "where idel = 1 and dele= 0 \n" +
                        "GROUP BY d.id, d.code, d.name,d.start_date,d.end_date,d.description, d.idel  \n\n");


                String sqlStr = sql.toString();
                Query query = entityManager.createNativeQuery(sqlStr);
                List<Object[]> resultList = query.getResultList();

                List<DiscountAdminDTO> discounts = new ArrayList<>(); // Initialize the discounts list

                for (Object[] row : resultList) {
                    DiscountAdminDTO discount = new DiscountAdminDTO();

                    discount.setId(Long.parseLong(row[0].toString()));
                    discount.setCode(row[1].toString());
                    discount.setName(row[2].toString());
                    discount.setDescription(row[5].toString());
                    discount.setIdel(Integer.valueOf(row[6].toString()));
                    discount.setUsed_count(row[7] != null ? new Integer(row[7].toString()) : null);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    try {
                        Date startDate = dateFormat.parse(row[3].toString());
                        Date endDate = dateFormat.parse(row[4].toString());

                        discount.setStartDate(startDate);
                        discount.setEndDate(endDate);

                        if (new Date(System.currentTimeMillis()).after(endDate)) {
                            discount.setStatus(1);
                        } else {
                            discount.setStatus(0);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                        continue;
                    }

                    discounts.add(discount);
                }
                return discounts;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        public List<DiscountAdminDTO> getAllKhongKichHoat() {
            try {
                StringBuilder sql = new StringBuilder("SELECT \n" +
                        "   d.id,  d.code,\n" +
                        "    d.name,\n" +
                        "    d.start_date,\n" +
                        "    d.end_date,\n" +
                        "    d.description,\n" +
                        "    d.idel, " +
                        "sum(od.quantity) AS used_count\n" +
                        "FROM discount d LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                        "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                        "where idel = 0 and dele=0 \n" +
                        "GROUP BY d.id, d.code, d.name,d.start_date,d.end_date,d.description, d.idel;  \n\n");


                String sqlStr = sql.toString();
                Query query = entityManager.createNativeQuery(sqlStr);
                List<Object[]> resultList = query.getResultList();

                List<DiscountAdminDTO> discounts = new ArrayList<>(); // Initialize the discounts list

                for (Object[] row : resultList) {
                    DiscountAdminDTO discount = new DiscountAdminDTO();

                    discount.setId(Long.parseLong(row[0].toString()));
                    discount.setCode(row[1].toString());
                    discount.setName(row[2].toString());
                    discount.setDescription(row[5].toString());
                    discount.setIdel(Integer.valueOf(row[6].toString()));
                    discount.setUsed_count(row[7] != null ? new Integer(row[7].toString()) : null);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    try {
                        Date startDate = dateFormat.parse(row[3].toString());
                        Date endDate = dateFormat.parse(row[4].toString());

                        discount.setStartDate(startDate);
                        discount.setEndDate(endDate);

                        if (new Date(System.currentTimeMillis()).after(endDate)) {
                            discount.setStatus(1);
                        } else {
                            discount.setStatus(0);
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                        continue;
                    }

                    discounts.add(discount);
                }
                return discounts;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public List<DiscountAdminDTO> getAllByCodeOrName(String search) {
            try {
                StringBuilder sql = new StringBuilder("SELECT \n" +
                        "   d.id, " +
                        "   d.code,\n" +
                        "   d.name,\n" +
                        "   d.start_date,\n" +
                        "   d.end_date,\n" +
                        "   d.description,\n" +
                        "   d.idel, " +
                        "COUNT(od.quantity) AS used_count\n" +
                        "FROM discount d " +
                        "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                        "LEFT JOIN order_detail AS od ON d.code = od.code_discount ");

                // Kiểm tra xem có search được cung cấp không
                if (search != null && !search.isEmpty()) {
                    sql.append("WHERE LOWER(d.code) LIKE LOWER(:search) OR LOWER(d.name) LIKE LOWER(:search) and dele=0\n");
                }

                sql.append("GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel\n" +
                        ";");

                Query query = entityManager.createNativeQuery(sql.toString());

                // Nếu có search, thiết lập tham số
                if (search != null && !search.isEmpty()) {
                    query.setParameter("search", "%" + search + "%");
                }

                List<Object[]> resultList = query.getResultList();
                List<DiscountAdminDTO> discounts = new ArrayList<>();

                for (Object[] row : resultList) {
                    DiscountAdminDTO discount = new DiscountAdminDTO();
                    discount.setId(Long.parseLong(row[0].toString()));
                    discount.setCode(row[1].toString());
                    discount.setName(row[2].toString());
                    discount.setDescription(row[5].toString());
                    discount.setIdel(Integer.valueOf(row[6].toString()));
                    discount.setUsed_count(row[7] != null ? new Integer(row[7].toString()) : null);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    try {
                        Date startDate = dateFormat.parse(row[3].toString());
                        Date endDate = dateFormat.parse(row[4].toString());

                        discount.setStartDate(startDate);
                        discount.setEndDate(endDate);

                        if (new Date(System.currentTimeMillis()).after(endDate)) {
                            discount.setStatus(1);
                        } else {
                            discount.setStatus(0);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        continue;
                    }


                    discounts.add(discount);
                }

                return discounts;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public List<DiscountAdminDTO> getAllByCategory(String category) {
            List<DiscountAdminDTO> discounts = new ArrayList<>();
            try {
                StringBuilder sql = new StringBuilder("SELECT \n" +
                        "   d.id,\n" +
                        "   d.code,\n" +
                        "   d.name,\n" +
                        "   d.start_date,\n" +
                        "   d.end_date,\n" +
                        "   d.description,\n" +
                        "   d.idel,\n" +
                        "COUNT(od.quantity) AS used_count\n" +
                        "FROM discount d\n" +
                        "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                        "left JOIN order_detail AS od ON d.code = od.code_discount\n" +
                        "LEFT JOIN product AS p ON dd.id_product = p.id\n" +
                        "LEFT JOIN category AS c ON p.id_category = c.id where dele=0  \n");

                if (category != null && !category.isEmpty()) {
                    sql.append(" and LOWER(c.name) LIKE LOWER(:category)");
                }
                sql.append("  GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel" );

                Query query = entityManager.createNativeQuery(sql.toString());

                if (category != null && !category.isEmpty()) {
                    query.setParameter("category", "%" + category + "%");
                }


                List<Object[]> resultList = query.getResultList();

                for (Object[] row : resultList) {
                    DiscountAdminDTO discount = new DiscountAdminDTO();
                    discount.setId(Long.parseLong(row[0].toString()));
                    discount.setCode(row[1].toString());
                    discount.setName(row[2].toString());
                    discount.setDescription(row[5].toString());
                    discount.setIdel(Integer.valueOf(row[6].toString()));
                    discount.setUsed_count(row[7] != null ? new Integer(row[7].toString()) : null);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    try {
                        Date startDate = dateFormat.parse(row[3].toString());
                        Date endDate = dateFormat.parse(row[4].toString());

                        discount.setStartDate(startDate);
                        discount.setEndDate(endDate);

                        if (new Date(System.currentTimeMillis()).after(endDate)) {
                            discount.setStatus(1);
                        } else {
                            discount.setStatus(0);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        continue;
                    }

                    discounts.add(discount);
                }

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            return discounts;
        }

        @Override
        public List<DiscountAdminDTO> getAllByProductNameOrCode(String productNameOrCode) {
            try {
                StringBuilder sql = new StringBuilder("SELECT \n" +
                        "   d.id, " +
                        "   d.code,\n" +
                        "   d.name,\n" +
                        "   d.start_date,\n" +
                        "   d.end_date,\n" +
                        "   d.description,\n" +
                        "   d.idel, " +
                        "COUNT(od.quantity) AS used_count\n" +
                        "FROM discount d\n" +
                        "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                        "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                        "LEFT JOIN product AS p ON dd.id_product = p.id\n" +
                        "WHERE LOWER(p.name) LIKE LOWER(:productNameOrCode) OR LOWER(p.code) LIKE LOWER(:productNameOrCode) and dele=0 \n" +
                        "GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel" +
                        " \n");

                Query query = entityManager.createNativeQuery(sql.toString());
                query.setParameter("productNameOrCode", "%" + productNameOrCode + "%");

                List<Object[]> resultList = query.getResultList();
                List<DiscountAdminDTO> discounts = new ArrayList<>();

                for (Object[] row : resultList) {
                    DiscountAdminDTO discount = new DiscountAdminDTO();
                    discount.setId(Long.parseLong(row[0].toString()));
                    discount.setCode(row[1].toString());
                    discount.setName(row[2].toString());
                    discount.setDescription(row[5].toString());
                    discount.setIdel(Integer.valueOf(row[6].toString()));
                    discount.setUsed_count(row[7] != null ? new Integer(row[7].toString()) : null);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    try {
                        Date startDate = dateFormat.parse(row[3].toString());
                        Date endDate = dateFormat.parse(row[4].toString());

                        discount.setStartDate(startDate);
                        discount.setEndDate(endDate);

                        if (new Date(System.currentTimeMillis()).after(endDate)) {
                            discount.setStatus(1);
                        } else {
                            discount.setStatus(0);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        continue;
                    }

                    discounts.add(discount);
                }

                return discounts;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public List<DiscountAdminDTO> getAllByBrand(String brand) {
            try {
                StringBuilder sql = new StringBuilder("SELECT \n" +
                        "    d.id,    \n" +
                        "    d.code,\n" +
                        "    d.name,\n" +
                        "    d.start_date,\n" +
                        "    d.end_date,\n" +
                        "    d.description,\n" +
                        "    d.idel, \n" +
                        "    COUNT(od.quantity) AS used_count\n" +
                        "FROM discount d\n" +
                        "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                        "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                        "LEFT JOIN product AS p ON dd.id_product = p.id\n" +
                        "LEFT JOIN brand AS b ON p.id_brand = b.id\n" +
                        "WHERE LOWER(b.name) LIKE LOWER(:brand) AND d.dele = 0\n" +
                        "GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel;\n");

                Query query = entityManager.createNativeQuery(sql.toString());
                query.setParameter("brand", "%" + brand + "%");

                List<Object[]> resultList = query.getResultList();
                List<DiscountAdminDTO> discounts = new ArrayList<>();

                for (Object[] row : resultList) {
                    DiscountAdminDTO discount = new DiscountAdminDTO();
                    discount.setId(Long.parseLong(row[0].toString()));
                    discount.setCode(row[1].toString());
                    discount.setName(row[2].toString());
                    discount.setDescription(row[5].toString());
                    discount.setIdel(Integer.valueOf(row[6].toString()));
                    discount.setUsed_count(row[7] != null ? new Integer(row[7].toString()) : null);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                    try {
                        Date startDate = dateFormat.parse(row[3].toString());
                        Date endDate = dateFormat.parse(row[4].toString());

                        discount.setStartDate(startDate);
                        discount.setEndDate(endDate);

                        if (new Date(System.currentTimeMillis()).after(endDate)) {
                            discount.setStatus(1);
                        } else {
                            discount.setStatus(0);
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        continue;
                    }

                    discounts.add(discount);
                }

                return discounts;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    @Override
    public List<DiscountAdminDTO> getAllByDateRange(String fromDate, String toDate) {
        try {
            StringBuilder sql = new StringBuilder("SELECT \n" +
                    "   d.id, " +
                    "   d.code,\n" +
                    "   d.name,\n" +
                    "   d.start_date,\n" +
                    "   d.end_date,\n" +
                    "   d.description,\n" +
                    "   d.idel, " +
                    "COUNT(od.quantity) AS used_count\n" +
                    "FROM discount d " +
                    "LEFT JOIN discount_detail AS dd ON d.id = dd.id_discount\n" +
                    "LEFT JOIN order_detail AS od ON d.code = od.code_discount\n" +
                    "WHERE d.dele = 0 " );
            if(StringUtils.isNotBlank(fromDate)){
                sql.append("and  (:dateFrom is null or STR_TO_DATE(DATE_FORMAT(d.start_date, '%Y/%m/%d'), '%Y/%m/%d') >= STR_TO_DATE(:dateFrom , '%d/%m/%Y')) ");
            }
            if (StringUtils.isNotBlank(toDate)){
                sql.append("  and (:dateTo is null or STR_TO_DATE(DATE_FORMAT(d.start_date, '%Y/%m/%d'), '%Y/%m/%d') <= STR_TO_DATE(:dateTo , '%d/%m/%Y'))  ");
            }
            sql.append(" GROUP BY d.id, d.code, d.name, d.start_date, d.end_date, d.description, d.idel");
            Query query = entityManager.createNativeQuery(sql.toString());
            if (StringUtils.isNotBlank(fromDate)){
                query.setParameter("dateFrom", fromDate);
            }
            if (StringUtils.isNotBlank(toDate)) {
                query.setParameter("dateTo",toDate);
            }

            List<Object[]> resultList = query.getResultList();
            List<DiscountAdminDTO> discounts = new ArrayList<>();

            for (Object[] row : resultList) {
                DiscountAdminDTO discount = new DiscountAdminDTO();
                discount.setId(Long.parseLong(row[0].toString()));
                discount.setCode(row[1].toString());
                discount.setName(row[2].toString());
                discount.setDescription(row[5].toString());
                discount.setIdel(Integer.valueOf(row[6].toString()));
                discount.setUsed_count(row[7] != null ? new Integer(row[7].toString()) : null);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    Date startDate = dateFormat.parse(row[3].toString());
                    Date endDate = dateFormat.parse(row[4].toString());

                    discount.setStartDate(startDate);
                    discount.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        discount.setStatus(1);
                    } else {
                        discount.setStatus(0);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }

                discounts.add(discount);
            }

            return discounts;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<ProductAdminDTO> getAllProduct() {
        try {
            String sql = "SELECT\n" +
                    "    p.id,\n" +
                    "    p.code,\n" +
                    "    p.name,\n" +
                    "    b.name AS brand_name,\n" +
                    "    c.name AS category_name,\n" +
                    "    IFNULL(SUM(od.quantity), 0) AS total_sold,\n" +
                    "    p.price\n" +
                    "FROM\n" +
                    "    product p\n" +
                    "LEFT JOIN\n" +
                    "    product_detail pd ON p.id = pd.id_product\n" +
                    "LEFT JOIN\n" +
                    "    order_detail od ON od.id_product_detail = pd.id\n" +
                    "LEFT JOIN\n" +
                    "    brand b ON p.id_brand = b.id\n" +
                    "LEFT JOIN\n" +
                    "    category c ON p.id_category = c.id\n" +
                    "WHERE\n" +
                    "    Not EXISTS (\n" +
                    "        SELECT 1\n" +
                    "        FROM discount_detail dd\n" +
                    "        JOIN discount d ON dd.id_discount = d.id\n" +
                    "        WHERE p.id = dd.id_product AND ( d.idel = 1 and d.dele =0)  \n" +
                    "    )\n" +
                    "GROUP BY\n" +
                    "    p.id,\n" +
                    "    p.code,\n" +
                    "    p.name,\n" +
                    "    brand_name,\n" +
                    "    category_name\n" +
                    "ORDER BY\n" +
                    "    total_sold;\n";
            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();
            List<ProductAdminDTO> productDTOList = new ArrayList<>();

            for (Object[] row : resultList) {
                ProductAdminDTO product = new ProductAdminDTO();
                product.setId(((Number) row[0]).longValue());
                product.setCode((String) row[1]);
                product.setName((String) row[2]);
                product.setPrice(new BigDecimal(row[6].toString()));
                BrandAdminDTO brand = new BrandAdminDTO();
                brand.setName((String) row[3]);
                product.setBrandAdminDTO(brand);

                CategoryAdminDTO category = new CategoryAdminDTO();
                category.setName((String) row[4]);
                product.setCategoryAdminDTO(category);
                product.setTotalQuantity((((Number) row[5]).intValue()));

                productDTOList.add(product);
            }

            return productDTOList;
        } catch (PersistenceException e) {
            e.printStackTrace(); // Handle the exception properly, e.g., log it or throw a custom exception
            return null;
        }
    }

    @Override
    public List<ProductAdminDTO> getAllProductKickHoat() {
        try {
            String sql = "SELECT\n" +
                    "    p.id,\n" +
                    "    p.code,\n" +
                    "    p.name,\n" +
                    "    b.name AS brand_name,\n" +
                    "    c.name AS category_name,\n" +
                    "    IFNULL(SUM(od.quantity), 0) AS total_sold,\n" +
                    "    p.price\n" +
                    "FROM\n" +
                    "    product p\n" +
                    "LEFT JOIN\n" +
                    "    product_detail pd ON p.id = pd.id_product\n" +
                    "LEFT JOIN\n" +
                    "    order_detail od ON od.id_product_detail = pd.id\n" +
                    "LEFT JOIN\n" +
                    "    brand b ON p.id_brand = b.id\n" +
                    "LEFT JOIN\n" +
                    "    category c ON p.id_category = c.id\n" +
                    "WHERE\n" +
                    "    EXISTS (\n" +
                    "        SELECT 1\n" +
                    "        FROM discount_detail dd\n" +
                    "        JOIN discount d ON dd.id_discount = d.id\n" +
                    "        WHERE p.id = dd.id_product AND ( d.idel = 1 and d.dele =0)  \n" +
                    "    )\n" +
                    "GROUP BY\n" +
                    "    p.id,\n" +
                    "    p.code,\n" +
                    "    p.name,\n" +
                    "    brand_name,\n" +
                    "    category_name\n" +
                    "ORDER BY\n" +
                    "    total_sold;\n";
            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();
            List<ProductAdminDTO> productDTOList = new ArrayList<>();

            for (Object[] row : resultList) {
                ProductAdminDTO product = new ProductAdminDTO();
                product.setId(((Number) row[0]).longValue());
                product.setCode((String) row[1]);
                product.setName((String) row[2]);
                product.setPrice(new BigDecimal(row[6].toString()));
                BrandAdminDTO brand = new BrandAdminDTO();
                brand.setName((String) row[3]);
                product.setBrandAdminDTO(brand);

                CategoryAdminDTO category = new CategoryAdminDTO();
                category.setName((String) row[4]);
                product.setCategoryAdminDTO(category);
                product.setTotalQuantity((((Number) row[5]).intValue()));

                productDTOList.add(product);
            }

            return productDTOList;
        } catch (PersistenceException e) {
            e.printStackTrace();
            return null;
        }
    }
}
