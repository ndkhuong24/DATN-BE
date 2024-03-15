package com.example.backend.core.commons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SheetConfigDTO {
    private List<?> list;
    private String[] headers;
    private List<CellConfigDTO> cellConfigList;
    private List<CellConfigDTO> cellCustomList;
    private String sheetName;
    private boolean hasIndex = true;
    private int rowStart = 0;
    private boolean hasBorder;
    private int exportType;
//    private List<DataDTO> dataDTOs;
}
