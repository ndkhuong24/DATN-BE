package com.example.backend.core.admin.controller;

import com.example.backend.core.admin.dto.SoleAdminDTO;
import com.example.backend.core.admin.repository.SoleAdminRepository;
import com.example.backend.core.admin.service.SoleAdminService;
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
public class SoleAdminController {
    @Autowired
    private SoleAdminService soleAdminService;

    @Autowired
    private SoleAdminRepository soleAdminRepository;

    @GetMapping("sole/hien-thi")
    public ResponseEntity<List<SoleAdminDTO>> hienthi() {
        return ResponseEntity.ok(soleAdminService.getAll());
    }

    @PostMapping("sole/add")
    public ResponseEntity<?> add(@RequestBody SoleAdminDTO soleAdminDTO) {
        return ResponseEntity.ok(soleAdminService.add(soleAdminDTO));
    }

    @PutMapping("sole/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody SoleAdminDTO soleAdminDTO) {
        return ResponseEntity.ok(soleAdminService.update(soleAdminDTO, id));
    }

    @DeleteMapping("sole/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        return ResponseEntity.ok(soleAdminService.delete(id));
    }

    @GetMapping("/sole/export-data")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<SoleAdminDTO> soleAdminDTOList = soleAdminService.getAll();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Đế Giày");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mô tả");
        headerRow.createCell(1).setCellValue("Chiều cao đế");
        headerRow.createCell(2).setCellValue("Chất liệu đế");
        headerRow.createCell(3).setCellValue("Ngày tạo");
        headerRow.createCell(4).setCellValue("Ngày cập nhật");
        headerRow.createCell(5).setCellValue("Trạng thái");

        // Create a date-time formatter for the Excel output
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Create data rows
        int rowNum = 1;
        for (SoleAdminDTO soleAdminDTO : soleAdminDTOList) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(soleAdminDTO.getDescription());
            row.createCell(1).setCellValue(soleAdminDTO.getSoleHeight());
            row.createCell(2).setCellValue(soleAdminDTO.getSoleMaterial());

            // Format createDate and updateDate if they are not null
            String createDateStr = soleAdminDTO.getCreateDate() != null ? soleAdminDTO.getCreateDate().format(formatter) : "";
            String updateDateStr = soleAdminDTO.getUpdateDate() != null ? soleAdminDTO.getUpdateDate().format(formatter) : "";
            row.createCell(3).setCellValue(createDateStr);
            row.createCell(4).setCellValue(updateDateStr);

            // Convert status to description
            String statusDescription = soleAdminDTO.getStatus() == 0 ? "Đang hoạt động" : "Ngừng hoạt động";
            row.createCell(5).setCellValue(statusDescription);
        }

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/sole/export-data-template")
    public void exportToExcelTemplate(HttpServletResponse response) throws IOException {
        List<SoleAdminDTO> soleAdminDTOList = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Đế Giày");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Mô tả");
        headerRow.createCell(1).setCellValue("Chiều cao đế");
        headerRow.createCell(2).setCellValue("Chất liệu đế");
        headerRow.createCell(3).setCellValue("Trạng thái");

        // Write to response output stream
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=soles.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    //    @PostMapping("sole/import-data")
//    public String importFromExcel(@RequestParam("file") MultipartFile file) throws IOException {
//        try (InputStream inputStream = file.getInputStream()) {
//            Workbook workbook = new XSSFWorkbook(inputStream);
//            // Kiểm tra tên của sheet
//            String expectedSheetName = "Đế Giày"; // Thay đổi tên sheet theo nhu cầu
//            Sheet sheet = workbook.getSheet(expectedSheetName);
//
//            if (sheet == null) {
//                return "Sheet không tồn tại trong file Excel.";
//            }
//
//            for (Row row : sheet) {
//                if (row.getRowNum() == 0) continue; // Bỏ qua dòng tiêu đề
//                Sole sole = new Sole();
//                sole.setDescription(row.getCell(0).getStringCellValue());
//                sole.setSoleHeight(row.getCell(1).getStringCellValue());
//                sole.setSoleMaterial(row.getCell(2).getStringCellValue());
//                sole.setCreateDate(LocalDateTime.now());
//                sole.setUpdateDate(LocalDateTime.now());
//
//                // Chuyển đổi mô tả trạng thái thành số nguyên
//                String statusDescription = row.getCell(3).getStringCellValue();
//                Integer status = "Đang hoạt động".equals(statusDescription) ? 0 : 1;
//
//                sole.setStatus(status);
//
//                soleAdminRepository.save(sole);
//            }
//            workbook.close();
//        }
//        return "File imported successfully";
//    }
    @PostMapping("sole/import-data")
    public String importFromExcel(@RequestParam("file") MultipartFile file) {
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            // Kiểm tra tên của sheet
            String expectedSheetName = "Đế Giày";
            Sheet sheet = workbook.getSheet(expectedSheetName);

            if (sheet == null) {
                return "Sheet không tồn tại trong file Excel.";
            }

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                // Kiểm tra các ô trong mỗi dòng
                if (row.getCell(0) == null || row.getCell(1) == null ||
                        row.getCell(2) == null || row.getCell(3) == null) {
//                    return "Dòng " + (row.getRowNum() + 1) + " có giá trị null trong file Excel.";
                    return "Có giá trị null trong file Excel.";
                }

                Sole sole = new Sole();
                sole.setDescription(row.getCell(0).getStringCellValue());
                sole.setSoleHeight(row.getCell(1).getStringCellValue());
                sole.setSoleMaterial(row.getCell(2).getStringCellValue());
                sole.setCreateDate(LocalDateTime.now());
                sole.setUpdateDate(LocalDateTime.now());

                // Chuyển đổi mô tả trạng thái thành số nguyên
                String statusDescription = row.getCell(3).getStringCellValue();
                Integer status = "Đang hoạt động".equals(statusDescription) ? 0 : 1;

                sole.setStatus(status);

                soleAdminRepository.save(sole);
            }

        } catch (IOException e) {
            return "Lỗi khi đọc file Excel.";
        } catch (Exception e) {
            return "Lỗi trong quá trình nhập dữ liệu.";
        }
        return "File imported successfully";
    }
}
