package com.example.backend.core.commons;


import com.example.backend.core.constant.AppConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CellConfigDTO {

    private String fieldName;
    private String textAlight = AppConstant.ALIGN_LEFT;
    private String cellType = AppConstant.STRING;
    private boolean hyperLinkEmail;
    private boolean formatNumber = Boolean.TRUE;
    private Integer row;
    private Integer colmn;
    private String cellValue;
    private int firstRow;
    private int lastRow;
    private int firstCol;
    private int lastCol;
    private String[] arrData;

    public CellConfigDTO(String fieldName) {
        this.fieldName = fieldName;
    }

    public CellConfigDTO(String fieldName, String textAlight, String cellType ) {
        super();
        this.fieldName = fieldName;
        this.textAlight = textAlight;
        this.cellType = cellType;
    }

    public CellConfigDTO(String fieldName, String textAlight, String cellType, Boolean hyperLinkEmail ) {
        super();
        this.fieldName = fieldName;
        this.textAlight = textAlight;
        this.cellType = cellType;
        this.hyperLinkEmail = hyperLinkEmail;
    }

    public CellConfigDTO(String fieldName, String textAlight, String[] arrData, int firstRow, int lastRow, int firstCol, int lastCol ) {
        super();
        this.fieldName = fieldName;
        this.textAlight = textAlight;
        this.arrData = arrData;
        this.firstRow = firstRow;
        this.lastRow = lastRow;
        this.firstCol = firstCol;
        this.lastCol = lastCol;
    }

    public CellConfigDTO(String fieldName, String textAlight, String cellType, boolean formatNumber ) {
        this.fieldName = fieldName;
        this.textAlight = textAlight;
        this.cellType = cellType;
        this.formatNumber = formatNumber;
    }

    public CellConfigDTO(Integer row, Integer colmn, String textAlight, String cellValue, String cellType) {
        this.row = row;
        this.colmn = colmn;
        this.cellValue = cellValue;
        this.textAlight = textAlight;
        this.cellType = cellType;
    }

    public CellConfigDTO(Integer row, Integer colmn, String textAlight, String cellValue, String cellType,
                         boolean formatNumber) {
        this.row = row;
        this.colmn = colmn;
        this.cellValue = cellValue;
        this.textAlight = textAlight;
        this.cellType = cellType;
        this.formatNumber = formatNumber;
    }
}
