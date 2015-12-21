package vmag.action.xlsx;

import vmag.Log;
import vmag.model.Kvartyry;
import vmag.model.SubRub;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCreationHelper;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by Mykhaylo_Mikus on 2/27/2015 2:41 PM.
 */

public class BookCreator {
    private Map<SubRub, List<Kvartyry>> map;

    public BookCreator(Map<SubRub, List<Kvartyry>> map) {
        this.map = map;
    }

    public void save(String fn) throws IOException, InvalidFormatException {
        Log.info("try save to '" + fn + "'");
        FileOutputStream fileOut = new FileOutputStream(new File(fn));
        Workbook workbook = new XSSFWorkbook();
        putCatalog(workbook.createSheet("catalog"));
        putTelList(workbook.createSheet("telList"));
        workbook.write(fileOut);
        fileOut.close();
        Log.info("save to '" + fn + "' success");
    }

    private void putTelList(Sheet sheet) {
        int rowIndex = 0;
        int colIndex = 0;
        Row newRow = sheet.createRow(rowIndex);
        Cell newCell = newRow.createCell(colIndex);
        newCell.setCellValue("Ok");
    }

    private void putCatalog(Sheet sheet) {
        int rowIndex = 0;
        int colIndex = 0;
        putCatalogHeader(sheet);
        for (Map.Entry<SubRub, List<Kvartyry>> e : map.entrySet()) {
            SubRub sr = e.getKey();
            for (Kvartyry kvartyry : e.getValue()) {
                Row row = sheet.createRow(++rowIndex);
                putSubRubCell(sheet, row, sr, colIndex);
                putKvartyryRow(row, kvartyry);
            }
        }

    }

    private void putKvartyryRow(Row row, Kvartyry kvartyry) {
        int cellIndex = 1;
        row.createCell(cellIndex++).setCellValue(kvartyry.getTitle());
        row.createCell(cellIndex++).setCellValue(kvartyry.getPloshcha());
        row.createCell(cellIndex++).setCellValue(kvartyry.getUsd());
        row.createCell(cellIndex++).setCellValue(kvartyry.getUsdm());
        row.createCell(cellIndex++).setCellValue(kvartyry.getHrn());
        row.createCell(cellIndex++).setCellValue(kvartyry.getPoverh());
        row.createCell(cellIndex++).setCellValue(kvartyry.getStina());
        row.createCell(cellIndex++).setCellValue(kvartyry.getCount());
        row.createCell(cellIndex++).setCellValue(kvartyry.getChastynaKvartyry());
        row.createCell(cellIndex++).setCellValue(kvartyry.getText());
        row.createCell(cellIndex++).setCellValue(kvartyry.getTels().toString());
        row.createCell(cellIndex++).setCellValue(kvartyry.getFormulaZyizdRozyizd());
    }

    private void putCatalogHeader(Sheet sheet) {
        Row newRow = sheet.createRow(0);
        int colIndex = 0;
        putHeaderCell(sheet, newRow, "SubRub", colIndex++, 5);
        putHeaderCell(sheet, newRow, "title", colIndex++, 10);
        putHeaderCell(sheet, newRow, "ploshcha", colIndex++, 4);
        putHeaderCell(sheet, newRow, "usd", colIndex++, 2);
        putHeaderCell(sheet, newRow, "usdm", colIndex++, 2);
        putHeaderCell(sheet, newRow, "hrn", colIndex++, 2);
        putHeaderCell(sheet, newRow, "poverh", colIndex++, 2);
        putHeaderCell(sheet, newRow, "stina", colIndex++, 3);
        putHeaderCell(sheet, newRow, "count", colIndex++, 2);
        putHeaderCell(sheet, newRow, "chastynaKvartyry", colIndex++, 3);
        putHeaderCell(sheet, newRow, "text", colIndex++, 20);
        putHeaderCell(sheet, newRow, "tels", colIndex++, 10);
        putHeaderCell(sheet, newRow, "formulaZyizdRozyizd", colIndex++, 3);
    }

    private void putHeaderCell(Sheet sheet, Row newRow, String text, int colIndex, int width) {
        sheet.setColumnWidth(colIndex, width * 1000);
        Cell cell = newRow.createCell(colIndex);
        CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setColor(IndexedColors.RED.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);
        cell.setCellStyle(style);
        cell.setCellValue(text);

    }

    private void putSubRubCell(Sheet sheet, Row newRow, SubRub sr, int colIndex) {
        Cell cell = newRow.createCell(colIndex);
        XSSFCreationHelper helper = (XSSFCreationHelper) sheet.getWorkbook().getCreationHelper();
        XSSFHyperlink url_link = helper.createHyperlink(Hyperlink.LINK_URL);
        url_link.setAddress(sr.getUrl());

        CellStyle style = sheet.getWorkbook().createCellStyle();
        Font font = sheet.getWorkbook().createFont();
        font.setColor(IndexedColors.BLUE.getIndex());
        font.setUnderline(Font.U_SINGLE);
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(font);

        cell.setCellStyle(style);
        String rub="("+sr.getUrl().split("http://vashmagazin.ua//nerukhomist/")[1].split("/")[0]+")";
        cell.setCellValue(sr.getTitle()+rub);
        cell.setHyperlink(url_link);
    }
}
