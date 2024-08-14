package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.BrandAdminDTO;
import com.example.backend.core.admin.dto.CategoryAdminDTO;
import com.example.backend.core.admin.repository.CategoryAdminRepository;
import com.example.backend.core.admin.service.CategoryAdminService;
import com.example.backend.core.model.Brand;
import com.example.backend.core.model.Category;
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
public class CategoryAdminController {
    @Autowired
    private CategoryAdminService categoryAdminService;

    @Autowired
    private CategoryAdminRepository categoryAdminRepository;

    @GetMapping("category/hien-thi")
    public ResponseEntity<List<CategoryAdminDTO>> hienthi() {
        return ResponseEntity.ok(categoryAdminService.getAll());
    }

    @PostMapping("category/add")
    public ResponseEntity<?> add(@RequestBody CategoryAdminDTO categoryAdminDTO) {
        return ResponseEntity.ok(categoryAdminService.add(categoryAdminDTO));
    }

    @PutMapping("category/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody CategoryAdminDTO categoryAdminDTO) {
        return ResponseEntity.ok(categoryAdminService.update(categoryAdminDTO, id));
    }

    @DeleteMapping("category/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(categoryAdminService.delete(id));
    }

    @GetMapping("/category/export-data")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<CategoryAdminDTO> categoryAdminDTOList = categoryAdminService.getAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh Mục");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Tên danh mục");
        headerRow.createCell(1).setCellValue("Ngày tạo");
        headerRow.createCell(2).setCellValue("Ngày cập nhật");
        headerRow.createCell(3).setCellValue("Trạng thái");

        // Create a date-time formatter for the Excel output
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Create data rows
        int rowNum = 1;
        for (CategoryAdminDTO categoryAdminDTO : categoryAdminDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(categoryAdminDTO.getName());

            // Format createDate and updateDate if they are not null
            String createDateStr = categoryAdminDTO.getCreateDate() != null ? categoryAdminDTO.getCreateDate().format(formatter) : "";
            String updateDateStr = categoryAdminDTO.getUpdateDate() != null ? categoryAdminDTO.getUpdateDate().format(formatter) : "";
            row.createCell(1).setCellValue(createDateStr);
            row.createCell(2).setCellValue(updateDateStr);

            // Convert status to description
            String statusDescription = categoryAdminDTO.getStatus() == 0 ? "Đang hoạt động" : "Ngừng hoạt động";
            row.createCell(3).setCellValue(statusDescription);
        }

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/category/export-data-template")
    public void exportToExcelTemplate(HttpServletResponse response) throws IOException {
        List<BrandAdminDTO> brandAdminDTOList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Danh Mục");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Tên danh mục");
        headerRow.createCell(1).setCellValue("Trạng thái");

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/category/import-data")
    public String importFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            // Kiểm tra tên của sheet
            String expectedSheetName = "Danh Mục"; // Thay đổi tên sheet theo nhu cầu
            Sheet sheet = workbook.getSheet(expectedSheetName);

            if (sheet == null) {
                return "Sheet không tồn tại trong file Excel.";
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Category category = new Category();
                category.setName(row.getCell(0).getStringCellValue());
                category.setCreateDate(LocalDateTime.now());
                category.setUpdateDate(LocalDateTime.now());
                category.setIsDel(0);

                // Chuyển đổi mô tả trạng thái thành số nguyên
                String statusDescription = row.getCell(1).getStringCellValue();
                Integer status = "Đang hoạt động".equals(statusDescription) ? 0 : 1;

                category.setStatus(status);

                categoryAdminRepository.save(category);
            }
            workbook.close();
        }
        return "File imported successfully";
    }
}
