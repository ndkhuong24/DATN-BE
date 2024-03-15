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
            sql.append("select * from `order` o  ");
            sql.append("where o.id_customer = :idCustomer and type = 0  ");
            if (orderDTO.getStatus() != 6 && orderDTO.getStatus() != null) {
                sql.append("  and o.status = :status  ");
            }
            if (StringUtils.isNotBlank(orderDTO.getCode())) {
                sql.append("  and (UPPER(o.code) like concat('%', UPPER(:codeSearch), '%'))");
            }
            if (StringUtils.isNotBlank(orderDTO.getDateFrom())) {
                sql.append("  and (:dateFrom is null or STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') >= STR_TO_DATE(:dateFrom , '%d/%m/%Y'))  ");
            }
            if (StringUtils.isNotBlank(orderDTO.getDateTo())) {
                sql.append("  and (:dateTo is null or STR_TO_DATE(DATE_FORMAT(o.create_date, '%Y/%m/%d'), '%Y/%m/%d') <= STR_TO_DATE(:dateTo , '%d/%m/%Y')) ");
            }
            sql.append("   order by o.create_date desc");
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
                dto.setCode((String) obj[1]);
                dto.setIdCustomer(obj[2] != null ? ((Number) obj[2]).longValue() : null);
                dto.setIdStaff(obj[3] != null ? ((Number) obj[3]).longValue() : null);
                dto.setCodeVoucher((String) obj[4]);
                dto.setCodeVoucherShip((String) obj[5]);
                Timestamp createTimestamp = (Timestamp) obj[6];
                dto.setCreateDate(createTimestamp != null ? createTimestamp.toInstant() : null);
                Timestamp paymentTimestamp = (Timestamp) obj[7];
                dto.setPaymentDate(paymentTimestamp != null ? paymentTimestamp.toInstant() : null);
                dto.setDeliveryDate((Date) obj[8]);
                dto.setReceivedDate((Date) obj[9]);
                dto.setAddressReceived((String) obj[10]);
                dto.setShipperPhone((String) obj[11]);
                dto.setReceiverPhone((String) obj[12]);
                dto.setReceiver((String) obj[13]);
                dto.setShipPrice((BigDecimal) obj[14]);
                dto.setTotalPrice((BigDecimal) obj[15]);
                dto.setTotalPayment((BigDecimal) obj[16]);
                dto.setType( obj[17] != null ? ((Number) obj[17]).intValue() : null);
                dto.setPaymentType((Integer) obj[18]);
                dto.setDescription((String) obj[19]);
                dto.setMissedOrder((Integer) obj[20]);
                dto.setStatus((Integer) obj[21]);
                dto.setStatusPayment((Integer) obj[22]);
                dto.setEmail((String) obj[23]);
                lstOrderDTOS.add(dto);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return lstOrderDTOS;
    }
}
