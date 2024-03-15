package com.example.backend.core.salesCounter.repository.impl;

import com.example.backend.core.admin.dto.OrderAdminDTO;
import com.example.backend.core.salesCounter.repository.OrderSalesCountRepository;
import com.example.backend.core.salesCounter.repository.OrderSalesCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class OrderSalesCustomimpl implements OrderSalesCustomRepository {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<OrderAdminDTO> getAllOrderAdmin(OrderAdminDTO orderAdminDTO) {
        List<OrderAdminDTO> lstOrderAdminDTOS = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("select * from `order` o where true ");
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
                OrderAdminDTO dto = new OrderAdminDTO();
                dto.setId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
                dto.setCode((String) obj[1]);
                dto.setIdCustomer(obj[2] != null ? ((Number) obj[2]).longValue() : null);
                dto.setIdStaff(obj[3] != null ? ((Number) obj[3]).longValue() : null);
                dto.setCodeVoucher((String) obj[4]);
                Timestamp createTimestamp = (Timestamp) obj[5];
                dto.setCreateDate(createTimestamp != null ? createTimestamp.toInstant() : null);
                Timestamp paymentTimestamp = (Timestamp) obj[6];
                dto.setPaymentDate(paymentTimestamp != null ? paymentTimestamp.toInstant() : null);
                dto.setDeliveryDate((Date) obj[7]);
                dto.setReceivedDate((Date) obj[8]);
                dto.setAddressReceived((String) obj[9]);
                dto.setShipperPhone((String) obj[10]);
                dto.setReceiverPhone((String) obj[11]);
                dto.setReceiver((String) obj[12]);
                dto.setShipPrice((BigDecimal) obj[13]);
                dto.setTotalPrice((BigDecimal) obj[14]);
                dto.setTotalPayment((BigDecimal) obj[15]);
                dto.setPaymentType((Integer) obj[16]);
                dto.setDescription((String) obj[17]);
                dto.setMissedOrder((Integer) obj[18]);
                dto.setStatus((Integer) obj[19]);
                dto.setStatusPayment((Integer) obj[20]);
                dto.setType((Integer) obj[21]);
                lstOrderAdminDTOS.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lstOrderAdminDTOS;
    }
}
