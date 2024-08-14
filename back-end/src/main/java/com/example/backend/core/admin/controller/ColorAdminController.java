package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.admin.repository.ColorAdminRepository;
import com.example.backend.core.admin.service.ColorAdminService;
import com.example.backend.core.model.Color;
import com.example.backend.core.model.Sole;
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
public class ColorAdminController {
    @Autowired
    private ColorAdminService colorAdminService;

    @Autowired
    private ColorAdminRepository colorAdminRepository;

    @GetMapping("/color/hien-thi")
    public ResponseEntity<List<ColorAdminDTO>> index() {
        return ResponseEntity.ok(colorAdminService.getAll());
    }

    @PostMapping("/color/add")
    public ResponseEntity<?> add(@RequestBody ColorAdminDTO colorAdminDTO) {
        return ResponseEntity.ok(colorAdminService.add(colorAdminDTO));
    }

    @PutMapping("/color/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ColorAdminDTO colorAdminDTO) {
        return ResponseEntity.ok(colorAdminService.update(colorAdminDTO, id));
    }

    @DeleteMapping("/color/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(colorAdminService.delete(id));
    }

    @GetMapping("/color/export-data")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<ColorAdminDTO> colorAdminDTOList = colorAdminService.getAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Màu Sắc");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã màu");
        headerRow.createCell(1).setCellValue("Tên màu");
        headerRow.createCell(2).setCellValue("Ngày tạo");
        headerRow.createCell(3).setCellValue("Ngày cập nhật");
        headerRow.createCell(4).setCellValue("Trạng thái");

        // Create a date-time formatter for the Excel output
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Create data rows
        int rowNum = 1;
        for (ColorAdminDTO colorAdminDTO : colorAdminDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(colorAdminDTO.getCode());
            row.createCell(1).setCellValue(colorAdminDTO.getName());

            // Format createDate and updateDate if they are not null
            String createDateStr = colorAdminDTO.getCreateDate() != null ? colorAdminDTO.getCreateDate().format(formatter) : "";
            String updateDateStr = colorAdminDTO.getUpdateDate() != null ? colorAdminDTO.getUpdateDate().format(formatter) : "";
            row.createCell(2).setCellValue(createDateStr);
            row.createCell(3).setCellValue(updateDateStr);

            // Convert status to description
            String statusDescription = colorAdminDTO.getStatus() == 0 ? "Đang hoạt động" : "Ngừng hoạt động";
            row.createCell(4).setCellValue(statusDescription);
        }

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/color/export-data-template")
    public void exportToExcelTemplate(HttpServletResponse response) throws IOException {
        List<SoleAdminDTO> soleAdminDTOList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Màu Sắc");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mã màu");
        headerRow.createCell(1).setCellValue("Tên màu");
        headerRow.createCell(2).setCellValue("Trạng thái");

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/color/import-data")
    public String importFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            // Kiểm tra tên của sheet
            String expectedSheetName = "Màu Sắc"; // Thay đổi tên sheet theo nhu cầu
            Sheet sheet = workbook.getSheet(expectedSheetName);

            if (sheet == null) {
                return "Sheet không tồn tại trong file Excel.";
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Color color = new Color();
                color.setCode(row.getCell(0).getStringCellValue());
                color.setName(row.getCell(1).getStringCellValue());
                color.setCreateDate(LocalDateTime.now());
                color.setUpdateDate(LocalDateTime.now());

                // Chuyển đổi mô tả trạng thái thành số nguyên
                String statusDescription = row.getCell(2).getStringCellValue();
                Integer status = "Đang hoạt động".equals(statusDescription) ? 0 : 1;

                color.setStatus(status);

                colorAdminRepository.save(color);
            }
            workbook.close();
        }
        return "File imported successfully";
    }
}
