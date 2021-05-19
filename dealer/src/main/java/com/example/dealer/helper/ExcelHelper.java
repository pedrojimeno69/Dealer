package com.example.dealer.helper;

import com.example.dealer.entity.CarEntity;
import com.example.dealer.exception.GenerateExcelException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.List;

public class ExcelHelper {

    private static final Integer ROW_NUMBER_INIT = 2;
    private static final String SHEET_NAME = "Cars";

    public static byte[] generate(List <CarEntity> carEntities) throws GenerateExcelException {
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = generateSheet(workbook);

        generateHeader(workbook, sheet);

        fillData(workbook, sheet, carEntities);

        byte [] bytes = writeWorkBook(workbook);

        return bytes;
    }

    private static Sheet generateSheet(Workbook workbook) {
        Sheet sheet = workbook.createSheet(SHEET_NAME);
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 6000);
        sheet.setColumnWidth(2, 4000);
        return sheet;
    }

    private static void generateHeader(Workbook workbook, Sheet sheet) {
        CellStyle headerStyle = createHeaderStyle(workbook);

        Row header = sheet.createRow(0);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Model Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("Color");
        headerCell.setCellStyle(headerStyle);
    }

    private static void fillData(Workbook workbook, Sheet sheet, List<CarEntity> carEntities) {
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);

        for (int i = 0; i < carEntities.size(); i++){
            Row row = sheet.createRow(ROW_NUMBER_INIT + i);

            Cell cell = row.createCell(0);
            cell.setCellValue(carEntities.get(i).getId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(carEntities.get(i).getModelName());
            cell.setCellStyle(style);

            cell = row.createCell(2);
            cell.setCellValue(carEntities.get(i).getColor());
            cell.setCellStyle(style);
        }
    }

    private static CellStyle createHeaderStyle(Workbook workbook){
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 14);
        font.setBold(true);
        headerStyle.setFont(font);

        return headerStyle;
    }

    private static byte[] writeWorkBook(Workbook workbook) throws GenerateExcelException {

        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayInputStream);
            workbook.close();
        } catch (IOException e) {
            throw new GenerateExcelException("Internal Error generating excel");
        }
        return byteArrayInputStream.toByteArray();
    }
}
