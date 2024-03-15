package com.example.backend.core.admin.repository.impl;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.repository.CustomerAdminRepository;
import com.example.backend.core.admin.repository.VoucherAdminCustomRepository;
import com.example.backend.core.admin.repository.VoucherAdminRepository;
import com.example.backend.core.model.Customer;
import jakarta.persistence.EntityManager;
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
public class VoucherAdminCustomRepositoryImpl implements VoucherAdminCustomRepository {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private CustomerAdminRepository customerAdminRepository;

    @Override
    public List<VoucherAdminDTO> getAllVouchers() {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.voucher_type, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.max_reduced," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher v " +
                    "LEFT JOIN `order` o ON o.code_voucher = v.code " +
                    "where v.dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
                    "v.voucher_type, v.reduced_value, v.description, v.idel, v.quantity,v.max_reduced,v.allow ";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherAdminDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherAdminDTO voucher = new VoucherAdminDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setVoucherType(Integer.valueOf(row[6].toString()));
                voucher.setReducedValue(new BigDecimal(row[7].toString()));
                voucher.setDescription(row[8].toString());
                voucher.setIdel(Integer.valueOf(row[9].toString()));
                voucher.setQuantity(Integer.valueOf(row[10].toString()));
                voucher.setMaxReduced(row[11] != null ? new BigDecimal(row[11].toString()) : null);
                voucher.setAllow(row[12] != null ? Integer.valueOf((row[12].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[13].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
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
    public List<VoucherAdminDTO> getAllVouchersExport() {
        try {
            String sql = "SELECT v.* \n" +
                    "FROM voucher v\n" +
                    " where v.dele=0;";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();
            List<CustomerAdminDTO> customerAdminDTOList=new ArrayList<>();
            List<VoucherAdminDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherAdminDTO voucher = new VoucherAdminDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setIdCustomer((String) row[3]);
                voucher.setConditionApply(new BigDecimal(row[7].toString()));
                voucher.setReducedValue(new BigDecimal(row[10].toString()));
                voucher.setQuantity(Integer.valueOf(row[15].toString()));
                voucher.setLimitCustomer(row[20] != null ? Integer.valueOf((row[20].toString())) : null);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[5].toString());
                    java.util.Date endDate = dateFormat.parse(row[6].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                    continue;
                }
                if(StringUtils.isNotBlank(voucher.getIdCustomer()) || null != voucher.getIdCustomer()){
                    String listCodeCustomer = "";
                    for (String str: voucher.getIdCustomer().split(",")) {
                        Customer customer = customerAdminRepository.findById(Long.parseLong(str)).orElse(null);
                        if(customer != null){
                            listCodeCustomer += customer.getCode() + ",";
                        }
                    }
                    voucher.setListCodeCustomerExport(listCodeCustomer);
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
    public List<VoucherAdminDTO> getAllKichHoat() {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.voucher_type, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.max_reduced," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher v " +
                    "LEFT JOIN `order` o ON o.code_voucher = v.code " +
                    "where v.idel = 1 and v.dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
                   "v.voucher_type, v.reduced_value, v.description, v.idel, v.quantity,v.max_reduced,v.allow ";

            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherAdminDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherAdminDTO voucher = new VoucherAdminDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setVoucherType(Integer.valueOf(row[6].toString()));
                voucher.setReducedValue(new BigDecimal(row[7].toString()));
                voucher.setDescription(row[8].toString());
                voucher.setIdel(Integer.valueOf(row[9].toString()));
                voucher.setQuantity(Integer.valueOf(row[10].toString()));
                voucher.setMaxReduced(row[11] != null ? new BigDecimal(row[11].toString()) : null);
                voucher.setAllow(row[12] != null ? Integer.valueOf((row[12].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[13].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
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
    public List<VoucherAdminDTO> getAllKhongKH() {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.voucher_type, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.max_reduced," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher v " +
                    "LEFT JOIN `order` o ON o.code_voucher = v.code " +
                    "where idel=0 and dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
                    "v.voucher_type, v.reduced_value, v.description, v.idel, v.quantity,v.max_reduced,v.allow ";
            Query query = entityManager.createNativeQuery(sql);
            List<Object[]> resultList = query.getResultList();

            List<VoucherAdminDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherAdminDTO voucher = new VoucherAdminDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setVoucherType(Integer.valueOf(row[6].toString()));
                voucher.setReducedValue(new BigDecimal(row[7].toString()));
                voucher.setDescription(row[8].toString());
                voucher.setIdel(Integer.valueOf(row[9].toString()));
                voucher.setQuantity(Integer.valueOf(row[10].toString()));
                voucher.setMaxReduced(row[11] != null ? new BigDecimal(row[11].toString()) : null);
                voucher.setAllow(row[12] != null ? Integer.valueOf((row[12].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[13].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
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
    public List<VoucherAdminDTO> getVouchersByTimeRange(String fromDate, String toDate) {
        try {
            StringBuilder sql =new StringBuilder(
                    "SELECT " +
                            "  v.id, " +
                            "  v.code, " +
                            "  v.name, " +
                            "  v.start_date," +
                            "  v.end_date, " +
                            "  v.conditions, " +
                            "  v.voucher_type, " +
                            "  v.reduced_value, " +
                            "  v.description, " +
                            "  v.idel, " +
                            "  v.quantity," +
                            "v.max_reduced," +
                            "v.allow ," +
                            "  COUNT(o.id) AS use_voucher " +
                            "FROM voucher v " +
                            "LEFT JOIN `order` o ON o.code_voucher = v.code " +
                            "where  dele=0 "
            );

            if(StringUtils.isNotBlank(fromDate)){
                sql.append("and  (:dateFrom is null or STR_TO_DATE(DATE_FORMAT(v.start_date, '%Y/%m/%d'), '%Y/%m/%d') >= STR_TO_DATE(:dateFrom , '%d/%m/%Y')) ");
            }
            if (StringUtils.isNotBlank(toDate)){
                sql.append("  and (:dateTo is null or STR_TO_DATE(DATE_FORMAT(v.start_date, '%Y/%m/%d'), '%Y/%m/%d') <= STR_TO_DATE(:dateTo , '%d/%m/%Y'))  ");
            }
            sql.append(" GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
                    "v.voucher_type, v.reduced_value, v.description, v.idel, v.quantity,v.max_reduced,v.allow ");
            Query query = entityManager.createNativeQuery(sql.toString());
            if (StringUtils.isNotBlank(fromDate)){
                query.setParameter("dateFrom", fromDate);
            }
            if (StringUtils.isNotBlank(toDate)) {
                query.setParameter("dateTo",toDate);
            }

            List<Object[]> resultList = query.getResultList();

            List<VoucherAdminDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherAdminDTO voucher = new VoucherAdminDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setVoucherType(Integer.valueOf(row[6].toString()));
                voucher.setReducedValue(new BigDecimal(row[7].toString()));
                voucher.setDescription(row[8].toString());
                voucher.setIdel(Integer.valueOf(row[9].toString()));
                voucher.setQuantity(Integer.valueOf(row[10].toString()));
                voucher.setMaxReduced(row[11] != null ? new BigDecimal(row[11].toString()) : null);
                voucher.setAllow(row[12] != null ? Integer.valueOf((row[12].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[13].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
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
    public List<VoucherAdminDTO> getVouchersByKeyword(String keyword) {
        try {
            String sql = "SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.voucher_type, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.max_reduced," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher v " +
                    "LEFT JOIN `order` o ON o.code_voucher = v.code " +
                    "WHERE v.name LIKE :keyword OR v.code LIKE :keyword and dele=0 " +
                    "GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, " +
                    "v.voucher_type, v.reduced_value, v.description, v.idel, v.quantity,v.max_reduced,v.allow ";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("keyword", "%" + keyword + "%"); // Sử dụng % để tìm kiếm mọi nơi trong chuỗi.

            List<Object[]> resultList = query.getResultList();

            List<VoucherAdminDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherAdminDTO voucher = new VoucherAdminDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setVoucherType(Integer.valueOf(row[6].toString()));
                voucher.setReducedValue(new BigDecimal(row[7].toString()));
                voucher.setDescription(row[8].toString());
                voucher.setIdel(Integer.valueOf(row[9].toString()));
                voucher.setQuantity(Integer.valueOf(row[10].toString()));
                voucher.setMaxReduced(row[11] != null ? new BigDecimal(row[11].toString()) : null);
                voucher.setAllow(row[12] != null ? Integer.valueOf((row[12].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[13].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
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
    public List<VoucherAdminDTO> getVouchersByCustomer(String searchTerm) {
        try {
            StringBuilder sql = new StringBuilder("SELECT " +
                    "  v.id, " +
                    "  v.code, " +
                    "  v.name, " +
                    "  v.start_date," +
                    "  v.end_date, " +
                    "  v.conditions, " +
                    "  v.voucher_type, " +
                    "  v.reduced_value, " +
                    "  v.description, " +
                    "  v.idel, " +
                    "  v.quantity," +
                    "v.max_reduced," +
                    "v.allow ," +
                    "  COUNT(o.id) AS use_voucher " +
                    "FROM voucher v " +
                    "LEFT JOIN `order` o ON o.code_voucher = v.code " +
                    "LEFT JOIN customer c ON v.id_customer = c.id " +
                    "WHERE dele=0 ");
            if (searchTerm != null && !searchTerm.isEmpty()) {
                sql.append(" and LOWER(c.code) LIKE LOWER(:searchTerm) " +
                        "   OR LOWER(c.fullname) LIKE LOWER(:searchTerm) " +
                        "   OR c.phone LIKE  :searchTerm ");
            }
            sql.append(" GROUP BY v.id, v.code, v.name, v.start_date, v.end_date, v.conditions, v.voucher_type, v.reduced_value, v.description, v.idel, v.quantity,v.max_reduced,v.allow " );

            Query query = entityManager.createNativeQuery(sql.toString());

            if (searchTerm != null && !searchTerm.isEmpty()) {
                query.setParameter("searchTerm", "%" + searchTerm + "%");
            }
            List<Object[]> resultList = query.getResultList();

            List<VoucherAdminDTO> vouchers = new ArrayList<>();
            for (Object[] row : resultList) {
                VoucherAdminDTO voucher = new VoucherAdminDTO();

                voucher.setId(Long.parseLong(row[0].toString()));
                voucher.setCode(row[1].toString());
                voucher.setName(row[2].toString());
                voucher.setConditionApply(new BigDecimal(row[5].toString()));
                voucher.setVoucherType(Integer.valueOf(row[6].toString()));
                voucher.setReducedValue(new BigDecimal(row[7].toString()));
                voucher.setDescription(row[8].toString());
                voucher.setIdel(Integer.valueOf(row[9].toString()));
                voucher.setQuantity(Integer.valueOf(row[10].toString()));
                voucher.setMaxReduced(row[11] != null ? new BigDecimal(row[11].toString()) : null);
                voucher.setAllow(row[12] != null ? Integer.valueOf((row[12].toString())) : null);
                voucher.setUseVoucher(Integer.parseInt(row[13].toString()));

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date startDate = dateFormat.parse(row[3].toString());
                    java.util.Date endDate = dateFormat.parse(row[4].toString());

                    voucher.setStartDate(startDate);
                    voucher.setEndDate(endDate);

                    if (new Date(System.currentTimeMillis()).after(endDate)) {
                        voucher.setStatus(1);
                    } else {
                        voucher.setStatus(0);
                    }

                } catch (ParseException e) {
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
                    "  ifnull(c.status, 0), " +
                    "  ifnull(c.idel,0), " +
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

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                try {
                    java.util.Date birthday = dateFormat.parse(row[3].toString());

                    customer.setBirthday(birthday);

                } catch (ParseException e) {
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
