package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.dto.SizeAdminDTO;
import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.admin.repository.SizeAdminRepository;
import com.example.backend.core.admin.service.SizeAdminService;
import com.example.backend.core.model.Color;
import com.example.backend.core.model.Size;
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
@RequestMapping("api/admin")
public class SizeAdminController {
    @Autowired
    private SizeAdminService sizeAdminService;

    @Autowired
    private SizeAdminRepository sizeAdminRepository;

    @GetMapping("size/hien-thi")
    public ResponseEntity<List<SizeAdminDTO>> hienthi() {
        return ResponseEntity.ok(sizeAdminService.getAll());
    }

    @PostMapping("size/add")
    public ResponseEntity<?> add(@RequestBody SizeAdminDTO sizeAdminDTO) {
        return ResponseEntity.ok(sizeAdminService.add(sizeAdminDTO));
    }

    @PutMapping("size/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody SizeAdminDTO sizeAdminDTO) {
        return ResponseEntity.ok(sizeAdminService.update(sizeAdminDTO, id));
    }

    @DeleteMapping("size/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(sizeAdminService.delete(id));
    }

    @GetMapping("/size/export-data")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<SizeAdminDTO> sizeAdminDTOList = sizeAdminService.getAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Kích Cỡ");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Kích thước");
        headerRow.createCell(1).setCellValue("Ngày tạo");
        headerRow.createCell(2).setCellValue("Ngày cập nhật");
        headerRow.createCell(3).setCellValue("Trạng thái");

        // Create a date-time formatter for the Excel output
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Create data rows
        int rowNum = 1;
        for (SizeAdminDTO sizeAdminDTO : sizeAdminDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(sizeAdminDTO.getSizeNumber());

            // Format createDate and updateDate if they are not null
            String createDateStr = sizeAdminDTO.getCreateDate() != null ? sizeAdminDTO.getCreateDate().format(formatter) : "";
            String updateDateStr = sizeAdminDTO.getUpdateDate() != null ? sizeAdminDTO.getUpdateDate().format(formatter) : "";
            row.createCell(1).setCellValue(createDateStr);
            row.createCell(2).setCellValue(updateDateStr);

            // Convert status to description
            String statusDescription = sizeAdminDTO.getStatus() == 0 ? "Đang hoạt động" : "Ngừng hoạt động";
            row.createCell(3).setCellValue(statusDescription);
        }

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/size/export-data-template")
    public void exportToExcelTemplate(HttpServletResponse response) throws IOException {
        List<SizeAdminDTO> sizeAdminDTOList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Kích Cỡ");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Kích thước");
        headerRow.createCell(1).setCellValue("Trạng thái");

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/size/import-data")
    public String importFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            // Kiểm tra tên của sheet
            String expectedSheetName = "Kích Cỡ"; // Thay đổi tên sheet theo nhu cầu
            Sheet sheet = workbook.getSheet(expectedSheetName);

            if (sheet == null) {
                return "Sheet không tồn tại trong file Excel.";
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Size size = new Size();
                size.setSizeNumber(row.getCell(0).getStringCellValue());
                size.setCreateDate(LocalDateTime.now());
                size.setUpdateDate(LocalDateTime.now());

                // Chuyển đổi mô tả trạng thái thành số nguyên
                String statusDescription = row.getCell(1).getStringCellValue();
                Integer status = "Đang hoạt động".equals(statusDescription) ? 0 : 1;

                size.setStatus(status);

                sizeAdminRepository.save(size);
            }
            workbook.close();
        }
        return "File imported successfully";
    }
}
