package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.dto.VoucherAdminDTO;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import com.example.backend.core.admin.service.VoucherAdminService;
import com.example.backend.core.commons.FileExportUtil;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin/voucher")
@CrossOrigin("*")
public class VoucherAdminController {
    @Autowired
    private VoucherAdminService voucherAdminService;
    @Autowired
    private FileExportUtil fileExportUtil;
    private static final Logger log = LoggerFactory.getLogger(DiscountDetailAdminDTO.class);


    @GetMapping()
    public ResponseEntity<?> getAllVoucher(){
        return ResponseEntity.ok(voucherAdminService.getAllVouchers());
    }
    @PostMapping()
    public ResponseEntity<?> createVoucher( @RequestBody VoucherAdminDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherAdminService.createVoucher(voucherAdminDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id,@Valid @RequestBody VoucherAdminDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherAdminService.updateVoucher(id,voucherAdminDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult<Void>> deleteVoucher(@PathVariable Long id) {
        ServiceResult<Void> result = voucherAdminService.deleteVoucher(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detailVoucher(@PathVariable Long id) {
        return ResponseEntity.ok( voucherAdminService.getDetailVoucher(id));
    }
    @PutMapping("/kichHoat/{id}")
    public ResponseEntity<?> kichHoat(@PathVariable Long id) throws MessagingException {
        return ResponseEntity.ok(voucherAdminService.KichHoat(id));
    }
    @PutMapping("/setIdel/{id}")
    public ResponseEntity<?> setIdel(@PathVariable Long id) throws MessagingException {
        return ResponseEntity.ok(voucherAdminService.setIdel(id));
    }
    @GetMapping("/customer")
    public ResponseEntity<?> getAllCustomer(){
        return ResponseEntity.ok(voucherAdminService.getAllCustomer());
    }
    @GetMapping("/searchByDate")
    public List<VoucherAdminDTO> searchByDateRange(
            @RequestParam(name = "fromDate") String fromDate,
            @RequestParam(name = "toDate") String toDate) {

        return voucherAdminService.getVouchersByTimeRange(fromDate,toDate);
    }
    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody VoucherAdminDTO voucherAdminDTO) throws MessagingException {
        voucherAdminService.sendMessageUsingThymeleafTemplate(voucherAdminDTO);
        return ResponseEntity.ok("Success");
    }
    @GetMapping("/searchByVoucher")
    public List<VoucherAdminDTO> searchByVoucher(
            @RequestParam(name = "search")  String search) {

        return voucherAdminService.getVouchersByKeyword(search);
    }
    @GetMapping("/searchByCustomer")
    public List<VoucherAdminDTO> searchByCustomer(
            @RequestParam(name = "search")  String search) {

        return voucherAdminService.getVouchersByCustomer(search);
    }
    @GetMapping("/KH")
    public ResponseEntity<?> getAllDiscountKH(){
        return ResponseEntity.ok(voucherAdminService.getAllKichHoat());
    }
    @GetMapping("/KKH")
    public ResponseEntity<?> getAllDiscountKhongKH(){
        return ResponseEntity.ok(voucherAdminService.getAllKhongKH());
    }
    @GetMapping("/export-data")
    public ResponseEntity<?> exportData() {
        try {
            byte[] fileData = voucherAdminService.exportExcelVoucher();
            SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.YYYYMMDDHHSS);
            String fileName = "DS_CBGV" + dateFormat.format(new Date()) + AppConstant.DOT + AppConstant.EXTENSION_XLSX;
            return fileExportUtil.responseFileExportWithUtf8FileName(fileData, fileName, AppConstant.MIME_TYPE_XLSX);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
