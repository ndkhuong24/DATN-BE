package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.DiscountAdminDTO;
import com.example.backend.core.admin.dto.DiscountDetailAdminDTO;
import com.example.backend.core.admin.service.DiscountDetailAdminService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class DiscountDetailAdminController {
    @Autowired
    private DiscountDetailAdminService discountDetailAdminService;

//    @Autowired
//    private DiscountAdminService discountAdminService;//moi

//    private static final Logger log = LoggerFactory.getLogger(DiscountDetailAdminDTO.class);

//    @GetMapping("/discount")
//    public ResponseEntity<?> getAllDiscount() {
//        try {
//            List<DiscountAdminDTO> discountAdminDTOList = discountDetailAdminService.getAll();
//            return ResponseEntity.ok(discountAdminDTOList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @GetMapping("/discount/KH")
//    public ResponseEntity<?> getAllDiscountKH() {
//        try {
//            List<DiscountAdminDTO> discountAdminDTOList = discountDetailAdminService.getAllKichHoat();
//            return ResponseEntity.ok(discountAdminDTOList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @GetMapping("/discount/KKH")
//    public ResponseEntity<?> getAllDiscountKhongKH() {
//        try {
//            List<DiscountAdminDTO> discountAdminDTOList = discountDetailAdminService.getAllKhongKichHoat();
//            return ResponseEntity.ok(discountAdminDTOList);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

//    @PutMapping("discount/kichHoat/{id}")
//    public ResponseEntity<?> kichHoat(@PathVariable Long id) {
//        return ResponseEntity.ok(discountDetailAdminService.KichHoat(id));
//    }
//
//    @PutMapping("discount/setIdel/{id}")
//    public ResponseEntity<?> setIdel(@PathVariable Long id) {
//        return ResponseEntity.ok(discountDetailAdminService.setIdel(id));
//    }

//    @GetMapping()
//    public ResponseEntity<?> getAllDiscount() {
//        return ResponseEntity.ok(discountService.getAll());//cu
//    }
//
//    @GetMapping("/KH")
//    public ResponseEntity<?> getAllDiscountKH() {
//        return ResponseEntity.ok(discountService.getAllKichHoat());
//    }
//
//    @GetMapping("/KKH")
//    public ResponseEntity<?> getAllDiscountKhongKH() {
//        return ResponseEntity.ok(discountService.getAllKhongKichHoat());
//    }
//
//    @GetMapping("/product")
//    public ResponseEntity<?> getAllProduct() {
//        return ResponseEntity.ok(discountService.getAllProduct());
//    }
//
//    @PostMapping()
//    public ResponseEntity<?> createDiscount(@RequestBody DiscountDetailAdminDTO khuyenMaiDTO) {
//        return ResponseEntity.ok(discountService.createDiscount(khuyenMaiDTO));
//    }
//
//    @PutMapping("/{idDiscount}")
//    public ResponseEntity<?> updateDiscount(@PathVariable(name = "idDiscount") Long idDiscount, @Valid @RequestBody DiscountDetailAdminDTO khuyenMaiDTO) {
//        System.out.println(idDiscount);
//        khuyenMaiDTO.setIdDiscount(idDiscount);
//        return ResponseEntity.ok(discountService.updateDiscount(khuyenMaiDTO));
//    }

//    @GetMapping("/{idDiscount}")
//    public ResponseEntity<?> getDetailDiscount(@PathVariable Long idDiscount) {
//        return ResponseEntity.ok(discountService.getDetailDiscount(idDiscount));
//    }
//
//    @DeleteMapping("/{idDiscount}")
//    public ResponseEntity<?> deleteDiscount(@PathVariable Long idDiscount) {
//        return ResponseEntity.ok(discountService.deleteDiscount(idDiscount));
//    }
//    @GetMapping("/searchByDate")
//    public List<DiscountAdminDTO> searchByDateRange(
//            @RequestParam(name = "fromDate") String fromDate,
//            @RequestParam(name = "toDate") String toDate) {
//        System.out.println( "fromdate");
//        return discountService.getAllByDateRange(fromDate, toDate);
//    }
//
//    @GetMapping("/searchByDiscount")
//    public List<DiscountAdminDTO> searchByName(
//            @RequestParam(name = "search") String search) {
//
//        return discountService.getAllByCodeOrName(search);
//    }
//
//    @GetMapping("/searchByBrand")
//    public List<DiscountAdminDTO> searchByBrand(
//            @RequestParam(name = "search") String search) {
//
//        return discountService.getAllByBrand(search);
//    }
//
//    @GetMapping("/searchByCategory")
//    public List<DiscountAdminDTO> searchByCategory(
//            @RequestParam(name = "search") String category) {
//
//        return discountService.getAllByCategory(category);
//    }
//
//    @GetMapping("/searchByProduct")
//    public List<DiscountAdminDTO> searchByProduct(
//            @RequestParam(name = "search") String product) {
//
//        return discountService.getAllByProductNameOrCode(product);
//    }
//    @GetMapping("/discount/export-data")
//    public ResponseEntity<?> exportData() {
//        try {
//            byte[] fileData = discountService.exportExcelDiscount();
//            SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.YYYYMMDDHHSS);
//            String fileName = "DS_CBGV" + dateFormat.format(new Date()) + AppConstant.DOT + AppConstant.EXTENSION_XLSX;
//            return fileExportUtil.responseFileExportWithUtf8FileName(fileData, fileName, AppConstant.MIME_TYPE_XLSX);
//        }catch (Exception e){
//            log.error(e.getMessage(), e);
//            return null;
//        }
//    }

}
