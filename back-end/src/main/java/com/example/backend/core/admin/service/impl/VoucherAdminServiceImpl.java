package com.example.backend.core.admin.service.impl;

import com.example.backend.core.admin.dto.CustomerAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.mapper.CustomerAdminMapper;
import com.example.backend.core.admin.mapper.VoucherAdminMapper;
import com.example.backend.core.admin.repository.CustomerAdminRepository;
import com.example.backend.core.admin.repository.OrderAdminRepository;
import com.example.backend.core.admin.repository.VoucherAdminCustomRepository;
import com.example.backend.core.admin.repository.VoucherAdminRepository;
import com.example.backend.core.admin.service.VoucherAdminService;
import com.example.backend.core.commons.FileExportUtil;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.model.Customer;
import com.example.backend.core.model.Order;
import com.example.backend.core.model.Voucher;
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

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VoucherAdminServiceImpl implements VoucherAdminService {
    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;

    @Autowired
    private VoucherAdminRepository voucherAdminRepository;

    @Autowired
    private CustomerAdminRepository customerAdminRepository;

    @Autowired
    private VoucherAdminMapper voucherAdminMapper;

    @Autowired
    private CustomerAdminMapper customerAdminMapper;

    @Autowired
    FileExportUtil fileExportUtil;

    @Autowired
    private VoucherAdminCustomRepository voucherAdminCustomRepository;

    @Autowired
    private OrderAdminRepository orderAdminRepository;

    public VoucherAdminServiceImpl(JavaMailSender javaMailSender, MessageSource messageSource, SpringTemplateEngine templateEngine) {
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }

    public void sendHtmlEmail(String toEmail, String subject, String htmlBody) throws MessagingException {
        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            helper.setFrom("kn134646@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
        };
        javaMailSender.send(preparator);

    }

    @Override
    @Async
    public void sendMessageUsingThymeleafTemplate(VoucherAdminDTO voucherAdminDTO) throws MessagingException {
        String id = voucherAdminDTO.getIdCustomer();
        if (StringUtils.isNotBlank(id)) {
            String[] idArray = id.split(",");
            for (String idCustomer : idArray) {
                Context thymeleafContext = new Context();
                try {
                    Long customerId = Long.parseLong(idCustomer.trim());
                    Optional<Customer> optionalCustomer = customerAdminRepository.findById(customerId);

                    if (optionalCustomer.isPresent()) {
                        Customer customer = optionalCustomer.get();
                        thymeleafContext.setVariable("voucher", voucherAdminDTO);
                        String subject = "Voucher xịn tặng bạn";
                        String htmlBody = templateEngine.process("sendEmailVoucher", thymeleafContext);
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
    }

//    @Override
//    public List<VoucherAdminDTO> getAllVouchers() {
//        // Lấy danh sách voucher từ repository tùy chỉnh
//        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getAllVouchers();
//
//        // Lọc và sắp xếp danh sách voucher theo ngày tạo (giảm dần)
//        list = list.stream()
//                .filter(voucher -> voucher.getCreateDate() != null)
//                .sorted(Comparator.comparing(VoucherAdminDTO::getCreateDate).reversed())
//                .collect(Collectors.toList());
//
//        // Lặp qua từng voucher trong danh sách
//        Iterator<VoucherAdminDTO> iterator = list.listIterator();
//        while (iterator.hasNext()) {
//            VoucherAdminDTO voucherAdminDTO = iterator.next();
//
//            // Lấy danh sách đơn hàng có mã voucher tương ứng và trạng thái bằng 3
//            List<Order> orderList = orderAdminRepository.findByCodeVoucher(voucherAdminDTO.getCode())
//                    .stream()
//                    .filter(order -> order.getStatus() == 3)
//                    .collect(Collectors.toList());
//
//            // Nếu danh sách đơn hàng không rỗng, đặt isUpdate là 1
//            if (!orderList.isEmpty()) {
//                voucherAdminDTO.setIsUpdate(1);
//            }else{
//                voucherAdminDTO.setIsUpdate(0);
//            }
//        }
//        return list;
//    }
//
//    @Override
//    public List<VoucherAdminDTO> getAllKichHoat() {
//        // Lấy danh sách voucher từ repository tùy chỉnh
//        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getAllKichHoat();
//
//        // Lọc và sắp xếp danh sách voucher theo ngày tạo (giảm dần)
//        list = list.stream()
//                .filter(voucher -> voucher.getCreateDate() != null)
//                .sorted(Comparator.comparing(VoucherAdminDTO::getCreateDate).reversed())
//                .collect(Collectors.toList());
//
//        // Lặp qua từng voucher trong danh sách
//        Iterator<VoucherAdminDTO> iterator = list.listIterator();
//        while (iterator.hasNext()) {
//            VoucherAdminDTO voucherAdminDTO = iterator.next();
//
//            // Lấy danh sách đơn hàng có mã voucher tương ứng và trạng thái bằng 3
//            List<Order> orderList = orderAdminRepository.findByCodeVoucher(voucherAdminDTO.getCode())
//                    .stream()
//                    .filter(order -> order.getStatus() == 3)
//                    .collect(Collectors.toList());
//
//            // Nếu danh sách đơn hàng không rỗng, đặt isUpdate là 1
//            if (!orderList.isEmpty()) {
//                voucherAdminDTO.setIsUpdate(1);
//            }else{
//                voucherAdminDTO.setIsUpdate(0);
//            }
//        }
//        return list;
//    }
//
//
//    @Override
//    public List<VoucherAdminDTO> getAllKhongKH() {
//        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getAllKhongKH();
//        return list;
//    }
//
//    @Override
//    public List<VoucherAdminDTO> getVouchersByTimeRange(String fromDate, String toDate) {
//        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getVouchersByTimeRange(fromDate, toDate);
//        return list;
//    }
//
//    @Override
//    public List<VoucherAdminDTO> getVouchersByKeyword(String keyword) {
//        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getVouchersByKeyword(keyword);
//        return list;
//    }
//
//    @Override
//    public List<VoucherAdminDTO> getVouchersByCustomer(String searchTerm) {
//        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getVouchersByCustomer(searchTerm);
//        return list;
//    }
//
//    @Override
//    public List<VoucherAdminDTO> getVouchersByVoucherType(String search) {
//        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getVouchersByVoucherType(search);
//        return list;
//    }

    @Override
    public List<VoucherAdminDTO> getAllVouchers() {
        // Lấy danh sách voucher từ repository tùy chỉnh
        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getAllVouchers();

        // Lọc và sắp xếp danh sách voucher theo ngày tạo (giảm dần)
        list = list.stream()
                .filter(voucher -> voucher.getCreateDate() != null)
                .sorted(Comparator.comparing(VoucherAdminDTO::getCreateDate).reversed())
                .collect(Collectors.toList());

        // Lặp qua từng voucher trong danh sách
        Iterator<VoucherAdminDTO> iterator = list.listIterator();
        while (iterator.hasNext()) {
            VoucherAdminDTO voucherAdminDTO = iterator.next();

            // Lấy danh sách đơn hàng có mã voucher tương ứng và trạng thái bằng 3
            List<Order> orderList = orderAdminRepository.findByCodeVoucher(voucherAdminDTO.getCode())
                    .stream()
                    .filter(order -> order.getStatus() == 3)
                    .collect(Collectors.toList());

            // Nếu danh sách đơn hàng không rỗng, đặt isUpdate là 1, ngược lại là 0
            voucherAdminDTO.setIsUpdate(!orderList.isEmpty() ? 1 : 0);
        }
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getAllKichHoat() {
        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getAllKichHoat();

        list = list.stream()
                .filter(voucher -> voucher.getCreateDate() != null)
                .sorted(Comparator.comparing(VoucherAdminDTO::getCreateDate).reversed())
                .collect(Collectors.toList());

        Iterator<VoucherAdminDTO> iterator = list.listIterator();
        while (iterator.hasNext()) {
            VoucherAdminDTO voucherAdminDTO = iterator.next();

            List<Order> orderList = orderAdminRepository.findByCodeVoucher(voucherAdminDTO.getCode())
                    .stream()
                    .filter(order -> order.getStatus() == 3)
                    .collect(Collectors.toList());

            voucherAdminDTO.setIsUpdate(!orderList.isEmpty() ? 1 : 0);
        }
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getAllKhongKH() {
        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getAllKhongKH();

        list = list.stream()
                .filter(voucher -> voucher.getCreateDate() != null)
                .sorted(Comparator.comparing(VoucherAdminDTO::getCreateDate).reversed())
                .collect(Collectors.toList());

        Iterator<VoucherAdminDTO> iterator = list.listIterator();
        while (iterator.hasNext()) {
            VoucherAdminDTO voucherAdminDTO = iterator.next();

            List<Order> orderList = orderAdminRepository.findByCodeVoucher(voucherAdminDTO.getCode())
                    .stream()
                    .filter(order -> order.getStatus() == 3)
                    .collect(Collectors.toList());

            voucherAdminDTO.setIsUpdate(!orderList.isEmpty() ? 1 : 0);
        }
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getVouchersByTimeRange(String fromDate, String toDate) {
        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getVouchersByTimeRange(fromDate, toDate);

        list = list.stream()
                .filter(voucher -> voucher.getCreateDate() != null)
                .sorted(Comparator.comparing(VoucherAdminDTO::getCreateDate).reversed())
                .collect(Collectors.toList());

        Iterator<VoucherAdminDTO> iterator = list.listIterator();
        while (iterator.hasNext()) {
            VoucherAdminDTO voucherAdminDTO = iterator.next();

            List<Order> orderList = orderAdminRepository.findByCodeVoucher(voucherAdminDTO.getCode())
                    .stream()
                    .filter(order -> order.getStatus() == 3)
                    .collect(Collectors.toList());

            voucherAdminDTO.setIsUpdate(!orderList.isEmpty() ? 1 : 0);
        }
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getVouchersByKeyword(String keyword) {
        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getVouchersByKeyword(keyword);

        list = list.stream()
                .filter(voucher -> voucher.getCreateDate() != null)
                .sorted(Comparator.comparing(VoucherAdminDTO::getCreateDate).reversed())
                .collect(Collectors.toList());

        Iterator<VoucherAdminDTO> iterator = list.listIterator();
        while (iterator.hasNext()) {
            VoucherAdminDTO voucherAdminDTO = iterator.next();

            List<Order> orderList = orderAdminRepository.findByCodeVoucher(voucherAdminDTO.getCode())
                    .stream()
                    .filter(order -> order.getStatus() == 3)
                    .collect(Collectors.toList());

            voucherAdminDTO.setIsUpdate(!orderList.isEmpty() ? 1 : 0);
        }
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getVouchersByCustomer(String searchTerm) {
        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getVouchersByCustomer(searchTerm);

        list = list.stream()
                .filter(voucher -> voucher.getCreateDate() != null)
                .sorted(Comparator.comparing(VoucherAdminDTO::getCreateDate).reversed())
                .collect(Collectors.toList());

        Iterator<VoucherAdminDTO> iterator = list.listIterator();
        while (iterator.hasNext()) {
            VoucherAdminDTO voucherAdminDTO = iterator.next();

            List<Order> orderList = orderAdminRepository.findByCodeVoucher(voucherAdminDTO.getCode())
                    .stream()
                    .filter(order -> order.getStatus() == 3)
                    .collect(Collectors.toList());

            voucherAdminDTO.setIsUpdate(!orderList.isEmpty() ? 1 : 0);
        }
        return list;
    }

    @Override
    public List<VoucherAdminDTO> getVouchersByVoucherType(String search) {
        List<VoucherAdminDTO> list = voucherAdminCustomRepository.getVouchersByVoucherType(search);

        list = list.stream()
                .filter(voucher -> voucher.getCreateDate() != null)
                .sorted(Comparator.comparing(VoucherAdminDTO::getCreateDate).reversed())
                .collect(Collectors.toList());

        Iterator<VoucherAdminDTO> iterator = list.listIterator();
        while (iterator.hasNext()) {
            VoucherAdminDTO voucherAdminDTO = iterator.next();

            List<Order> orderList = orderAdminRepository.findByCodeVoucher(voucherAdminDTO.getCode())
                    .stream()
                    .filter(order -> order.getStatus() == 3)
                    .collect(Collectors.toList());

            voucherAdminDTO.setIsUpdate(!orderList.isEmpty() ? 1 : 0);
        }
        return list;
    }

    @Override
    public ServiceResult<VoucherAdminDTO> createVoucher(VoucherAdminDTO voucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();

        Voucher voucher = voucherAdminMapper.toEntity(voucherAdminDTO);

        voucher.setCode("VC" + Instant.now().getEpochSecond());
        voucher.setCreateDate(LocalDateTime.now());
        voucher.setStatus(0);
        voucher.setIdel(0);
        voucher.setDelete(0);
        voucher.setAmountUsed(0);
        voucher.setConditionApply(voucherAdminDTO.getConditionApply());
        voucher.setOptionCustomer(voucherAdminDTO.getOptionCustomer());
        voucher.setDescription(voucherAdminDTO.getDescription());
        voucher.setApply(voucherAdminDTO.getApply());
        voucher.setCreateName(voucherAdminDTO.getCreateName());

        LocalDateTime startDateTime = voucherAdminDTO.getStartDate();
        voucher.setStartDate(startDateTime);
        LocalDateTime endDateTime = voucherAdminDTO.getEndDate();
        voucher.setEndDate(endDateTime);

        voucher.setAllow(voucherAdminDTO.getAllow());

        if (voucherAdminDTO.getVoucherType() == 0) {
            voucher.setMaxReduced(voucher.getMaxReduced());
        }

        if (voucherAdminDTO.getOptionCustomer() == 0) {
            voucher.setIdCustomer(null);
        } else {
            StringBuilder customer = new StringBuilder();

            for (int i = 0; i < voucherAdminDTO.getCustomerAdminDTOList().size(); i++) {
                CustomerAdminDTO customerAdminDTO = voucherAdminDTO.getCustomerAdminDTOList().get(i);
                customer.append(customerAdminDTO.getId());
                customer.append(",");
            }

            if (customer.length() > 0) {
                customer.setLength(customer.length() - 1);
                // Lặp qua danh sách id và tạo voucher cho mỗi id
                voucher.setIdCustomer((customer.toString()));
                voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());
            } else {
                // Xử lý trường hợp không có customer nào
                voucher.setIdCustomer(null);
            }
        }

        voucherAdminRepository.save(voucher);
        serviceResult.setData(voucherAdminDTO);
        serviceResult.setMessage("Them thanh cong");
        serviceResult.setStatus(HttpStatus.OK);

        return serviceResult;
    }

    @Override
    public ServiceResult<VoucherAdminDTO> updateVoucher(Long id, VoucherAdminDTO voucherAdminDTO) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();

        Optional<Voucher> voucherOptional = voucherAdminRepository.findById(id);

        if (voucherOptional.isPresent()) {
            Voucher voucher = voucherOptional.get();
            // Cập nhật các thuộc tính cần thiết dựa trên updatedVoucherAdminDTO
            voucher.setName(voucherAdminDTO.getName());
            voucher.setLimitCustomer(voucherAdminDTO.getLimitCustomer());
            voucher.setQuantity(voucherAdminDTO.getQuantity());
            voucher.setConditionApply(voucherAdminDTO.getConditionApply());
            voucher.setDescription(voucherAdminDTO.getDescription());
            voucher.setApply(voucherAdminDTO.getApply());
            voucher.setCreateName(voucherAdminDTO.getCreateName());
            voucher.setStartDate(voucherAdminDTO.getStartDate());
            voucher.setEndDate(voucherAdminDTO.getEndDate());
            voucher.setReducedValue(voucherAdminDTO.getReducedValue());
            voucher.setVoucherType(voucherAdminDTO.getVoucherType());
            voucher.setAllow(voucherAdminDTO.getAllow());
            voucher.setOptionCustomer(voucherAdminDTO.getOptionCustomer());
            if (voucherAdminDTO.getVoucherType() == 1) {
                voucher.setMaxReduced(voucherAdminDTO.getMaxReduced());
            }
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
            voucherAdminRepository.save(voucher);
            serviceResult.setData(voucherAdminDTO);
            serviceResult.setMessage("Cap nhat thanh cong");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setData(null);
            serviceResult.setMessage("Không tìm thấy Voucher cần cập nhật");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }

        return serviceResult;
    }

    @Override
    public ServiceResult<Void> deleteVoucher(Long voucherId) {
        ServiceResult<Void> serviceResult = new ServiceResult<>();
        Optional<Voucher> voucher = voucherAdminRepository.findById(voucherId);

        if (voucher.isPresent()) {
            Voucher voucher1 = voucher.get();
            voucher1.setDelete(1); // Sửa thành setIdel(1) để đánh dấu đã xóa
            voucherAdminRepository.save(voucher1); // Lưu lại thay đổi vào cơ sở dữ liệu

            serviceResult.setMessage("Xóa thành công!");
            serviceResult.setStatus(HttpStatus.OK);
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }

    @Override
    public VoucherAdminDTO getDetailVoucher(Long id) {
        Voucher voucher = voucherAdminRepository.findById(id).get();

        VoucherAdminDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);

        String idCustomer = voucherAdminDTO.getIdCustomer();

        List<CustomerAdminDTO> toList = new ArrayList<>();
        if (StringUtils.isNotBlank(idCustomer)) {
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
        List<CustomerAdminDTO> list = voucherAdminCustomRepository.getAllCustomer();
        return list;
    }

    @Override
    public ServiceResult<VoucherAdminDTO> KichHoat(Long idVoucher) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();
        Optional<Voucher> optionalVoucher = voucherAdminRepository.findById(idVoucher);

        if (optionalVoucher.isPresent()) {
            Voucher voucher = optionalVoucher.get();

            voucher.setIdel(voucher.getIdel() == 1 ? 0 : 1);
            voucher = voucherAdminRepository.save(voucher);
            VoucherAdminDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);
            serviceResult.setData(voucherAdminDTO);
            serviceResult.setStatus(HttpStatus.OK);
            serviceResult.setMessage("Thành công");

            // Lưu lại thay đổi vào cơ sở dữ liệu
        } else {
            serviceResult.setMessage("Không tìm thấy khuyến mãi");
            serviceResult.setStatus(HttpStatus.NOT_FOUND);
        }
        return serviceResult;
    }

    @Override
    public ServiceResult<VoucherAdminDTO> setIdel(Long idVoucher) {
        ServiceResult<VoucherAdminDTO> serviceResult = new ServiceResult<>();

        Optional<Voucher> optionalVoucher = voucherAdminRepository.findById(idVoucher);

        if (optionalVoucher.isPresent()) {
            Voucher voucher = optionalVoucher.get();
            voucher.setIdel(0);
            voucher = voucherAdminRepository.save(voucher);
            VoucherAdminDTO voucherAdminDTO = voucherAdminMapper.toDto(voucher);
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

//    @Override
//    public byte[] exportExcelVoucher() throws IOException {
//        List<SheetConfigDTO> sheetConfigList = new ArrayList<>();
//        List<VoucherAdminDTO> voucherAdminDTOS = voucherAdminCustomRepository.getAllVouchersExport();
//        sheetConfigList = getDataForExcel("Danh Sách Voucher", voucherAdminDTOS, sheetConfigList, AppConstant.EXPORT_DATA);
//        try {
//            String title = "DANH SÁCH VOUCHER";
//            return fileExportUtil.exportXLSX(false, sheetConfigList, title);
//        } catch (IOException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ioE) {
//            throw new IOException("Lỗi Export" + ioE.getMessage(), ioE);
//        }
//    }
//
//    private List<SheetConfigDTO> getDataForExcel(String sheetName,
//                                                 List<VoucherAdminDTO> listDataSheet,
//                                                 List<SheetConfigDTO> sheetConfigList,
//                                                 Long exportType) {
//        SheetConfigDTO sheetConfig = new SheetConfigDTO();
//        String[] headerArr = null;
//        if (AppConstant.EXPORT_DATA.equals(exportType)) {
//            headerArr =
//                    new String[]{
//                            "STT",
//                            "Mã Voucher",
//                            "Tên Voucher",
//                            "Ngày Bắt Đầu",
//                            "Ngày Kết Thúc",
//                            "Điều kiện áp dụng",
//                            "Giá Trị Giảm",
//                            "Số lượng",
//                            "Giới hạn sử dụng với mỗi khách hàng",
//                            "Tên khách hàng",
//                    };
//        }
//        sheetConfig.setSheetName(sheetName);
//        sheetConfig.setHeaders(headerArr);
//        int recordNo = 1;
//        List<CellConfigDTO> cellConfigCustomList = new ArrayList<>();
//        for (VoucherAdminDTO item : listDataSheet) {
//            item.setRecordNo(recordNo++);
//        }
//        List<CellConfigDTO> cellConfigList = new ArrayList<>();
//        sheetConfig.setList(listDataSheet);
//        cellConfigList.add(new CellConfigDTO("recordNo", AppConstant.ALIGN_LEFT, AppConstant.NO));
//        cellConfigList.add(new CellConfigDTO("code", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("name", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("startDate", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("endDate", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("conditionApply", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("reducedValue", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        cellConfigList.add(new CellConfigDTO("quantity", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
//        cellConfigList.add(new CellConfigDTO("limitCustomer", AppConstant.ALIGN_LEFT, AppConstant.NUMBER));
//        cellConfigList.add(new CellConfigDTO("listCodeCustomerExport", AppConstant.ALIGN_LEFT, AppConstant.STRING));
//        if (AppConstant.EXPORT_DATA.equals(exportType) || AppConstant.EXPORT_ERRORS.equals(exportType)) {
//            cellConfigList.add(new CellConfigDTO("messageStr", AppConstant.ALIGN_LEFT, AppConstant.ERRORS));
//        }
//        sheetConfig.setHasIndex(false);
//        sheetConfig.setHasBorder(true);
//        sheetConfig.setExportType(exportType.intValue());
//        sheetConfig.setCellConfigList(cellConfigList);
//        sheetConfig.setCellCustomList(cellConfigCustomList);
//        sheetConfigList.add(sheetConfig);
//        return sheetConfigList;
//    }

    public List<String> getAllVoucherExport() {
        List<String> lstStr = voucherAdminRepository.findAll()
                .stream()
                .map(b -> b.getId() + "-" + b.getName())
                .collect(Collectors.toList());
        return lstStr;
    }
}
