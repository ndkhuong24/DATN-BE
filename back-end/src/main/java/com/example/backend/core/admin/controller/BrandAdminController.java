package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.repository.BrandAdminRepository;
import com.example.backend.core.admin.service.BrandAdminService;
import com.example.backend.core.model.Brand;
import com.example.backend.core.model.Color;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/admin")
public class BrandAdminController {
    @Autowired
    private BrandAdminService brandAdminService;

    @Autowired
    private BrandAdminRepository brandAdminRepository;

    @GetMapping("brand/hien-thi")
    public ResponseEntity<List<BrandAdminDTO>> hienthi() {
        return ResponseEntity.ok(brandAdminService.getAll());
    }

    @PostMapping("brand/add")
    public ResponseEntity<?> add(@RequestBody BrandAdminDTO brandAdminDTO) {
        return ResponseEntity.ok(brandAdminService.add(brandAdminDTO));
    }

    @PutMapping("brand/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody BrandAdminDTO brandAdminDTO) {
        return ResponseEntity.ok(brandAdminService.update(brandAdminDTO, id));
    }

    @DeleteMapping("brand/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(brandAdminService.delete(id));
    }

    @GetMapping("/brand/export-data")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<BrandAdminDTO> brandAdminDTOList = brandAdminService.getAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thương Hiệu");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Tên thương hiệu");
        headerRow.createCell(1).setCellValue("Ngày tạo");
        headerRow.createCell(2).setCellValue("Ngày cập nhật");
        headerRow.createCell(3).setCellValue("Trạng thái");

        // Create a date-time formatter for the Excel output
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Create data rows
        int rowNum = 1;
        for (BrandAdminDTO brandAdminDTO : brandAdminDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(brandAdminDTO.getName());

            // Format createDate and updateDate if they are not null
            String createDateStr = brandAdminDTO.getCreateDate() != null ? brandAdminDTO.getCreateDate().format(formatter) : "";
            String updateDateStr = brandAdminDTO.getUpdateDate() != null ? brandAdminDTO.getUpdateDate().format(formatter) : "";
            row.createCell(1).setCellValue(createDateStr);
            row.createCell(2).setCellValue(updateDateStr);

            // Convert status to description
            String statusDescription = brandAdminDTO.getStatus() == 0 ? "Đang hoạt động" : "Ngừng hoạt động";
            row.createCell(3).setCellValue(statusDescription);
        }

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/brand/export-data-template")
    public void exportToExcelTemplate(HttpServletResponse response) throws IOException {
        List<BrandAdminDTO> brandAdminDTOList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Thương Hiệu");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Tên thương hiệu");
        headerRow.createCell(1).setCellValue("Trạng thái");

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/brand/import-data")
    public String importFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            // Kiểm tra tên của sheet
            String expectedSheetName = "Thương Hiệu"; // Thay đổi tên sheet theo nhu cầu
            Sheet sheet = workbook.getSheet(expectedSheetName);

            if (sheet == null) {
                return "Sheet không tồn tại trong file Excel.";
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Brand brand = new Brand();
                brand.setName(row.getCell(0).getStringCellValue());
                brand.setCreateDate(LocalDateTime.now());
                brand.setUpdateDate(LocalDateTime.now());
                brand.setIsDel(0);

                // Chuyển đổi mô tả trạng thái thành số nguyên
                String statusDescription = row.getCell(1).getStringCellValue();
                Integer status = "Đang hoạt động".equals(statusDescription) ? 0 : 1;

                brand.setStatus(status);

                brandAdminRepository.save(brand);
            }
            workbook.close();
        }
        return "File imported successfully";
    }
}
