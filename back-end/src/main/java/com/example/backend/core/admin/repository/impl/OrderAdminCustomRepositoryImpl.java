package com.example.backend.core.admin.repository.impl;

import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.admin.repository.OrderAdminCustomerRepository;
import com.example.backend.core.view.dto.OrderDTO;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Transactional
public class OrderAdminCustomRepositoryImpl implements OrderAdminCustomerRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<OrderAdminDTO> getAllOrderAdmin(OrderAdminDTO orderAdminDTO) {
        List<OrderAdminDTO> orderAdminDTOList = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder();

            sql.append("select * from `order` o where true and type = 0");

            if (orderAdminDTO.getStatus() != 6 && orderAdminDTO.getStatus() != null) {
                sql.append("  and o.status = :status  ");
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getCode())) {
                sql.append("  and (UPPER(o.code) like concat('%', UPPER(:codeSearch), '%'))");
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateFrom())) {
                sql.append("  and (:dateFrom is null or STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') >= STR_TO_DATE(:dateFrom , '%d/%m/%Y'))  ");
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateTo())) {
                sql.append("  and (:dateTo is null or STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') <= STR_TO_DATE(:dateTo , '%d/%m/%Y')) ");
            }
            sql.append("   order by o.create_date desc");

            Query query = entityManager.createNativeQuery(sql.toString());

            if (orderAdminDTO.getStatus() != 6 && orderAdminDTO.getStatus() != null) {
                query.setParameter("status", orderAdminDTO.getStatus());
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getCode())) {
                query.setParameter("codeSearch", orderAdminDTO.getCode());
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateFrom())) {
                query.setParameter("dateFrom", orderAdminDTO.getDateFrom());
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateTo())) {
                query.setParameter("dateTo", orderAdminDTO.getDateTo());
            }

            List<Object[]> lst = query.getResultList();

            for (Object[] obj: lst) {

                OrderAdminDTO orderAdminDTO1 = new OrderAdminDTO();

                orderAdminDTO1.setId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
                orderAdminDTO1.setCode((String) obj[2]);
                orderAdminDTO1.setIdCustomer(obj[9] != null ? ((Number) obj[9]).longValue() : null);
                orderAdminDTO1.setIdStaff(obj[10] != null ? ((Number) obj[10]).longValue() : null);
                orderAdminDTO1.setCodeVoucher((String) obj[3]);
                orderAdminDTO1.setCodeVoucherShip((String) obj[4]);

                Timestamp createTimestamp = (Timestamp) obj[5];
                orderAdminDTO1.setCreateDate(createTimestamp != null ? LocalDateTime.now() : null);

                Timestamp paymentTimestamp = (Timestamp) obj[12];
                orderAdminDTO1.setPaymentDate(paymentTimestamp != null ? LocalDateTime.now() : null);

                orderAdminDTO1.setDeliveryDate((LocalDateTime) obj[6]);
                orderAdminDTO1.setReceivedDate((LocalDateTime) obj[14]);
                orderAdminDTO1.setAddressReceived((String) obj[1]);
                orderAdminDTO1.setShipperPhone((String) obj[18]);
                orderAdminDTO1.setReceiverPhone((String) obj[16]);
                orderAdminDTO1.setReceiver((String) obj[15]);
                orderAdminDTO1.setShipPrice((BigDecimal) obj[17]);
                orderAdminDTO1.setTotalPrice((BigDecimal) obj[21]);
                orderAdminDTO1.setTotalPayment((BigDecimal) obj[21]);
                orderAdminDTO1.setType( obj[23] != null ? ((Number) obj[23]).intValue() : null);
                orderAdminDTO1.setPaymentType((Integer) obj[13]);
                orderAdminDTO1.setDescription((String) obj[8]);
                orderAdminDTO1.setMissedOrder((Integer) obj[11]);
                orderAdminDTO1.setStatus((Integer) obj[19]);
                orderAdminDTO1.setStatusPayment((Integer) obj[20]);
                orderAdminDTO1.setEmail((String) obj[8]);
                orderAdminDTOList.add(orderAdminDTO1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return orderAdminDTOList;
    }

    @Override
    public Map<String, Integer> totalStatusOrder(OrderAdminDTO orderAdminDTO) {
        Map<String, Integer> map = new HashMap<>();
        try {
            StringBuilder sql = new StringBuilder("");
            sql.append("SELECT\n" +
                    "  SUM(CASE WHEN o.status = 0 AND o.type = 0 THEN 1 ELSE 0 END) AS tongDonHangXacNhan,\n" +
                    "  SUM(CASE WHEN o.status = 1 AND o.type = 0 THEN 1 ELSE 0 END) AS tongDonHangXuLy,\n" +
                    "  SUM(CASE WHEN o.status = 2 AND o.type = 0 THEN 1 ELSE 0 END) AS tongDonHangGiaoHang,\n" +
                    "  SUM(CASE WHEN o.status = 3 AND o.type = 0 THEN 1 ELSE 0 END) AS tongDonHangHoanThanh,\n" +
                    "  SUM(CASE WHEN o.status = 4 AND o.type = 0 THEN 1 ELSE 0 END) AS tongDonHangHuy\n" +
                    "FROM `order` AS o\n" +
                    "WHERE o.type = 0  ");
            if (StringUtils.isNotBlank(orderAdminDTO.getDateFrom())) {
                sql.append("  and (:dateFrom is null or STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') >= STR_TO_DATE(:dateFrom , '%d/%m/%Y'))  ");
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateTo())) {
                sql.append("  and (:dateTo is null or STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') <= STR_TO_DATE(:dateTo , '%d/%m/%Y')) ");
            }
            Query query = entityManager.createNativeQuery(sql.toString());
            if (StringUtils.isNotBlank(orderAdminDTO.getDateFrom())) {
                query.setParameter("dateFrom", orderAdminDTO.getDateFrom());
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateTo())) {
                query.setParameter("dateTo", orderAdminDTO.getDateTo());
            }
            List<Object[]> list = query.getResultList();
            for (Object[] obj: list) {
                map.put("xacNhan", ((BigDecimal) obj[0]).intValue());
                map.put("xuLy", ((BigDecimal) obj[1]).intValue());
                map.put("giaoHang", ((BigDecimal) obj[2]).intValue());
                map.put("hoanThanh", ((BigDecimal) obj[3]).intValue());
                map.put("huy", ((BigDecimal) obj[4]).intValue());
            }
            return map;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<OrderAdminDTO> getAllOrderSalesAdmin(OrderAdminDTO orderAdminDTO) {
        List<OrderAdminDTO> orderAdminDTOList = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from `order` o where true and type = 1");
            if (orderAdminDTO.getStatus() != 6 && orderAdminDTO.getStatus() != null) {
                sql.append("  and o.status = :status  ");
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getCode())) {
                sql.append("  and (UPPER(o.code) like concat('%', UPPER(:codeSearch), '%'))");
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateFrom())) {
                sql.append("  and (:dateFrom is null or STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') >= STR_TO_DATE(:dateFrom , '%d/%m/%Y'))  ");
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateTo())) {
                sql.append("  and (:dateTo is null or STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') <= STR_TO_DATE(:dateTo , '%d/%m/%Y')) ");
            }
            sql.append("   order by o.create_date desc");
            Query query = entityManager.createNativeQuery(sql.toString());
            if (orderAdminDTO.getStatus() != 6 && orderAdminDTO.getStatus() != null) {
                query.setParameter("status", orderAdminDTO.getStatus());
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getCode())) {
                query.setParameter("codeSearch", orderAdminDTO.getCode());
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateFrom())) {
                query.setParameter("dateFrom", orderAdminDTO.getDateFrom());
            }
            if (StringUtils.isNotBlank(orderAdminDTO.getDateTo())) {
                query.setParameter("dateTo", orderAdminDTO.getDateTo());
            }
            List<Object[]> lst = query.getResultList();
            for (Object[] obj: lst) {
                OrderAdminDTO orderAdminDTO1 = new OrderAdminDTO();

                orderAdminDTO1.setId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
                orderAdminDTO1.setCode((String) obj[2]);
                orderAdminDTO1.setIdCustomer(obj[9] != null ? ((Number) obj[9]).longValue() : null);
                orderAdminDTO1.setIdStaff(obj[10] != null ? ((Number) obj[10]).longValue() : null);
                orderAdminDTO1.setCodeVoucher((String) obj[3]);
                orderAdminDTO1.setCodeVoucherShip((String) obj[4]);

                Timestamp createTimestamp = (Timestamp) obj[5];
                orderAdminDTO1.setCreateDate(createTimestamp != null ? LocalDateTime.now() : null);

                Timestamp paymentTimestamp = (Timestamp) obj[12];
                orderAdminDTO1.setPaymentDate(paymentTimestamp != null ? LocalDateTime.now() : null);

                orderAdminDTO1.setDeliveryDate((LocalDateTime) obj[6]);
                orderAdminDTO1.setReceivedDate((LocalDateTime) obj[14]);
                orderAdminDTO1.setAddressReceived((String) obj[1]);
                orderAdminDTO1.setShipperPhone((String) obj[18]);
                orderAdminDTO1.setReceiverPhone((String) obj[16]);
                orderAdminDTO1.setReceiver((String) obj[15]);
                orderAdminDTO1.setShipPrice((BigDecimal) obj[17]);
                orderAdminDTO1.setTotalPrice((BigDecimal) obj[21]);
                orderAdminDTO1.setTotalPayment((BigDecimal) obj[21]);
                orderAdminDTO1.setType( obj[23] != null ? ((Number) obj[23]).intValue() : null);
                orderAdminDTO1.setPaymentType((Integer) obj[13]);
                orderAdminDTO1.setDescription((String) obj[8]);
                orderAdminDTO1.setMissedOrder((Integer) obj[11]);
                orderAdminDTO1.setStatus((Integer) obj[19]);
                orderAdminDTO1.setStatusPayment((Integer) obj[20]);
                orderAdminDTO1.setEmail((String) obj[8]);
                orderAdminDTOList.add(orderAdminDTO1);
//                OrderAdminDTO dto = new OrderAdminDTO();
//                dto.setId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
//                dto.setCode((String) obj[1]);
//                dto.setIdCustomer(obj[2] != null ? ((Number) obj[2]).longValue() : null);
//                dto.setIdStaff(obj[3] != null ? ((Number) obj[3]).longValue() : null);
//                dto.setCodeVoucher((String) obj[4]);
//                dto.setCodeVoucherShip((String) obj[5]);
//                Timestamp createTimestamp = (Timestamp) obj[6];
//                dto.setCreateDate(createTimestamp != null ? LocalDateTime.now() : null);
//                Timestamp paymentTimestamp = (Timestamp) obj[7];
//                dto.setPaymentDate(paymentTimestamp != null ? LocalDateTime.now() : null);
//                dto.setDeliveryDate((LocalDateTime) obj[8]);
//                dto.setReceivedDate((LocalDateTime) obj[9]);
//                dto.setAddressReceived((String) obj[10]);
//                dto.setShipperPhone((String) obj[11]);
//                dto.setReceiverPhone((String) obj[12]);
//                dto.setReceiver((String) obj[13]);
//                dto.setShipPrice((BigDecimal) obj[14]);
//                dto.setTotalPrice((BigDecimal) obj[15]);
//                dto.setTotalPayment((BigDecimal) obj[16]);
//                dto.setType( obj[17] != null ? ((Number) obj[17]).intValue() : null);
//                dto.setPaymentType((Integer) obj[18]);
//                dto.setDescription((String) obj[19]);
//                dto.setMissedOrder((Integer) obj[20]);
//                dto.setStatus((Integer) obj[21]);
//                dto.setStatusPayment((Integer) obj[22]);
//                dto.setEmail((String) obj[23]);
//                lstOrderAdminDTOS.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return orderAdminDTOList;
    }
}
