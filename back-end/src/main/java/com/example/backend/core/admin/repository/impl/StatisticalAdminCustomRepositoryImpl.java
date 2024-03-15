package com.example.backend.core.admin.repository.impl;


import com.example.backend.core.admin.dto.StatisticalAdminDTO;
import com.example.backend.core.admin.dto.TotalStatisticalAdminDTO;
import com.example.backend.core.admin.repository.StatisticalAdminCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class StatisticalAdminCustomRepositoryImpl implements StatisticalAdminCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<StatisticalAdminDTO> getStatisticalByDate(StatisticalAdminDTO statisticalAdminDTO) {
        List<StatisticalAdminDTO> lst = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("WITH RECURSIVE DateRange AS (\n" +
                    "    SELECT :dateFrom AS DateValue\n" +
                    "    UNION ALL\n" +
                    "    SELECT DATE_ADD(DateValue, INTERVAL 1 DAY)\n" +
                    "    FROM DateRange\n" +
                    "    WHERE DateValue < :dateTo\n" +
                    ")\n");
            sql.append("  SELECT \n" +
                    "    DateRange.DateValue,\n" +
                    "    COALESCE(COUNT(DISTINCT o.id), 0) AS totalQuantity,\n" +
                    "    coalesce(SUM(DISTINCT o.total_payment), 0.00) AS TotalPayment,  COALESCE(SUM(od.quantity), 0) AS TotalQuantity  \n" +
                    "FROM DateRange  ");
            sql.append("  LEFT JOIN `order` o ON DATE(o.payment_date) = DateRange.DateValue and o.status = 3\n" +
                    "LEFT JOIN order_detail od ON o.id = od.id_order  GROUP BY DateRange.DateValue\n" +
                    "ORDER BY DateRange.DateValue; ");
            Query query =entityManager.createNativeQuery(sql.toString());
            query.setParameter("dateFrom", statisticalAdminDTO.getDateFrom());
            query.setParameter("dateTo", statisticalAdminDTO.getDateTo());
            List<Object[]> obj = query.getResultList();
            for (Object[] o: obj) {
                StatisticalAdminDTO dto = new StatisticalAdminDTO();
                dto.setDateStr((String) o[0]);
                dto.setQuantityOrder((Long) o[1]);
                dto.setRevenue((BigDecimal) o[2]);
                dto.setQuantityProduct(((BigDecimal) o[3]).longValue());
                lst.add(dto);
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return lst;
    }

    @Override
    public TotalStatisticalAdminDTO totalStatistical() {
        TotalStatisticalAdminDTO dto = new TotalStatisticalAdminDTO();
        LocalDate currentDate = LocalDate.now();
        // Lấy tháng và năm từ ngày hiện tại
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT\n" +
                    "    COALESCE(COUNT(DISTINCT o.id), 0) AS totalQuantity,\n" +
                    "    COALESCE(SUM(DISTINCT o.total_payment), 0.00) AS totalPayment,\n" +
                    "    COALESCE(SUM(od.quantity), 0) AS totalQuantityProduct\n" +
                    "FROM `order` o\n" +
                    "LEFT JOIN order_detail od ON o.id = od.id_order\n" +
                    "WHERE o.status = 3 AND YEAR(o.payment_date) = YEAR(NOW()) AND MONTH(o.payment_date) = :month");
            StringBuilder sql2 = new StringBuilder();
            sql2.append("SELECT COALESCE(COUNT(o.id), 0) as totalQuantity,  coalesce(SUM(o.total_payment), 0.00) AS totalPayment\n" +
                    "FROM `order` o\n" +
                    "WHERE o.status = 3 AND  DATE(o.payment_date) = CURDATE();");
            Query query = entityManager.createNativeQuery(sql.toString());
            Query query2 = entityManager.createNativeQuery(sql2.toString());
            query.setParameter("month", currentMonth);
            List<Object[]> obj = query.getResultList();
            List<Object[]> obj2 = query2.getResultList();
            dto.setTotalOrder((Long) obj.get(0)[0]);
            dto.setTotalRevenue((BigDecimal) obj.get(0)[1]);
            dto.setTotalQuantityProduct(((BigDecimal) obj.get(0)[2]).longValue());
            dto.setTotalOrderToday((Long) obj2.get(0)[0]);
            dto.setTotalRevenueToday((BigDecimal) obj2.get(0)[1]);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return dto;
    }
}
