package com.example.backend.core.commons;

import com.example.backend.core.constant.AppConstant;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class FileExportUtil {

    private static final short BLUE = 4;
    public static final String FONT_TIMES_NEW_ROMAN = "Times New Roman";
    private static final String DATA_FORMAT = "###,##0";
    private static final String PROPERTY_HIDDEN = "hidden";
    private static final String SIMPLE_DATE_FORMAT_NO_TIME = "yyyy-MM-dd";
    private final Logger log = LoggerFactory.getLogger(FileExportUtil.class);
    private static final String RECORD_NO = "recordNo";

    public byte[] exportXLSXTemplate(Boolean isSample, List<SheetConfigDTO> sheetConfigList, String title) throws ReflectiveOperationException, IOException {
        log.info("Export XLSX title : {}", title);
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream bos = new ByteArrayOutputStream();) {

            XSSFSheet sheet = null;
            XSSFFont fontTimeNewRoman = (XSSFFont) workbook.createFont();
            fontTimeNewRoman.setFontName(FONT_TIMES_NEW_ROMAN);
            fontTimeNewRoman.setFontHeightInPoints(Short.parseShort("12"));

            DataFormat format = workbook.createDataFormat();

            CellStyle styleLeft = workbook.createCellStyle(); // Create new style
            styleLeft.setVerticalAlignment(VerticalAlignment.TOP);
            styleLeft.setAlignment(HorizontalAlignment.LEFT);
            styleLeft.setWrapText(true);
            styleLeft.setFont(fontTimeNewRoman);
            styleLeft.setDataFormat(format.getFormat("@"));

            CellStyle styleRightNumber = workbook.createCellStyle(); // Create new style
            styleRightNumber.setVerticalAlignment(VerticalAlignment.TOP);
            styleRightNumber.setAlignment(HorizontalAlignment.RIGHT);
            styleRightNumber.setDataFormat(format.getFormat(DATA_FORMAT));

            CellStyle styleRightDouble = workbook.createCellStyle(); // Create new style
            styleRightDouble.setVerticalAlignment(VerticalAlignment.TOP);
            styleRightDouble.setAlignment(HorizontalAlignment.RIGHT);
            styleRightDouble.setDataFormat(format.getFormat("###,##0.0#"));
            styleRightDouble.setFont(fontTimeNewRoman);

            CellStyle styleCenterNo = workbook.createCellStyle(); // Create new style
            styleCenterNo.setVerticalAlignment(VerticalAlignment.TOP);
            styleCenterNo.setAlignment(HorizontalAlignment.CENTER);
            styleCenterNo.setDataFormat(format.getFormat(DATA_FORMAT));

            CellStyle styleRight = workbook.createCellStyle();
            styleRight.setAlignment(HorizontalAlignment.RIGHT);
            styleRight.setFont(fontTimeNewRoman);

            CellStyle styleTitle = workbook.createCellStyle();
            styleTitle.setAlignment(HorizontalAlignment.CENTER);
            styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleTitle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
            XSSFFont fontTitle = (XSSFFont) workbook.createFont();
            fontTitle.setBold(true);
            fontTitle.setFontName(FONT_TIMES_NEW_ROMAN);
            fontTitle.setFontHeightInPoints(Short.parseShort("12"));
            fontTitle.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            styleTitle.setFont(fontTitle);

            CellStyle styleError = workbook.createCellStyle();
            styleError.setAlignment(HorizontalAlignment.LEFT);
            XSSFFont fontError = (XSSFFont) workbook.createFont();
            fontError.setColor(IndexedColors.RED1.getIndex());
            fontError.setFontName(FONT_TIMES_NEW_ROMAN);
            fontError.setFontHeightInPoints(Short.parseShort("12"));
            styleError.setFont(fontError);
            styleError.setWrapText(true);

            CellStyle styleBorderError = workbook.createCellStyle();
            styleBorderError.setAlignment(HorizontalAlignment.LEFT);
            XSSFFont fontBorderError = (XSSFFont) workbook.createFont();
            styleBorderError.setFont(fontBorderError);
            setBorderError(styleBorderError);

            CellStyle styleBorderErrorNo = workbook.createCellStyle();
            styleBorderErrorNo.setAlignment(HorizontalAlignment.CENTER);
            XSSFFont fontBorderErrorNo = (XSSFFont) workbook.createFont();
            styleBorderErrorNo.setFont(fontBorderErrorNo);
            setBorderError(styleBorderErrorNo);

            CellStyle hyperLinkStyle = workbook.createCellStyle();
            XSSFFont hyperLinkFont = (XSSFFont) workbook.createFont();
            hyperLinkFont.setUnderline(XSSFFont.U_SINGLE);
            hyperLinkFont.setColor(BLUE);
            hyperLinkStyle.setFont(hyperLinkFont);

            CreationHelper createHelper = workbook.getCreationHelper();
            XSSFHyperlink emailHyperLink = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.EMAIL);
            DataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);

            for (SheetConfigDTO sheetConfig : sheetConfigList) {
                if (StringUtils.isNotBlank(sheetConfig.getSheetName())) {
                    String sheetName = sheetConfig.getSheetName();
                    if (StringUtils.isNotBlank(sheetName)) {
                        sheet = workbook.createSheet(sheetName);
                    } else {
                        sheet = workbook.createSheet(sheetConfig.getSheetName());
                    }
                } else {
                    sheet = workbook.createSheet(String.format("Sheet%d", sheetConfigList.indexOf(sheetConfig) + 1));
                }

                if (sheetConfig.isHasBorder()) {
                    addBorder(styleLeft);
                    addBorder(styleRightNumber);
                    addBorder(styleRightDouble);
                    addBorder(styleRight);
                    addBorder(hyperLinkStyle);
                    addBorder(styleError);
                    addBorder(styleCenterNo);
                } else {
                    removeBorder(styleLeft);
                    removeBorder(styleRightNumber);
                    removeBorder(styleRightDouble);
                    removeBorder(styleRight);
                    removeBorder(hyperLinkStyle);
                    addBorder(styleCenterNo);
                }

                int rowStart = sheetConfig.getRowStart();
                int cellStart = 0;

                XSSFRow row;
                XSSFCell cell;
                if (StringUtilsBE.isNotNullOrEmpty(title)) {
                    String titleTrans = title;
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sheetConfig.getHeaders().length - 1));
                    row = sheet.createRow(rowStart++);
                    cell = row.createCell(0);
                    if (StringUtilsBE.isNotNullOrEmpty(titleTrans)) {
                        cell.setCellValue(titleTrans);
                    } else {
                        cell.setCellValue(title);
                    }
                    cell.setCellStyle(styleTitle);
                    rowStart = 2;
                }

                List list = sheetConfig.getList();
                String[] headers = sheetConfig.getHeaders();
                boolean hasIndex = sheetConfig.isHasIndex();
                if (headers != null) {
                    row = sheet.createRow(rowStart++);
                    // write header
                    CellStyle styleHeader = workbook.createCellStyle();
                    if (sheetConfig.getExportType() == AppConstant.EXPORT_DATA) {
                        styleHeader.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
                        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    }
                    styleHeader.setAlignment(HorizontalAlignment.CENTER);
                    styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
                    styleHeader.setWrapText(true);
                    styleHeader.setFont(fontTimeNewRoman);
                    addBorder(styleHeader);

                    XSSFFont fontHeaderBold = (XSSFFont) workbook.createFont();
                    fontHeaderBold.setBold(true);
                    fontHeaderBold.setFontName(FONT_TIMES_NEW_ROMAN);
                    fontHeaderBold.setFontHeightInPoints(Short.parseShort("12"));

                    XSSFFont fontHeaderBoldRed = (XSSFFont) workbook.createFont();
                    fontHeaderBoldRed.setBold(true);
                    fontHeaderBoldRed.setFontName(FONT_TIMES_NEW_ROMAN);
                    fontHeaderBoldRed.setFontHeightInPoints(Short.parseShort("12"));
                    fontHeaderBoldRed.setColor(Font.COLOR_RED);
                    if (hasIndex) {
                        cell = row.createCell(cellStart++);
                        cell.setCellValue("recordNo");
                        cell.setCellStyle(styleHeader);
                    }
                    for (String header : headers) {
                        XSSFRichTextString richTextString = new XSSFRichTextString();
                        cell = row.createCell(cellStart++);
                        String headerStr = header;
                        String[] arrHeader = headerStr.split(Pattern.quote(AppConstant.NEXT_LINE));
                        if (arrHeader.length > 0) {
                            if (arrHeader[0].contains(AppConstant.CHAR_STAR)) {
                                String[] arrStar = arrHeader[0].split(Pattern.quote(AppConstant.CHAR_STAR));
                                richTextString.append(arrStar[0], fontHeaderBold);
                                richTextString.append(AppConstant.CHAR_STAR, fontHeaderBoldRed);
                                richTextString.append(arrStar[1], fontHeaderBold);
                                richTextString.append(AppConstant.NEXT_LINE);
                            } else {
                                richTextString.append(arrHeader[0], fontHeaderBold);
                                richTextString.append(AppConstant.NEXT_LINE);
                            }
                            for (int i = 1; i < arrHeader.length; i++) {
                                richTextString.append(arrHeader[i], fontTimeNewRoman);
                                richTextString.append(AppConstant.NEXT_LINE);
                            }
                        } else {
                            richTextString.append(headerStr, fontHeaderBold);
                        }
                        cell.setCellValue(richTextString);
                        cell.setCellStyle(styleHeader);
                    }
                }
                List<CellConfigDTO> cellConfigList = sheetConfig.getCellConfigList();
                // write content
                for (Object object : list) {
                    List<String> listFieldErr = Arrays.asList(BeanUtils.getArrayProperty(object, "fieldErr"));
                    row = sheet.createRow(rowStart++);
                    cellStart = 0;
                    if (hasIndex) {
                        cell = row.createCell(cellStart++);
                        cell.setCellValue(list.indexOf(object) + 1);
                        cell.setCellStyle(styleRight);
                    }
                    for (CellConfigDTO cellConfig : cellConfigList) {
                        cell = row.createCell(cellStart++);
                        String cellValue = BeanUtils.getProperty(object, cellConfig.getFieldName());
                        String cellValueStr = StringUtilsBE.ifNullToEmpty(cellValue);

                        boolean isHyperLinkEmail = cellConfig.isHyperLinkEmail();
                        if (!StringUtilsBE.isNotNullOrEmpty(cellValueStr) || AppConstant.STRING.equals(cellConfig.getCellType())) {
                            cell.setCellValue(StringUtilsBE.ifNullToEmpty(cellValue));
                            if (isHyperLinkEmail) {
                                emailHyperLink.setAddress(
                                        String.format("mailto:%s?subject=Hyperlink", StringUtilsBE.ifNullToEmpty(cellValue))
                                );
                                cell.setHyperlink(emailHyperLink);
                            }
                        } else if (AppConstant.NUMBER.equals(cellConfig.getCellType())) {
                            cell.setCellValue(Long.parseLong(cellValueStr));
                        } else if (AppConstant.DOUBLE.equals(cellConfig.getCellType())) {
                            cell.setCellValue(Double.parseDouble(cellValueStr));
                        } else if (AppConstant.ERRORS.equals(cellConfig.getCellType())) {
                            cell.setCellValue(cellValueStr);
                            cell.setCellStyle(styleError);
                        } else if (AppConstant.NO.equals(cellConfig.getCellType())) {
                            cell.setCellValue(Long.parseLong(cellValueStr));
                        }

                        if (!AppConstant.ERRORS.equals(cellConfig.getCellType())) {
                            String textAlight = StringUtilsBE.ifNullToEmpty(cellConfig.getTextAlight());
                            if (AppConstant.ALIGN_RIGHT.equals(textAlight)) {
                                if (!cellConfig.isFormatNumber()) {
                                    if (listFieldErr.contains(cellConfig.getFieldName())) {
                                        cell.setCellStyle(styleBorderError);
                                    } else {
                                        cell.setCellStyle(styleRight);
                                    }
                                } else if (AppConstant.NUMBER.equals(cellConfig.getCellType())) {
                                    if (listFieldErr.contains(cellConfig.getFieldName())) {
                                        cell.setCellStyle(styleBorderError);
                                    } else {
                                        cell.setCellStyle(styleRightNumber);
                                    }
                                } else if (AppConstant.DOUBLE.equals(cellConfig.getCellType())) {
                                    if (listFieldErr.contains(cellConfig.getFieldName())) {
                                        cell.setCellStyle(styleBorderError);
                                    } else {
                                        cell.setCellStyle(styleRightDouble);
                                    }
                                } else if (isHyperLinkEmail) {
                                    if (listFieldErr.contains(cellConfig.getFieldName())) {
                                        cell.setCellStyle(styleBorderError);
                                    } else {
                                        cell.setCellStyle(hyperLinkStyle);
                                    }
                                } else if (AppConstant.NO.equals(cellConfig.getCellType())) {
                                    if (listFieldErr.contains(cellConfig.getFieldName())) {
                                        cell.setCellStyle(styleBorderErrorNo);
                                    } else {
                                        cell.setCellStyle(styleCenterNo);
                                    }
                                }
                            } else {
                                if (listFieldErr.contains(cellConfig.getFieldName())) {
                                    cell.setCellStyle(styleBorderError);
                                } else {
                                    cell.setCellStyle(styleLeft);
                                }
                            }
                        }
                    }
                }

                List<CellConfigDTO> cellCustomList = sheetConfig.getCellCustomList();
                if (StringUtilsBE.isListNotNullOrEmpty(cellCustomList)) {
                    int countSheet = 1;
                    for (CellConfigDTO i : cellCustomList) {
                        DataValidation validation = creatDropDownList(sheet, dvHelper, workbook, i);
                        sheet.addValidationData(validation);
                        workbook.setSheetHidden(countSheet++, true);
                    }
                }
                for (int i = 0; i < sheetConfig.getCellConfigList().size(); i++) {
                    sheet.autoSizeColumn(i);
                }
            }

            if (Boolean.FALSE.equals(isSample)) {
                Row r = sheet.getRow(2);
                ExportUtils ex = new ExportUtils();
                for (Cell c : r) {
                    c.setCellStyle(ex.createStyleHeader(workbook));
                }
            }

            try {
                workbook.write(bos);
            } finally {
                bos.close();
            }

            return bos.toByteArray();

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return new byte[0];
    }

    public byte[] exportXLSX(Boolean isSample, List<SheetConfigDTO> sheetConfigList, String title)
            throws IOException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        try (XSSFWorkbook workbook = new XSSFWorkbook();){
            XSSFSheet sheet = null;
            XSSFFont fontTimeNewRoman = (XSSFFont) workbook.createFont();
            fontTimeNewRoman.setFontName(FONT_TIMES_NEW_ROMAN);
            fontTimeNewRoman.setFontHeightInPoints(Short.parseShort("12"));

            DataFormat format = workbook.createDataFormat();

            CellStyle styleLeft = workbook.createCellStyle(); // Create new style
            styleLeft.setVerticalAlignment(VerticalAlignment.TOP);
            styleLeft.setAlignment(HorizontalAlignment.LEFT);
            styleLeft.setWrapText(true);
            styleLeft.setFont(fontTimeNewRoman);
            styleLeft.setDataFormat(format.getFormat("@"));

            CellStyle styleRightNumber = workbook.createCellStyle(); // Create new style
            styleRightNumber.setVerticalAlignment(VerticalAlignment.TOP);
            styleRightNumber.setAlignment(HorizontalAlignment.RIGHT);
            styleRightNumber.setDataFormat(format.getFormat("###,##0"));

            CellStyle styleRightDouble = workbook.createCellStyle(); // Create new style
            styleRightDouble.setVerticalAlignment(VerticalAlignment.TOP);
            styleRightDouble.setAlignment(HorizontalAlignment.RIGHT);
            styleRightDouble.setDataFormat(format.getFormat("###,##0.0#"));
            styleRightDouble.setFont(fontTimeNewRoman);

            CellStyle styleCenterNo = workbook.createCellStyle(); // Create new style
            styleCenterNo.setVerticalAlignment(VerticalAlignment.TOP);
            styleCenterNo.setAlignment(HorizontalAlignment.CENTER);
            styleCenterNo.setDataFormat(format.getFormat("###,##0"));

            CellStyle styleRight = workbook.createCellStyle();
            styleRight.setAlignment(HorizontalAlignment.RIGHT);
            styleRight.setFont(fontTimeNewRoman);

            CellStyle styleCenter = workbook.createCellStyle();
            styleCenter.setAlignment(HorizontalAlignment.CENTER);
            styleCenter.setFont(fontTimeNewRoman);

            CellStyle styleTitle = workbook.createCellStyle();
            styleTitle.setAlignment(HorizontalAlignment.CENTER);
            styleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleTitle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREEN.getIndex());
            XSSFFont fontTitle = (XSSFFont) workbook.createFont();
            fontTitle.setBold(true);
            fontTitle.setFontName(FONT_TIMES_NEW_ROMAN);
            fontTitle.setFontHeightInPoints(Short.parseShort("12"));
            fontTitle.setColor(HSSFColor.HSSFColorPredefined.WHITE.getIndex());
            styleTitle.setFont(fontTitle);

            CellStyle styleError = workbook.createCellStyle();
            styleError.setAlignment(HorizontalAlignment.LEFT);
            XSSFFont fontError = (XSSFFont) workbook.createFont();
            fontError.setColor(IndexedColors.RED1.getIndex());
            fontError.setFontName(FONT_TIMES_NEW_ROMAN);
            fontError.setFontHeightInPoints(Short.parseShort("12"));
            styleError.setFont(fontError);
            styleError.setWrapText(true);

            CellStyle styleBorderError = workbook.createCellStyle();
            styleBorderError.setAlignment(HorizontalAlignment.LEFT);
            XSSFFont fontBorderError = (XSSFFont) workbook.createFont();
            styleBorderError.setFont(fontBorderError);
            setBorderError(styleBorderError);

            CellStyle hyperLinkStyle = workbook.createCellStyle();
            XSSFFont hyperLinkFont = (XSSFFont) workbook.createFont();
            hyperLinkFont.setUnderline(XSSFFont.U_SINGLE);
            hyperLinkFont.setColor(BLUE);
            hyperLinkStyle.setFont(hyperLinkFont);

            CreationHelper createHelper = workbook.getCreationHelper();
            XSSFHyperlink emailHyperLink = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.EMAIL);

            for (SheetConfigDTO sheetConfig : sheetConfigList) {
                if (StringUtilsBE.isNotNullOrEmpty(sheetConfig.getSheetName())) {
                    String sheetName = sheetConfig.getSheetName();
                    if (StringUtilsBE.isNotNullOrEmpty(sheetName)) {
                        sheet = workbook.createSheet(sheetName);
                    } else {
                        sheet = workbook.createSheet(sheetConfig.getSheetName());
                    }
                } else {
                    sheet = workbook.createSheet(String.format("Sheet%d", sheetConfigList.indexOf(sheetConfig) + 1));
                }

                if (sheetConfig.isHasBorder()) {
                    addBorder(styleLeft);
                    addBorder(styleRightNumber);
                    addBorder(styleRightDouble);
                    addBorder(styleRight);
                    addBorder(hyperLinkStyle);
                    addBorder(styleError);
                    addBorder(styleCenterNo);
                } else {
                    removeBorder(styleLeft);
                    removeBorder(styleRightNumber);
                    removeBorder(styleRightDouble);
                    removeBorder(styleRight);
                    removeBorder(hyperLinkStyle);
                    removeBorder(styleCenterNo);
                }

                int rowStart = sheetConfig.getRowStart();
                int cellStart = 0;

                XSSFRow row;
                XSSFCell cell;
                if (StringUtilsBE.isNotNullOrEmpty(title)) {
                    String titleTrans = title;
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sheetConfig.getHeaders().length - 1));
                    row = sheet.createRow(rowStart++);
                    cell = row.createCell(0);
                    if (StringUtilsBE.isNotNullOrEmpty(titleTrans)) {
                        cell.setCellValue(titleTrans);
                    } else {
                        cell.setCellValue(title);
                    }
                    cell.setCellStyle(styleTitle);
                    rowStart = 2;
                }

                List list = sheetConfig.getList();
                String[] headers = sheetConfig.getHeaders();
                boolean hasIndex = sheetConfig.isHasIndex();
                if (headers != null) {
                    row = sheet.createRow(rowStart++);
                    // write header
                    CellStyle styleHeader = workbook.createCellStyle();
                    if (sheetConfig.getExportType() == AppConstant.EXPORT_DATA) {
                        styleHeader.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
                        styleHeader.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                    }
                    styleHeader.setAlignment(HorizontalAlignment.CENTER);
                    styleHeader.setVerticalAlignment(VerticalAlignment.CENTER);
                    styleHeader.setWrapText(true);
                    styleHeader.setFont(fontTimeNewRoman);
                    addBorder(styleHeader);

                    XSSFFont fontHeaderBold = (XSSFFont) workbook.createFont();
                    fontHeaderBold.setBold(true);
                    fontHeaderBold.setFontName(FONT_TIMES_NEW_ROMAN);
                    fontHeaderBold.setFontHeightInPoints(Short.parseShort("12"));

                    XSSFFont fontHeaderBoldRed = (XSSFFont) workbook.createFont();
                    fontHeaderBoldRed.setBold(true);
                    fontHeaderBoldRed.setFontName(FONT_TIMES_NEW_ROMAN);
                    fontHeaderBoldRed.setFontHeightInPoints(Short.parseShort("12"));
                    fontHeaderBoldRed.setColor(Font.COLOR_RED);
                    if (hasIndex) {
                        cell = row.createCell(cellStart++);
                        cell.setCellValue(RECORD_NO);
                        cell.setCellStyle(styleHeader);
                    }
                    for (String header : headers) {
                        XSSFRichTextString richTextString = new XSSFRichTextString();
                        cell = row.createCell(cellStart++);
                        String headerStr = header;
                        String[] arrHeader = headerStr.split(Pattern.quote(AppConstant.NEXT_LINE));
                        if (arrHeader.length > 1) {
                            if (arrHeader[0].contains(AppConstant.CHAR_STAR)) {
                                String[] arrStar = arrHeader[0].split(Pattern.quote(AppConstant.CHAR_STAR));
                                richTextString.append(arrStar[0], fontHeaderBold);
                                richTextString.append(AppConstant.CHAR_STAR, fontHeaderBoldRed);
                                richTextString.append(arrStar[1], fontHeaderBold);
                                richTextString.append(AppConstant.NEXT_LINE);
                            } else {
                                richTextString.append(arrHeader[0], fontHeaderBold);
                                richTextString.append(AppConstant.NEXT_LINE);
                            }
                            for (int i = 1; i < arrHeader.length; i++) {
                                richTextString.append(arrHeader[i], fontTimeNewRoman);
                                richTextString.append(AppConstant.NEXT_LINE);
                            }
                        } else {
                            richTextString.append(headerStr, fontHeaderBold);
                        }
                        cell.setCellValue(richTextString);
                        cell.setCellStyle(styleHeader);
                    }
                }
                List<CellConfigDTO> cellConfigList = sheetConfig.getCellConfigList();
                // write content
                for (Object object : list) {
                    List<String> listFieldErr = Arrays.asList(BeanUtils.getArrayProperty(object, "fieldErr"));
                    row = sheet.createRow(rowStart++);
                    cellStart = 0;
                    if (hasIndex) {
                        cell = row.createCell(cellStart++);
                        cell.setCellValue(list.indexOf(object) + 1);
                        cell.setCellStyle(styleRight);
                    }
                    for (CellConfigDTO cellConfig : cellConfigList) {
                        cell = row.createCell(cellStart++);
                        String cellValue = BeanUtils.getProperty(object, cellConfig.getFieldName());
                        String cellValueStr = StringUtilsBE.ifNullToEmpty(cellValue);

                        boolean isHyperLinkEmail = cellConfig.isHyperLinkEmail();
                        if (
                                !StringUtilsBE.isNotNullOrEmpty(cellValueStr) ||
                                        AppConstant.STRING.equals(cellConfig.getCellType())
                        ) {
                            cell.setCellValue(StringUtilsBE.ifNullToEmpty(cellValue));
                            if (isHyperLinkEmail) {
                                emailHyperLink.setAddress(
                                        String.format(
                                                "mailto:%s?subject=Hyperlink",
                                                StringUtilsBE.ifNullToEmpty(cellValue)
                                        )
                                );
                                cell.setHyperlink(emailHyperLink);
                            }
                        } else if (AppConstant.NUMBER.equals(cellConfig.getCellType())) {
                            cell.setCellValue(Long.valueOf(cellValueStr));
                        } else if (AppConstant.DOUBLE.equals(cellConfig.getCellType())) {
                            cell.setCellValue(Double.valueOf(cellValueStr));
                        } else if (AppConstant.ERRORS.equals(cellConfig.getCellType())) {
                            cell.setCellValue(cellValueStr);
                            cell.setCellStyle(styleError);
                        } else if (AppConstant.NO.equals(cellConfig.getCellType())) {
                            cell.setCellValue(Long.valueOf(cellValueStr));
                        }

                        if (!AppConstant.ERRORS.equals(cellConfig.getCellType())) {
                            String textAlight = StringUtilsBE.ifNullToEmpty(cellConfig.getTextAlight());
                            switch (textAlight) {
                                case AppConstant.ALIGN_RIGHT:
                                    if (!cellConfig.isFormatNumber()) {
                                        if (listFieldErr.contains(cellConfig.getFieldName())) {
                                            cell.setCellStyle(styleBorderError);
                                        } else {
                                            cell.setCellStyle(styleRight);
                                        }
                                    } else if (AppConstant.NUMBER.equals(cellConfig.getCellType())) {
                                        if (listFieldErr.contains(cellConfig.getFieldName())) {
                                            cell.setCellStyle(styleBorderError);
                                        } else {
                                            cell.setCellStyle(styleRightNumber);
                                        }
                                    } else if (AppConstant.DOUBLE.equals(cellConfig.getCellType())) {
                                        if (listFieldErr.contains(cellConfig.getFieldName())) {
                                            cell.setCellStyle(styleBorderError);
                                        } else {
                                            cell.setCellStyle(styleRightDouble);
                                        }
                                    } else if (isHyperLinkEmail) {
                                        if (listFieldErr.contains(cellConfig.getFieldName())) {
                                            cell.setCellStyle(styleBorderError);
                                        } else {
                                            cell.setCellStyle(hyperLinkStyle);
                                        }
                                    } else if (AppConstant.NO.equals(cellConfig.getCellType())) {
                                        if (listFieldErr.contains(cellConfig.getFieldName())) {
                                            cell.setCellStyle(styleBorderError);
                                        } else {
                                            cell.setCellStyle(styleCenterNo);
                                        }
                                    }
                                    break;
                                default:
                                    if (listFieldErr.contains(cellConfig.getFieldName())) {
                                        cell.setCellStyle(styleBorderError);
                                    } else {
                                        cell.setCellStyle(styleLeft);
                                    }
                                    break;
                            }
                        }
                    }
                }
                for (int i = 0; i < sheetConfig.getCellConfigList().size(); i++) {
                    sheet.autoSizeColumn(i);
                }
            }
            if (Boolean.FALSE.equals(isSample)) {
                Row r = sheet.getRow(2);
                ExportUtils ex = new ExportUtils();
                for (Cell c : r) {
                    c.setCellStyle(ex.createStyleHeader(workbook));
                }
            }

            for (int i = 1; i <= 3; i++) {
                sheet.setColumnWidth(i, 10000);
            }

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                workbook.write(bos);
            } finally {
                bos.close();
            }
            return bos.toByteArray();

        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }

        return new byte[0];
    }


    private void setBorderError(CellStyle style) {
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setLeftBorderColor(IndexedColors.RED.getIndex());
        style.setRightBorderColor(IndexedColors.RED.getIndex());
        style.setTopBorderColor(IndexedColors.RED.getIndex());
        style.setBottomBorderColor(IndexedColors.RED.getIndex());
    }

    private static DataValidation creatDropDownList(
            XSSFSheet taskInfoSheet,
            DataValidationHelper helper,
            XSSFWorkbook book,
            CellConfigDTO dto
    ) {
        XSSFSheet hidden = book.createSheet(PROPERTY_HIDDEN + dto.getFieldName());
        Cell cell = null;
        for (int i = 0, length = dto.getArrData().length; i < length; i++) {
            String name = dto.getArrData()[i];
            Row row = hidden.createRow(i);
            cell = row.createCell(0);
            cell.setCellValue(name);
        }

        Name namedCell = book.createName();
        namedCell.setNameName(PROPERTY_HIDDEN + dto.getFieldName());
        namedCell.setRefersToFormula(PROPERTY_HIDDEN + dto.getFieldName() + "!$A$1:$A$" + dto.getArrData().length);
        DataValidationConstraint constraint = helper.createFormulaListConstraint(PROPERTY_HIDDEN + dto.getFieldName());
        CellRangeAddressList addressList = new CellRangeAddressList(
                dto.getFirstRow(),
                dto.getLastRow(),
                dto.getFirstCol(),
                dto.getLastCol()
        );
        DataValidation validation = helper.createValidation(constraint, addressList);
        validation.setShowErrorBox(true);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        // The second sheet is set to hide
        if (null != validation) {
            taskInfoSheet.addValidationData(validation);
        }
        return validation;
    }

    private void addBorder(CellStyle style) {
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
    }

    private void removeBorder(CellStyle style) {
        style.setBorderBottom(BorderStyle.NONE);
        style.setBorderTop(BorderStyle.NONE);
        style.setBorderRight(BorderStyle.NONE);
        style.setBorderLeft(BorderStyle.NONE);
    }

    public ResponseEntity<?> responseFileExportWithUtf8FileName(byte[] fileData, String fileName, String contentType) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
        InputStreamResource resource = new InputStreamResource(inputStream);
        try {
            fileName = URLEncoder.encode(fileName, AppConstant.ENCODING_UTF8);
        } catch (IOException ioE) {
            log.error("An exception occured when reponse file to client" + ioE.getMessage());
        }
        return ResponseEntity
                .ok()
                .contentType(MediaType.parseMediaType(contentType))
                .contentLength(inputStream.available())
                .header("filename", fileName)
                .body(resource);
    }


}
