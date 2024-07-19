package com.example.backend.core.view.repository.impl;

import com.example.backend.core.view.dto.OrderDTO;
import com.example.backend.core.view.repository.OrderCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class OrderCustomRepositoryImpl implements OrderCustomRepository {
    @Autowired
    private EntityManager entityManager;

    @Override
    public List<OrderDTO> getAllOrderByCustomerSearch(OrderDTO orderDTO) {
        List<OrderDTO> lstOrderDTOS = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT * FROM `order` o ");
            sql.append("WHERE o.id_customer = :idCustomer AND o.type = 0 ");

            if (orderDTO.getStatus() != null && orderDTO.getStatus() != 6) {
                sql.append(" AND o.status = :status ");
            }

            if (StringUtils.isNotBlank(orderDTO.getCode())) {
                sql.append(" AND UPPER(o.code) LIKE CONCAT('%', UPPER(:codeSearch), '%') ");
            }

            if (StringUtils.isNotBlank(orderDTO.getDateFrom())) {
                sql.append(" AND (:dateFrom IS NULL OR STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') >= STR_TO_DATE(:dateFrom, '%d/%m/%Y')) ");
            }

            if (StringUtils.isNotBlank(orderDTO.getDateTo())) {
                sql.append(" AND (:dateTo IS NULL OR STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') <= STR_TO_DATE(:dateTo, '%d/%m/%Y')) ");
            }

            sql.append(" ORDER BY o.create_date DESC ");

            Query query = entityManager.createNativeQuery(sql.toString());
            query.setParameter("idCustomer", orderDTO.getIdCustomer());
            if (orderDTO.getStatus() != 6 && orderDTO.getStatus() != null) {
                query.setParameter("status", orderDTO.getStatus());
            }
            if (StringUtils.isNotBlank(orderDTO.getCode())) {
               query.setParameter("codeSearch", orderDTO.getCode());
            }
            if (StringUtils.isNotBlank(orderDTO.getDateFrom())) {
                query.setParameter("dateFrom", orderDTO.getDateFrom());
            }
            if (StringUtils.isNotBlank(orderDTO.getDateTo())) {
                query.setParameter("dateTo", orderDTO.getDateTo());
            }
            List<Object[]> lst = query.getResultList();
            for (Object[] obj: lst) {
                OrderDTO dto = new OrderDTO();
                dto.setId(obj[0] != null ? ((Number) obj[0]).longValue() : null);
                dto.setCode((String) obj[2]);
                dto.setCodeVoucher((String) obj[3]);
                dto.setCodeVoucherShip((String) obj[4]);
                dto.setIdCustomer(obj[9] != null ? ((Number) obj[9]).longValue() : null);
                dto.setIdStaff(obj[10] != null ? ((Number) obj[10]).longValue() : null);

                Timestamp createTimestamp = (Timestamp) obj[5];
                dto.setCreateDate(createTimestamp != null ? createTimestamp.toLocalDateTime() : null);

                Timestamp paymentTimestamp = (Timestamp) obj[12];
                dto.setPaymentDate(paymentTimestamp != null ? paymentTimestamp.toLocalDateTime() : null);

                Timestamp deliveryTimestamp = (Timestamp) obj[6];
                dto.setDeliveryDate(deliveryTimestamp != null ? deliveryTimestamp.toLocalDateTime() : null);

                Timestamp receivedTimestamp = (Timestamp) obj[14];
                dto.setReceivedDate(receivedTimestamp != null ? receivedTimestamp.toLocalDateTime() : null);

                dto.setAddressReceived((String) obj[1]);
                dto.setShipperPhone((String) obj[18]);
                dto.setReceiverPhone((String) obj[16]);
                dto.setReceiver((String) obj[15]);
                dto.setShipPrice((BigDecimal) obj[17]);
                dto.setTotalPrice((BigDecimal) obj[22]);
                dto.setTotalPayment((BigDecimal) obj[21]);
                dto.setType(obj[23] != null ? ((Number) obj[23]).intValue() : null);
                dto.setPaymentType((Integer) obj[13]);
                dto.setDescription((String) obj[8]);
                dto.setMissedOrder((Integer) obj[11]);
                dto.setStatus((Integer) obj[19]);
                dto.setStatusPayment((Integer) obj[20]);
                dto.setEmail((String) obj[8]);
                lstOrderDTOS.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lstOrderDTOS;
    }
}
