package com.example.backend.core.admin.repository.impl;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.admin.repository.CustomerAdminRepository;
import com.example.backend.core.admin.repository.VoucherFSCustomerRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class VoucherFSCustomerRepositoryImpl implements VoucherFSCustomerRepository {
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private CustomerAdminRepository customerAdminRepository;

    @Override
    public List<VoucherFreeShipDTO> getAllVouchers() {
        try {
            String sql = "SELECT \n" +
                    "    v.id,\n" +
                    "    v.code,\n" +
                    "    v.name,\n" +
                    "    v.start_date,\n" +
                    "    v.end_date,\n" +
                    "    v.conditions,\n" +
                    "    v.reduced_value,\n" +
                    "    v.description,\n" +
                    "    v.idel,\n" +
                    "    v.quantity,\n" +
                    "    COUNT(CASE WHEN o.status = 3 THEN o.id ELSE NULL END) AS use_voucher," +
//                    "    COUNT(o.id) AS use_voucher,\n" +
                    "    v.create_date\n" +
                    "FROM\n" +
                    "    voucher_free_ship v\n" +
                    "        LEFT JOIN\n" +
                    "    `order` o ON o.code_voucher_ship = v.code\n" +
                    "WHERE\n" +
                    "    v.dele = 0\n" +
                    "GROUP BY v.id , v.code , v.name , v.start_date , v.end_date , v.conditions , v.reduced_value , v.description , v.idel , v.quantity , v.create_date;\n";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setUseVoucher(Integer.parseInt(row[10].toString()));

                try {
                    Timestamp startTimestamp = (Timestamp) row[3];
                    Timestamp endTimestamp = (Timestamp) row[4];
                    Timestamp createDateTimestamp = (Timestamp) row[11];

                    LocalDateTime startDate = startTimestamp.toLocalDateTime();
                    LocalDateTime endDate = endTimestamp.toLocalDateTime();
                    LocalDateTime createDate = createDateTimestamp.toLocalDateTime();

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);
                    voucher.setCreateDate(createDate);

                    if (LocalDateTime.now().isAfter(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    @Override
//    public List<VoucherFreeShipDTO> getAllVoucherFSsExport() {
//        try {
//            String sql = "SELECT v.* \n" +
//                    "FROM voucher_free_ship v\n" +
//                    " where v.dele=0;";
//
//            Query query = entityManager.createNativeQuery(sql);
//            List<Object[]> resultList = query.getResultList();
//            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
//            for (Object[] row : resultList) {
//                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();
//
//                voucher.setId(Long.parseLong(row[0].toString()));
//                voucher.setCode(row[1].toString());
//                voucher.setName(row[2].toString());
//                voucher.setIdCustomer((String) row[3]);
//                voucher.setConditionApply(new BigDecimal(row[7].toString()));
//                voucher.setReducedValue(new BigDecimal(row[9].toString()));
//                voucher.setQuantity(Integer.valueOf(row[14].toString()));
//                voucher.setLimitCustomer(row[15] != null ? Integer.valueOf((row[15].toString())) : null);
//                voucher.setStatus(row[11] != null ? Integer.valueOf((row[11].toString())) : null);
//
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//                try {
//                    LocalDate startDate = LocalDate.parse(row[3].toString());
//                    LocalDate endDate = LocalDate.parse(row[4].toString());
//
//                    voucher.setStartDate(startDate);
//                    voucher.setEndDate(endDate);
//
//                    if (LocalDate.now().isAfter(endDate)) {
//                        voucher.setStatus(1);
//                    } else {
//                        voucher.setStatus(0);
//                    }
//
//                } catch (DateTimeParseException e) {
//                    e.printStackTrace();
//                    continue;
//                }
//                if (StringUtils.isNotBlank(voucher.getIdCustomer()) || null != voucher.getIdCustomer()) {
//                    String listCodeCustomer = "";
//                    for (String str : voucher.getIdCustomer().split(",")) {
//                        Customer customer = customerAdminRepository.findById(Long.parseLong(str)).orElse(null);
//                        if (customer != null) {
//                            listCodeCustomer += customer.getCode() + ",";
//                        }
//                    }
//                    voucher.setListCodeCustomerExport(listCodeCustomer);
//                }
//                vouchers.add(voucher);
//            }
//            return vouchers;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public List<VoucherFreeShipDTO> getAllKichHoat() {
        try {
            String sql = "SELECT \n" +
                    "    v.id,\n" +
                    "    v.code,\n" +
                    "    v.name,\n" +
                    "    v.start_date,\n" +
                    "    v.end_date,\n" +
                    "    v.conditions,\n" +
                    "    v.reduced_value,\n" +
                    "    v.description,\n" +
                    "    v.idel,\n" +
                    "    v.quantity,\n" +
                    "    COUNT(CASE WHEN o.status = 3 THEN o.id ELSE NULL END) AS use_voucher,\n" +
//                    "    COUNT(o.id) AS use_voucher,\n" +
                    "    v.create_date\n" +
                    "FROM\n" +
                    "    voucher_free_ship v\n" +
                    "        LEFT JOIN\n" +
                    "    `order` o ON o.code_voucher_ship = v.code\n" +
                    "WHERE\n" +
                    "    v.dele = 0 AND v.idel = 1\n" +
                    "GROUP BY v.id , v.code , v.name , v.start_date , v.end_date , v.conditions , v.reduced_value , v.description , v.idel , v.quantity , v.create_date;\n";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setUseVoucher(Integer.parseInt(row[10].toString()));

                try {
                    Timestamp startTimestamp = (Timestamp) row[3];
                    Timestamp endTimestamp = (Timestamp) row[4];
                    Timestamp createDateTimestamp = (Timestamp) row[11];

                    LocalDateTime startDate = startTimestamp.toLocalDateTime();
                    LocalDateTime endDate = endTimestamp.toLocalDateTime();
                    LocalDateTime createDate = createDateTimestamp.toLocalDateTime();

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);
                    voucher.setCreateDate(createDate);

                    if (LocalDateTime.now().isAfter(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<VoucherFreeShipDTO> getAllKhongKH() {
        try {
            String sql = "SELECT \n" +
                    "    v.id,\n" +
                    "    v.code,\n" +
                    "    v.name,\n" +
                    "    v.start_date,\n" +
                    "    v.end_date,\n" +
                    "    v.conditions,\n" +
                    "    v.reduced_value,\n" +
                    "    v.description,\n" +
                    "    v.idel,\n" +
                    "    v.quantity,\n" +
                    "    COUNT(CASE WHEN o.status = 3 THEN o.id ELSE NULL END) AS use_voucher,\n" +
//                    "    COUNT(o.id) AS use_voucher,\n" +
                    "    v.create_date\n" +
                    "FROM\n" +
                    "    voucher_free_ship v\n" +
                    "        LEFT JOIN\n" +
                    "    `order` o ON o.code_voucher_ship = v.code\n" +
                    "WHERE\n" +
                    "    v.dele = 0 AND v.idel = 0\n" +
                    "GROUP BY v.id , v.code , v.name , v.start_date , v.end_date , v.conditions , v.reduced_value , v.description , v.idel , v.quantity , v.create_date;\n";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setUseVoucher(Integer.parseInt(row[10].toString()));

                try {
                    Timestamp startTimestamp = (Timestamp) row[3];
                    Timestamp endTimestamp = (Timestamp) row[4];
                    Timestamp createDateTimestamp = (Timestamp) row[11];

                    LocalDateTime startDate = startTimestamp.toLocalDateTime();
                    LocalDateTime endDate = endTimestamp.toLocalDateTime();
                    LocalDateTime createDate = createDateTimestamp.toLocalDateTime();

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);
                    voucher.setCreateDate(createDate);

                    if (LocalDateTime.now().isAfter(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<VoucherFreeShipDTO> getVouchersByTimeRange(String fromDate, String toDate) {
        try {
            StringBuilder sql = new StringBuilder("SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "  COUNT(CASE WHEN o.status = 3 THEN o.id ELSE NULL END) AS use_voucher ," +
//                    "  COUNT(o.id) AS use_voucher " +
                    "    v.create_date\n" +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN `order` o ON o.code_voucher_ship = v.code " +
                    "WHERE v.dele = 0 ");

            if (StringUtils.isNotBlank(fromDate)) {
                sql.append("and  (:dateFrom is null or STR_TO_DATE(DATE_FORMAT(v.start_date, '%Y/%m/%d'), '%Y/%m/%d') >= STR_TO_DATE(:dateFrom , '%d/%m/%Y')) ");
            }
            if (StringUtils.isNotBlank(toDate)) {
                sql.append("  and (:dateTo is null or STR_TO_DATE(DATE_FORMAT(v.start_date, '%Y/%m/%d'), '%Y/%m/%d') <= STR_TO_DATE(:dateTo , '%d/%m/%Y'))  ");
            }
            sql.append(" GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
                    "v.reduced_value, v.description, v.idel, v.quantity");

            Query query = entityManager.createNativeQuery(sql.toString());

            if (StringUtils.isNotBlank(fromDate)) {
                query.setParameter("dateFrom", fromDate);
            }
            if (StringUtils.isNotBlank(toDate)) {
                query.setParameter("dateTo", toDate);
            }


            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setUseVoucher(Integer.parseInt(row[10].toString()));

                try {
                    Timestamp startTimestamp = (Timestamp) row[3];
                    Timestamp endTimestamp = (Timestamp) row[4];
                    Timestamp createDateTimestamp = (Timestamp) row[11];

                    LocalDateTime startDate = startTimestamp.toLocalDateTime();
                    LocalDateTime endDate = endTimestamp.toLocalDateTime();
                    LocalDateTime createDate = createDateTimestamp.toLocalDateTime();

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);
                    voucher.setCreateDate(createDate);


                    if (LocalDateTime.now().isAfter(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<VoucherFreeShipDTO> getVouchersByKeyword(String keyword) {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "  COUNT(CASE WHEN o.status = 3 THEN o.id ELSE NULL END) AS use_voucher ," +
//                    "  COUNT(o.id) AS use_voucher " +
                    "    v.create_date\n" +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN `order` o ON o.code_voucher_ship = v.code " +
                    "WHERE v.name LIKE :keyword OR v.code LIKE :keyword and dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
                    "v.reduced_value, v.description, v.idel, v.quantity";

            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("keyword", "%" + keyword + "%"); // Sử dụng % để tìm kiếm mọi nơi trong chuỗi.

            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setUseVoucher(Integer.parseInt(row[10].toString()));

                try {
                    Timestamp startTimestamp = (Timestamp) row[3];
                    Timestamp endTimestamp = (Timestamp) row[4];
                    Timestamp createDateTimestamp = (Timestamp) row[11];

                    LocalDateTime startDate = startTimestamp.toLocalDateTime();
                    LocalDateTime endDate = endTimestamp.toLocalDateTime();
                    LocalDateTime createDate = createDateTimestamp.toLocalDateTime();

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);
                    voucher.setCreateDate(createDate);

                    if (LocalDateTime.now().isAfter(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<VoucherFreeShipDTO> getVouchersByCustomer(String searchTerm) {
        try {
            StringBuilder sql = new StringBuilder("SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "  COUNT(CASE WHEN o.status = 3 THEN o.id ELSE NULL END) AS use_voucher ," +
//                    "  COUNT(o.id) AS use_voucher " +
                    "    v.create_date\n" +
                    "FROM voucher_free_ship v " +
                    "LEFT JOIN `order` o ON o.code_voucher_ship = v.code " +
                    "LEFT JOIN customer c ON v.id_customer = c.id " +
                    "WHERE  v.dele = 0");

            if (searchTerm != null && !searchTerm.isEmpty()) {
//                sql.append(" and LOWER(c.fullname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ");
//                sql.append(" and LOWER(c.code) LIKE LOWER(:searchTerm) " +
//                        "   OR LOWER(c.fullname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) \") " +
//                        "   OR c.phone LIKE  :searchTerm ");
                sql.append(" AND (LOWER(c.code) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
                        " OR LOWER(c.fullname) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
                        " OR c.phone LIKE CONCAT('%', :searchTerm, '%'))");
            }
            sql.append(" GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions,v.reduced_value, v.description, v.idel, v.quantity");

            Query query = entityManager.createNativeQuery(sql.toString());

            if (searchTerm != null && !searchTerm.isEmpty()) {
                query.setParameter("searchTerm", "%" + searchTerm + "%");
            }
            List<Object[]> resultList = query.getResultList();

            List<VoucherFreeShipDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherFreeShipDTO voucher = new VoucherFreeShipDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setReducedValue(new BigDecimal(row[6].toString()));
                voucher.setDescription(row[7].toString());
                voucher.setIdel(Integer.valueOf(row[8].toString()));
                voucher.setQuantity(Integer.valueOf(row[9].toString()));
                voucher.setUseVoucher(Integer.parseInt(row[10].toString()));

                try {
                    Timestamp startTimestamp = (Timestamp) row[3];
                    Timestamp endTimestamp = (Timestamp) row[4];
                    Timestamp createDateTimestamp = (Timestamp) row[11];

                    LocalDateTime startDate = startTimestamp.toLocalDateTime();
                    LocalDateTime endDate = endTimestamp.toLocalDateTime();
                    LocalDateTime createDate = createDateTimestamp.toLocalDateTime();

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);
                    voucher.setCreateDate(createDate);

                    if (LocalDateTime.now().isAfter(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                    continue;
                }

                vouchers.add(voucher);
            }
            return vouchers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<CustomerAdminDTO> getAllCustomer() {
        try {
            String sql = "SELECT " +
                    "  c.id, " +
                    "  c.code, " +
                    "  c.fullname, " +
                    "  c.birthday, " +
                    "  c.phone, " +
                    "  c.email, " +
                    "  c.gender, " +
                    " ifnull( c.status, 0), " +
                    " ifnull( c.idel, 0), " +
                    "  COUNT(o.id) AS order_count " +
                    "FROM customer c " +
                    "LEFT JOIN `order` o ON c.id = o.id_customer " +
                    "GROUP BY c.id, c.code, c.fullname, c.birthday, c.phone, c.email, c.gender, c.status, c.idel";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<CustomerAdminDTO> customers = new ArrayList<>();

            for (Object[] row : resultList) {
                CustomerAdminDTO customer = new CustomerAdminDTO();

                customer.setId(Long.parseLong(row[0].toString()));
                customer.setCode(row[1].toString());
                customer.setFullname(row[2].toString());
                customer.setPhone(row[4].toString());
                customer.setEmail(row[5].toString());
                customer.setGender(row[6].toString());
                customer.setStatus(Integer.valueOf(row[7].toString()));
                customer.setIdel(Integer.valueOf(row[8].toString()));
                customer.setOrderCount(Integer.valueOf(row[9].toString()));

                try {
                    java.sql.Date birthdayDate = (java.sql.Date) row[3];
                    LocalDate birthday = birthdayDate.toLocalDate();
                    customer.setBirthday(birthday);
                } catch (DateTimeParseException e) {
                    e.printStackTrace();
                    continue;
                }

                customers.add(customer);
            }
            return customers;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
