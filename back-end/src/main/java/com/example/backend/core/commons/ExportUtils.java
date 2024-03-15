package com.example.backend.core.commons;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class ExportUtils {
    private final Logger log = LoggerFactory.getLogger(ExportUtils.class);
    private final DecimalFormat doubleFormat = new DecimalFormat("#.##");
    private static final String MSG_CODE_UNREGISTERED = "_Unregistered";
    private static final String FONT_TIMES_NEW_ROMAN = "Times New Roman";

    public CellStyle createStyleHeader(Workbook workbook) {
        CellStyle cellStyleHeader = createCellStyleHeader(workbook);
        Font hSSFFontHeader = createFontHeader(workbook);
        hSSFFontHeader.setColor(IndexedColors.BLACK.index);
        cellStyleHeader.setFont(hSSFFontHeader);
        return cellStyleHeader;
    }

    private CellStyle createCellStyleHeader(Workbook workbook) {
        CellStyle cellStyleHeader = workbook.createCellStyle();
        cellStyleHeader.setAlignment(HorizontalAlignment.CENTER);
        cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleHeader.setBorderLeft(BorderStyle.THIN);
        cellStyleHeader.setBorderBottom(BorderStyle.THIN);
        cellStyleHeader.setBorderRight(BorderStyle.THIN);
        cellStyleHeader.setBorderTop(BorderStyle.THIN);
        cellStyleHeader.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        cellStyleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleHeader.setWrapText(true);
        return cellStyleHeader;
    }
    private Font createFontHeader(Workbook workbook) {
        Font hSSFFontHeader = workbook.createFont();
        hSSFFontHeader.setFontName(HSSFFont.FONT_ARIAL);
        hSSFFontHeader.setFontHeightInPoints((short) 10);
        hSSFFontHeader.setBold(true);
        return hSSFFontHeader;
    }
}
