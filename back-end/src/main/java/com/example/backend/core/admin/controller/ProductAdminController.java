package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.*;
import com.example.backend.core.admin.mapper.ProductAdminMapper;
import com.example.backend.core.admin.service.*;
import com.example.backend.core.commons.FileExportUtil;
import com.example.backend.core.commons.ResponseImportDTO;
import com.example.backend.core.commons.ServiceResult;
import com.example.backend.core.constant.AppConstant;
import com.example.backend.core.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class ProductAdminController {
    @Autowired
    private ProductAdminService prdsv;
    private static final Logger log = LoggerFactory.getLogger(ProductAdminDTO.class);
    @Autowired
    private FileExportUtil fileExportUtil;


    @GetMapping("product/hien-thi")
    public ResponseEntity<List<ProductAdminDTO>> hienthi() {
        return ResponseEntity.ok(prdsv.getAll());
    }
    @GetMapping("product/hien-thii")
    public ResponseEntity<List<ProductAdminDTO>> hienthiall(@RequestParam(value = "search", required = false, defaultValue = "") String search) {
        if (search != null && !search.isEmpty()) {
            return ResponseEntity.ok(prdsv.getAllProductsWithDetailsAndImages(search));
        } else {
            return ResponseEntity.ok(prdsv.getAllProductsWithDetailsAndImages(null));
        }
    }
    @PostMapping("product/add")
    public ResponseEntity<?> add(@RequestBody ProductAdminDTO productAdminDTO) {
        return ResponseEntity.ok(prdsv.add(productAdminDTO));
    }

    @PutMapping("product/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ProductAdminDTO productAdminDTO) {
        return ResponseEntity.ok(prdsv.update(productAdminDTO, id));
    }
    @PutMapping("product/{id}/activate")
    public void activateProduct(@PathVariable("id") Long id) {
        prdsv.activateProduct(id);
    }

    @PutMapping("product/{id}/deactivate")
    public void deactivateProduct(@PathVariable("id") Long id) {
        prdsv.deactivateProduct(id);
    }

    @DeleteMapping("product/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(prdsv.delete(id));
    }

    @GetMapping("product/detail/{id}")
    public ResponseEntity<?> detail(@PathVariable("id") Long id) {
        return ResponseEntity.ok(prdsv.getById(id));
    }

    @GetMapping("product/search/{param}")
    public ResponseEntity<?> searchProduct(
            @PathVariable("param") String param
    ) {
        return ResponseEntity.ok(prdsv.findByNameLikeOrCodeLike(param));
    }

    @GetMapping("/product/export-data")
    public ResponseEntity<?> exportData() {
        try {
            byte[] fileData = prdsv.exportExcelProduct();
            SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.YYYYMMDDHHSS);
            String fileName = "DS_QLSP" + dateFormat.format(new Date()) + AppConstant.DOT + AppConstant.EXTENSION_XLSX;
            return fileExportUtil.responseFileExportWithUtf8FileName(fileData, fileName, AppConstant.MIME_TYPE_XLSX);
        }catch (Exception e){
            log.error(e.getMessage(), e);
            return null;
        }
    }
    @GetMapping("/product/export-data-template")
    public ResponseEntity<?> exportDataTemplate() {
        try {
            byte[] fileData = prdsv.exportExcelTemplateProduct();
            SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.YYYYMMDDHHSS);
            String fileName = "DS_QLSP" + dateFormat.format(new Date()) + AppConstant.DOT + AppConstant.EXTENSION_XLSX;
            return fileExportUtil.responseFileExportWithUtf8FileName(fileData, fileName, AppConstant.MIME_TYPE_XLSX);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    @PostMapping("/product/import")
    public ServiceResult<ResponseImportDTO> importProduct(
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(required = false) Long typeImport
    ) throws IOException, ParseException {
        return prdsv.importFileProduct(file, typeImport);
    }

    @PostMapping("/product/exportDataErrors")
    public ResponseEntity<?> exportDataErrors(@RequestBody List<ProductAdminDTO> listErr) {
        log.info("export data lỗi ");
        try {
            byte[] fileData = prdsv.exportExcelProductErrors(listErr);
            SimpleDateFormat dateFormat = new SimpleDateFormat(AppConstant.YYYYMMDDHHSS);
            String fileName = "DS_SanPham_errors" + dateFormat.format(new Date()) + AppConstant.DOT + AppConstant.EXTENSION_XLSX;
            return fileExportUtil.responseFileExportWithUtf8FileName(fileData, fileName, AppConstant.MIME_TYPE_XLSX);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
