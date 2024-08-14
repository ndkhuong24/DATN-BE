package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.ColorAdminDTO;
import com.example.backend.core.admin.dto.MaterialAdminDTO;
import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.admin.repository.MaterialAdminRepository;
import com.example.backend.core.admin.service.MaterialAdminService;
import com.example.backend.core.model.Color;
import com.example.backend.core.model.Material;
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
public class MaterialAdminController {
    @Autowired
    private MaterialAdminService materialAdminService;

    @Autowired
    private MaterialAdminRepository materialAdminRepository;

    @GetMapping("material/hien-thi")
    public ResponseEntity<List<MaterialAdminDTO>> hienthi() {
        return ResponseEntity.ok(materialAdminService.getAll());
    }

    @PostMapping("material/add")
    public ResponseEntity<?> add(@RequestBody MaterialAdminDTO materialAdminDTO) {
        return ResponseEntity.ok(materialAdminService.add(materialAdminDTO));
    }

    @PutMapping("material/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody MaterialAdminDTO materialAdminDTO) {
        return ResponseEntity.ok(materialAdminService.update(materialAdminDTO, id));
    }

    @DeleteMapping("material/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(materialAdminService.delete(id));
    }

    @GetMapping("/material/export-data")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<MaterialAdminDTO> materialAdminDTOList = materialAdminService.getAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Chất Liệu");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Tên chất liệu");
        headerRow.createCell(1).setCellValue("Mô tả");
        headerRow.createCell(2).setCellValue("Ngày tạo");
        headerRow.createCell(3).setCellValue("Ngày cập nhật");
        headerRow.createCell(4).setCellValue("Trạng thái");

        // Create a date-time formatter for the Excel output
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Create data rows
        int rowNum = 1;
        for (MaterialAdminDTO materialAdminDTO : materialAdminDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(materialAdminDTO.getName());
            row.createCell(1).setCellValue(materialAdminDTO.getDescription());

            // Format createDate and updateDate if they are not null
            String createDateStr = materialAdminDTO.getCreateDate() != null ? materialAdminDTO.getCreateDate().format(formatter) : "";
            String updateDateStr = materialAdminDTO.getUpdateDate() != null ? materialAdminDTO.getUpdateDate().format(formatter) : "";
            row.createCell(2).setCellValue(createDateStr);
            row.createCell(3).setCellValue(updateDateStr);

            // Convert status to description
            String statusDescription = materialAdminDTO.getStatus() == 0 ? "Đang hoạt động" : "Ngừng hoạt động";
            row.createCell(4).setCellValue(statusDescription);
        }

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/material/export-data-template")
    public void exportToExcelTemplate(HttpServletResponse response) throws IOException {
        List<SoleAdminDTO> soleAdminDTOList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Chất Liệu");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Tên chất liệu");
        headerRow.createCell(1).setCellValue("Mô tả");
        headerRow.createCell(2).setCellValue("Trạng thái");

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @PostMapping("/material/import-data")
    public String importFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = new XSSFWorkbook(inputStream);
            // Kiểm tra tên của sheet
            String expectedSheetName = "Chất Liệu"; // Thay đổi tên sheet theo nhu cầu
            Sheet sheet = workbook.getSheet(expectedSheetName);

            if (sheet == null) {
                return "Sheet không tồn tại trong file Excel.";
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;
                Material material = new Material();
                material.setName(row.getCell(0).getStringCellValue());
                material.setDescription(row.getCell(1).getStringCellValue());
                material.setCreateDate(LocalDateTime.now());
                material.setUpdateDate(LocalDateTime.now());

                // Chuyển đổi mô tả trạng thái thành số nguyên
                String statusDescription = row.getCell(2).getStringCellValue();
                Integer status = "Đang hoạt động".equals(statusDescription) ? 0 : 1;

                material.setStatus(status);

                materialAdminRepository.save(material);
            }
            workbook.close();
        }
        return "File imported successfully";
    }
}
