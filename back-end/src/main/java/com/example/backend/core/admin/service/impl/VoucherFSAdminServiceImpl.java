package com.example.backend.core.admin.service.impl;
import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.admin.mapper.CustomerAdminMapper;
import com.example.backend.core.admin.repository.CustomerAdminRepository;
import com.example.backend.core.admin.repository.OrderAdminRepository;
import com.example.backend.core.admin.repository.VoucherFSCustomerRepository;
import com.example.backend.core.admin.repository.VoucherFreeShipAdminRepository;
import com.example.backend.core.admin.service.VoucherFSAdminService;
import com.example.backend.core.commons.*;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Customer;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.Voucher;
import com.example.backend.core.model.VoucherFreeShip;
import com.example.backend.core.view.mapper.VoucherFSMapper;
import jakarta.mail.MessagingException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoucherFSAdminServiceImpl implements VoucherFSAdminService {
    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;
    @Autowired
    private VoucherFreeShipAdminRepository voucherFreeShipAdminRepository;
    @Autowired
    private CustomerAdminRepository  customerAdminRepository;

    @Autowired
    private VoucherFSMapper voucherAdminMapper;
    @Autowired
    private CustomerAdminMapper customerAdminMapper;
    @Autowired
    private VoucherFSCustomerRepository voucherFSCustomerRepository;
    @Autowired
    FileExportUtil fileExportUtil;
    @Autowired
    private OrderAdminRepository orderAdminRepository;


    public VoucherFSAdminServiceImpl(JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }


    public void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException {

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom("xuanntph21397@fpt.edu.vn");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
        };
        javaMailSender.send(preparator);

    }

    @Override
    @Async
    public void sendMessageUsingThymeleafTemplate(VoucherFreeShipDTO voucherAdminDTO) throws MessagingException {

        String id = voucherAdminDTO.getIdCustomer();
        String[] idArray = id.split(",");

        for (String idCustomer : idArray) {
            Context thymeleafContext = new Context();
            try {
                Long customerId = Long.parseLong(idCustomer.trim());
                Optional<Customer> optionalCustomer = customerAdminRepository.findById(customerId);

                if (optionalCustomer.isPresent()) {
                    Customer customer = optionalCustomer.get();
                    thymeleafContext.setVariable("voucher", voucherAdminDTO);
                    String subject = "Voucher xịn 2T Store tặng bạn";
                    String htmlBody = templateEngine.process("sendEmailVoucherFS", thymeleafContext);
                    sendHtmlEmail(customer.getEmail(), subject, htmlBody);
                } else {
                    // Handle the case where customer is not found by id
                    System.out.println("Customer with id " + customerId + " not found.");
                }
            } catch (NumberFormatException e) {
                // Handle the case where id is not a valid number
                System.out.println("Invalid id format: " + idCustomer);
            }
        }
    }

    @Override
    public List<VoucherFreeShipDTO> getAllVouchers() {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getAllVouchers();
        Iterator<VoucherFreeShipDTO> iterator = list.listIterator();
        while (iterator.hasNext()) {
            VoucherFreeShipDTO voucherAdminDTO = iterator.next();
            List<Order> orderList = orderAdminRepository.findByCodeVoucherShip(voucherAdminDTO.getCode());
            if (orderList.size() > 0) {
                voucherAdminDTO.setIsUpdate(1);
            }
        }
        return list;
    }
    @Override
    public List<VoucherFreeShipDTO> getAllKichHoat() {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getAllKichHoat();
        return list;
    }
    @Override
    public List<VoucherFreeShipDTO> getAllKhongKH() {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getAllKhongKH();
        return list;
    }

    @Override
    public List<VoucherFreeShipDTO> getVouchersByTimeRange(String fromDate, String toDate) {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getVouchersByTimeRange(fromDate,toDate);
        return list;
    }
    @Override
    public List<VoucherFreeShipDTO> getVouchersByKeyword(String keyword) {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getVouchersByKeyword(keyword);
        return list;
    }
    @Override
    public List<VoucherFreeShipDTO> getVouchersByCustomer(String searchTerm) {
        List<VoucherFreeShipDTO> list= voucherFSCustomerRepository.getVouchersByCustomer(searchTerm);
        return list;
    }

    @Override
    public ServiceResult<VoucherFreeShipDTO> createVoucher(VoucherFreeShipDTO voucherAdminDTO) {
        ServiceResult<VoucherFreeShipDTO> serviceResult = new ServiceResult<>();
        VoucherFreeShip voucher = voucherAdminMapper.toEntity(voucherAdminDTO);
        List<VoucherFreeShip> voucherList = new ArrayList<>();
        voucher.setCode("VCS" + Instant.now().getEpochSecond());
        voucher.setCreateDate(java.util.Date.from(Instant.now()));
        voucher.setStatus(0);
        voucher.setIdel(0);
        voucher.setDelete(0);
        voucher.setAmountUsed(0);
        voucher.setConditionApply(voucherAdminDTO.getConditionApply());
        voucher.setDescription(voucherAdminDTO.getDescription());
        voucher.setCreateName(voucherAdminDTO.getCreateName());
        voucher.setStartDate(DateUtil.formatDate(voucherAdminDTO.getStartDate()));
        voucher.setEndDate(DateUtil.formatDate(voucherAdminDTO.getEndDate()));
        if (voucherAdminDTO.getOptionCustomer() == 0) {
            voucher.setIdCustomer(null);
        } else {
            StringBuilder customer = new StringBuilder();

            for (int i = 0; i < voucherAdminDTO.getCustomerAdminDTOList().size(); i++) {
                CustomerAdminDTO customerAdminDTO = voucherAdminDTO.getCustomerAdminDTOList().get(i);
                customer.append(customerAdminDTO.getId());
                customer.append(",");
            }

            // Kiểm tra xem có phần tử nào trong danh sách không
            if (customer.length() > 0) {
                // Cắt bỏ dấu phẩy cuối cùng
                customer.setLength(customer.length() - 1);

                // Lặp qua danh sách id và tạo voucher cho mỗi id
                voucher.setIdCustomer((customer.toString()));
                voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());

            } else {
                // Xử lý trường hợp không có customer nào
                voucher.setIdCustomer(null); // hoặc gán giá trị mong muốn khác
            }
        }
        voucherFreeShipAdminRepository.save(voucher);
        serviceResult.setData(voucherAdminDTO);
        serviceResult.setMessage("Thêm thành công!");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<VoucherFreeShipDTO> updateVoucher(Long id, VoucherFreeShipDTO voucherAdminDTO) {
        ServiceResult<VoucherFreeShipDTO> serviceResult = new ServiceResult<>();
        Optional<VoucherFreeShip> voucherOptional = voucherFreeShipAdminRepository.findById(id);

        if (voucherOptional.isPresent()) {
            VoucherFreeShip voucher = voucherOptional.get();

            // Cập nhật các thuộc tính cần thiết dựa trên updatedVoucherAdminDTO
            voucher.setConditionApply(voucherAdminDTO.getConditionApply());
            voucher.setDescription(voucherAdminDTO.getDescription());
            voucher.setCreateName(voucherAdminDTO.getCreateName());
            voucher.setName(voucherAdminDTO.getName());
            voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());
            voucher.setQuantity(voucherAdminDTO.getQuantity());
            voucher.setName(voucherAdminDTO.getName());
            voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());
            voucher.setReducedValue(voucherAdminDTO.getReducedValue());
            voucher.setStartDate(DateUtil.formatDate(voucherAdminDTO.getStartDate()));
            voucher.setEndDate(DateUtil.formatDate(voucherAdminDTO.getEndDate()));
            if (voucherAdminDTO.getOptionCustomer() == 0) {
                voucher.setIdCustomer(null);
            } else {
                StringBuilder customer = new StringBuilder();

                for (int i = 0; i < voucherAdminDTO.getCustomerAdminDTOList().size(); i++) {
                    CustomerAdminDTO customerAdminDTO = voucherAdminDTO.getCustomerAdminDTOList().get(i);
                    customer.append(customerAdminDTO.getId());
                    customer.append(",");
                }

                // Kiểm tra xem có phần tử nào trong danh sách không
                if (customer.length() > 0) {
                    // Cắt bỏ dấu phẩy cuối cùng
                    customer.setLength(customer.length() - 1);

                    // Lặp qua danh sách id và tạo voucher cho mỗi id
                    voucher.setIdCustomer((customer.toString()));
                    voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());

                } else {
                    // Xử lý trường hợp không có customer nào
                    voucher.setIdCustomer(null); // hoặc gán giá trị mong muốn khác
                }
            }
            voucherFreeShipAdminRepository.save(voucher);
            serviceResult.setData(voucherAdminDTO);
            serviceResult.setMessage("Cập nhật thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setData(null);
            serviceResult.setMessage("Không tìm thấy Voucher cần cập nhật");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }
    @Override
    public ServiceResult<VoucherFreeShipDTO> setIdel(Long idVoucher) {
        ServiceResult<VoucherFreeShipDTO> serviceResult = new ServiceResult<>();
        Optional<VoucherFreeShip> optionalVoucher = voucherFreeShipAdminRepository.findById(idVoucher);

        if (optionalVoucher.isPresent()) {
            VoucherFreeShip voucher = optionalVoucher.get();
            voucher.setIdel(0);
            voucher =  voucherFreeShipAdminRepository.save(voucher);
            VoucherFreeShipDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);
            serviceResult.setData(voucherAdminDTO);
            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setMessage("Thành công");// Lưu lại thay đổi vào cơ sở dữ liệu

        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
            serviceResult.setData(null);
        }

        return serviceResult;
    }



    @Override
    public ServiceResult<Void> deleteVoucher(Long voucherId) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        Optional<VoucherFreeShip> voucher = voucherFreeShipAdminRepository.findById(voucherId);

        if (voucher.isPresent()) {
            VoucherFreeShip voucher1 = voucher.get();
            voucher1.setDelete(1); // Sửa thành setIdel(1) để đánh dấu đã xóa
            voucherFreeShipAdminRepository.save(voucher1); // Lưu lại thay đổi vào cơ sở dữ liệu

            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }
//
    @Override
    public VoucherFreeShipDTO getDetailVoucher(Long id) {

        VoucherFreeShip voucher = voucherFreeShipAdminRepository.findById(id).get();

        VoucherFreeShipDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);

        String idCustomer = voucherAdminDTO.getIdCustomer();

        List<CustomerAdminDTO> toList = new ArrayList<>();

        if(StringUtils.isNotBlank(idCustomer)){
            String[] idArray = idCustomer.split(",");
            for (String idCustomer1 : idArray) {
                try {
                    Long customerId = Long.parseLong(idCustomer1.trim());
                    Optional<Customer> optionalCustomer = customerAdminRepository.findById(customerId);

                    if (optionalCustomer.isPresent()) {
                        Customer customer = optionalCustomer.get();
                        CustomerAdminDTO customerAdminDTO = customerAdminMapper.toDto(customer);

                        // Kiểm tra và thêm vào danh sách nếu không phải là null
                        if (customerAdminDTO != null) {
                            toList.add(customerAdminDTO);
                        }
                    } else {
                        System.out.println("Lỗi");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi");
                }
            }
        }
        voucherAdminDTO.setCustomerAdminDTOList(toList.isEmpty() ? null : toList);

        return voucherAdminDTO;
    }



    @Override
    public List<CustomerAdminDTO> getAllCustomer() {
        List<CustomerAdminDTO> list= voucherFSCustomerRepository.getAllCustomer();
        return list;
    }
    @Override
    public ServiceResult<VoucherFreeShipDTO> KichHoat(Long idVoucher) {
        ServiceResult<VoucherFreeShipDTO> serviceResult = new ServiceResult<>();
        Optional<VoucherFreeShip> optionalVoucher = voucherFreeShipAdminRepository.findById(idVoucher);

        if (optionalVoucher.isPresent()) {
            VoucherFreeShip voucher = optionalVoucher.get();

            voucher.setIdel(voucher.getIdel() == 1 ? 0 : 1);
            voucher =  voucherFreeShipAdminRepository.save(voucher);
            VoucherFreeShipDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);
            serviceResult.setData(voucherAdminDTO);
            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setMessage("Thành công");
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }

    @Override
    public byte[] exportExcelVoucher() throws IOException {
        List<SheetConfigDTO> sheetConfigList = new ArrayList<>();
        List<VoucherFreeShipDTO> voucherAdminDTOS = voucherFSCustomerRepository.getAllVoucherFSsExport();
        sheetConfigList = getDataForExcel("Danh Sách Voucher FreeShip", voucherAdminDTOS, sheetConfigList, AppConstant.EXPORT_DATA);
        try {
            String title = "DANH SÁCH VOUCHER FREESHIP ";
            return fileExportUtil.exportXLSX(false, sheetConfigList, title);
        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ioE) {
            throw new IOException("Lỗi Export" + ioE.getMessage(), ioE);
        }
    }

    private List<SheetConfigDTO> getDataForExcel(String sheetName,
                                                 List<VoucherFreeShipDTO> listDataSheet,
                                                 List<SheetConfigDTO> sheetConfigList,
                                                 Long exportType) {
        SheetConfigDTO sheetConfig = new SheetConfigDTO();
        String[] headerArr = null;
        if (AppConstant.EXPORT_DATA.equals(exportType)) {
            headerArr =
                    new String[]{
                            "STT",
                            "Mã Voucher",
                            "Tên Voucher",
                            "Ngày Bắt Đầu",
                            "Ngày Kết Thúc",
                            "Điều kiện áp dụng",
                            "Giá Trị Giảm",
                            "Số lượng",
                            "Giới hạn sử dụng với mỗi khách hàng",
                            "Trạng thái",
                            "Tên khách hàng",
                    };
        }
        sheetConfig.setSheetName(sheetName);
        sheetConfig.setHeaders(headerArr);
        int recordNo = 1;
        List<CellConfigDTO> cellConfigCustomList = new ArrayList<>();
            for (VoucherFreeShipDTO item : listDataSheet) {
                item.setRecordNo(recordNo++);
            }
        List<CellConfigDTO> cellConfigList = new ArrayList<>();
        sheetConfig.setList(listDataSheet);
        cellConfigList.add(new CellConfigDTO("recordNo", AppConstant.ALIGN_LEFT, AppConstant.NO));
        cellConfigList.add(new CellConfigDTO("code", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("name", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("startDate", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("endDate", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("conditionApply", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("reducedValue", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        cellConfigList.add(new CellConfigDTO("quantity", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
        cellConfigList.add(new CellConfigDTO("limitCustomer", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
//        cellConfigList.add(new CellConfigDTO("allow", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
        cellConfigList.add(new CellConfigDTO("status", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
        cellConfigList.add(new CellConfigDTO("listCodeCustomerExport", AppConstant.ALIGN_LEFT, AppConstant.STRING));
        if (AppConstant.EXPORT_DATA.equals(exportType) || AppConstant.EXPORT_ERRORS.equals(exportType)) {
            cellConfigList.add(new CellConfigDTO("messageStr", AppConstant.ALIGN_LEFT, AppConstant.ERRORS));
        }
        sheetConfig.setHasIndex(false);
        sheetConfig.setHasBorder(true);
        sheetConfig.setExportType(exportType.intValue());
        sheetConfig.setCellConfigList(cellConfigList);
        sheetConfig.setCellCustomList(cellConfigCustomList);
        sheetConfigList.add(sheetConfig);
        return sheetConfigList;
    }
    public List<String> getAllVoucherExport() {
        List<String> lstStr = voucherFreeShipAdminRepository.findAll()
                .stream()
                .map(b -> b.getId() + "-" + b.getName())
                .collect(Collectors.toList());
        return lstStr;
    }
}
