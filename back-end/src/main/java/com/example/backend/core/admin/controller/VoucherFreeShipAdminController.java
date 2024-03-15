package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;

import com.example.backend.core.admin.dto.VoucherFreeShipDTO;
import com.example.backend.core.admin.service.VoucherFSAdminService;
import com.example.backend.core.commons.FileExportUtil;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/admin/voucherFS")
@CrossOrigin("*")
public class VoucherFreeShipAdminController {
    @Autowired
    private VoucherFSAdminService voucherFSService;
    @Autowired
    private FileExportUtil fileExportUtil;
    private static final Logger log = LoggerFactory.getLogger(DiscountDetailAdminDTO.class);


    @GetMapping("")
    public ResponseEntity<?> getAllVoucher(){
        return ResponseEntity.ok(voucherFSService.getAllVouchers());
    }
    @PostMapping()
    public ResponseEntity<?> createVoucher( @RequestBody VoucherFreeShipDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherFSService.createVoucher(voucherAdminDTO));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateVoucher(@PathVariable Long id,@Valid @RequestBody VoucherFreeShipDTO voucherAdminDTO){
        return ResponseEntity.ok(voucherFSService.updateVoucher(id,voucherAdminDTO));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<ServiceResult<Void>> deleteVoucher(@PathVariable Long id) {
        ServiceResult<Void> result = voucherFSService.deleteVoucher(id);
        return ResponseEntity.ok(result);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detailVoucher(@PathVariable Long id) {
        return ResponseEntity.ok( voucherFSService.getDetailVoucher(id));
    }
    @PutMapping("/kichHoat/{id}")
    public ResponseEntity<?> deleteDiscount(@PathVariable Long id) {
        return ResponseEntity.ok(voucherFSService.KichHoat(id));
    }
    @PutMapping("/setIdel/{id}")
    public ResponseEntity<?> setIdel(@PathVariable Long id) {
        return ResponseEntity.ok(voucherFSService.setIdel(id));
    }
    @GetMapping("/customer")
    public ResponseEntity<?> getAllCustomer(){
        return ResponseEntity.ok(voucherFSService.getAllCustomer());
    }
    @GetMapping("/searchByDate")
    public List<VoucherFreeShipDTO> searchByDateRange(
            @RequestParam(name = "fromDate") String fromDate,
            @RequestParam(name = "toDate") String toDate) {

        return voucherFSService.getVouchersByTimeRange(fromDate, toDate);
    }
    @PostMapping("/sendEmail")
    public ResponseEntity<?> sendEmail(@RequestBody VoucherFreeShipDTO voucherAdminDTO) throws MessagingException {
        voucherFSService.sendMessageUsingThymeleafTemplate(voucherAdminDTO);
        return ResponseEntity.ok("Success");
    }
    @GetMapping("/searchByVoucherFS")
    public List<VoucherFreeShipDTO> searchByVoucher(
            @RequestParam(name = "search")  String search) {

        return voucherFSService.getVouchersByKeyword(search);
    }
    @GetMapping("/searchByCustomer")
    public List<VoucherFreeShipDTO> searchByCustomer(
            @RequestParam(name = "search")  String search) {

        return voucherFSService.getVouchersByCustomer(search);
    }
    @GetMapping("/KH")
    public ResponseEntity<?> getAllDiscountKH(){
        return ResponseEntity.ok(voucherFSService.getAllKichHoat());
    }
    @GetMapping("/KKH")
    public ResponseEntity<?> getAllDiscountKhongKH(){
        return ResponseEntity.ok(voucherFSService.getAllKhongKH());
    }
    @GetMapping("/export-data")
    public ResponseEntity<?> exportData() {
        try {
            byte[] fileData = voucherFSService.exportExcelVoucher();
            SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.YYYYMMDDHHSS);
            String fileName = "DS_VC" + dateFormat.format(new Date()) + AppConstant.DOT + AppConstant.EXTENSION_XLSX;
            return fileExportUtil.responseFileExportWithUtf8FileName(fileData, fileName, AppConstant.MIME_TYPE_XLSX);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
