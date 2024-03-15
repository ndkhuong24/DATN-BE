package com.example.backend.core.commons;

import com.example.backend.core.constant.AppConstant;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class FileImportUtil {

    public static boolean checkValidFileFormat(String filename) {
        String fileExt = "7z,rar,zip,txt,ppt,pptx,doc,docx,xls,xlsx,pdf,jpg,jpeg,png,bmp,gif";
        int lastIndexOf = filename.lastIndexOf(".");
        if (lastIndexOf == -1 || lastIndexOf == filename.length()-1) {
            return false;
        }
        List<String> lstValidFileExt = Arrays.asList(fileExt.split(","));
        List<String> lstFileName = Arrays.asList(filename.substring(lastIndexOf+1));
        if(lstFileName.size()<1)
            return false;
        for(int i = 0; i < lstFileName.size();i++) {
            if(!lstValidFileExt.contains(lstFileName.get(i)))
                return false;
        }
        return true;
    }

    public static boolean isNotNullOrEmpty( MultipartFile files ) {
        return files != null && StringUtils.isNotBlank(files.getOriginalFilename());
    }

    public static List<List<String>> excelReader( MultipartFile excelFile ) throws IOException {
        List<List<String>> datas = new ArrayList<>();
        InputStream inputStream = excelFile.getInputStream();
        Workbook workbook;
        workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();

        Iterator<Row> iterator = sheet.iterator();
        while( iterator.hasNext() ) {
            Row currentRow = iterator.next();
            List<String> cells = new ArrayList<>();

            for( int i = 0; i < currentRow.getLastCellNum(); i++ ) {
                Cell currCell = currentRow.getCell(i);
                String cellVal;
                if( currCell != null ) {
                    if( currCell.getCellType() == CellType.NUMERIC ) {
                        cellVal = formatter.formatCellValue(currCell);
                        cellVal = cellVal.replaceAll(AppConstant.COMMA_DELIMITER, StringUtils.EMPTY);
                    } else {
                        cellVal = formatter.formatCellValue(currCell);
                    }
                } else {
                    cellVal = StringUtils.EMPTY;
                }

                cells.add(cellVal);
            }

            datas.add(cells);
        }

        return datas;
    }

}
