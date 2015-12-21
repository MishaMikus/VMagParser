package qvc;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import vmag.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Mykhaylo_Mikus on 4/17/2015 10:15 AM.
 */
public class Book {

    public static List<POContrl> parse(File f) throws IOException, org.apache.poi.openxml4j.exceptions.InvalidFormatException {
        OPCPackage opcPackage = OPCPackage.open(f.getAbsolutePath());
        XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
        XSSFSheet sheet = workbook.getSheetAt(0);
        XSSFRow firstRow = sheet.getRow(0);
        int rowNum = sheet.getPhysicalNumberOfRows();
        int cellNum = firstRow.getPhysicalNumberOfCells();
        Map<Integer, String> headerMap = new HashMap<>();
        for (int cN = 0; cN < cellNum; cN++) {
            headerMap.put(cN, firstRow.getCell(cN) + "");
        }
        List<POContrl> res = new ArrayList<>();
        for (int rN = 1; rN < rowNum; rN++) {
            POContrl control = new POContrl();
            control.fileName = f.getName();
            XSSFRow row = sheet.getRow(rN);
            if (row!=null){
            for (int cN = 0; cN < cellNum; cN++) {
                String name = headerMap.get(cN);
                String value = row.getCell(cN) + "";
                control = appendControl(control, name, value);
            }}
            res.add(control);
            Log.info(control);
        }
        return res;
    }

    private static POContrl appendControl(POContrl control, String name, String value) {
        if (name.equals("name")) {
            control.name = value;
        }
        if (control.locatorMap.containsKey(name))
            control.locatorMap.get(name).add(value);
        return control;
    }

    public static void saveSumMap(String fn, List<POContrl> controlList) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(new File(fn));
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(30);
        int rN = 0;
        List<String> header = addHeader(sheet.createRow(rN++), controlList);
        for (POContrl control : controlList) {
            addRow(sheet.createRow(rN++), control, header);
        }

        workbook.write(fileOut);
        fileOut.close();

    }

    private static void addRow(XSSFRow row, POContrl control, List<String> header) {
        row.createCell(0).setCellValue(control.name);
        row.createCell(1).setCellValue(control.fileName);
        row.createCell(2).setCellValue(control.modules + "");
        row.createCell(3).setCellValue(control.tests + "");
        int cN = 0;
        for (String hName : header) {
            if (control.locatorMap.containsKey(hName)) {
                XSSFCell cell = row.createCell(4 + cN++);
                cell.setCellValue(getValueForLocator(control, header, hName, cN));
            }
        }
    }


    private static String getValueForLocator(POContrl control, List<String> header, String hName, int cN) {
        String res = null;
        List<String> curApp = control.locatorMap.get(hName);
        int indx = cN - header.indexOf(hName) - 1;
        if (curApp.size() >= (indx + 1))
            res = curApp.get(indx);
        return noNull(res);
    }

    private static String noNull(String s) {
        return ((s == null) || (s.equals("null")) || (s.equals("-"))) ? "" : s;
    }

    private static List<String> addHeader(XSSFRow row, List<POContrl> controlList) {
        List<String> header = new ArrayList<>();
        for (String hName : controlList.get(0).locatorMap.keySet())
            addHeadersUsingMaxSizeOfLocators(controlList, hName, header);
        row.createCell(0).setCellValue("name");
        row.createCell(1).setCellValue("fileName");
        row.createCell(2).setCellValue("modules");
        row.createCell(3).setCellValue("tests");
        for (int cN = 0; cN < header.size(); cN++) {
            row.createCell(cN + 4).setCellValue(header.get(cN));
        }
        return header;
    }

    private static void addHeadersUsingMaxSizeOfLocators(List<POContrl> controlList, String nameOfHeader, List<String> header) {
        int size = getMaxSize(controlList, nameOfHeader);
        for (int i = 0; i < size; i++) {
            header.add(nameOfHeader);
        }
    }

    private static int getMaxSize(List<POContrl> controlList, String nameOfHeader) {
        int res = 0;
        for (POContrl c : controlList) {
            if (c.locatorMap.containsKey(nameOfHeader)) {
                int cSize = c.locatorMap.get(nameOfHeader).size();
                if (cSize > res) {
                    res = cSize;
                }
            }

        }
        return res;
    }

    public static List<ModuleFile> parseModule(File f) throws InvalidFormatException, IOException {
        List<ModuleFile> res = new ArrayList<>();
        ModuleFile moduleFile = new ModuleFile();
        moduleFile.name = f.getName();
        OPCPackage opcPackage = OPCPackage.open(f.getAbsolutePath());
        XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
        int sheetCount = workbook.getNumberOfSheets();
        for (int sN = 0; sN < sheetCount; sN++) {
            XSSFSheet sheet = workbook.getSheetAt(sN);
            XSSFRow firstRow = sheet.getRow(0);
            if (firstRow != null) {
                int rowNum = sheet.getPhysicalNumberOfRows();
                int cellNum = firstRow.getPhysicalNumberOfCells();
                int ccNum = 1;
                for (int cN = 0; cN < cellNum; cN++) {
                    if ((firstRow.getCell(cN) + "").equalsIgnoreCase("Object")) {
                        ccNum = cN;
                    }
                }
                for (int rN = 1; rN < rowNum; rN++) {
                    XSSFRow row = sheet.getRow(rN);
                    if (row.getCell(ccNum) != null) {
                        moduleFile.controls.add(row.getCell(ccNum) + "");
                        res.add(moduleFile);
                    }
                }
            }
        }
        Log.info(res);
        return res;
    }

    public static List<TestFile> parseTest(File f) throws InvalidFormatException, IOException {
        List<TestFile> res = new ArrayList<>();
        TestFile testFile = new TestFile();
        testFile.name = f.getName();

        OPCPackage opcPackage = OPCPackage.open(f.getAbsolutePath());
        XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
        int sheetCount = workbook.getNumberOfSheets();
        for (int sN = 0; sN < sheetCount; sN++) {
            XSSFSheet sheet = workbook.getSheetAt(sN);
            XSSFRow firstRow = sheet.getRow(0);
            if (firstRow != null) {
                int rowNum = sheet.getPhysicalNumberOfRows();
                int cellNum = firstRow.getPhysicalNumberOfCells();
                int ccNumPage = 0;
                int ccNumObject = 1;

                for (int cN = 0; cN < cellNum; cN++) {

                    if ((firstRow.getCell(cN) + "").equalsIgnoreCase("Page")) {
                        ccNumPage = cN;
                    }

                    if ((firstRow.getCell(cN) + "").equalsIgnoreCase("Object")) {
                        ccNumObject = cN;
                    }
                }
                Log.info("parse " + testFile.name + "\t rowNum=" + rowNum);
                for (int rN = 1; rN < rowNum; rN++) {
                    XSSFRow row = sheet.getRow(rN);

                    try {
                        if (row.getCell(ccNumPage).getStringCellValue().length() > 0) {
                            testFile.controls.add(row.getCell(ccNumObject) + "");
                        }else
                        {testFile.modules.add(row.getCell(ccNumObject) + "");}
                    } catch (Exception e) {
                        testFile.modules.add(row.getCell(ccNumObject) + "");
                    }
                }
                res.add(testFile);
            }
        }
        Log.info(res);
        return res;
    }
}
