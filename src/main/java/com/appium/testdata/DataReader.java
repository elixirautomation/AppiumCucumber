package com.appium.testdata;

import com.appium.base.TestBase;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Platform;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.HashSet;

public class DataReader extends TestBase {

    // Data Driven Testing Using "Test Case ID"

    private static XSSFWorkbook setExcelWorkbook(String testDataExcelFileName) {
        String testDataExcelPath = null;
        XSSFWorkbook excelWBook = null;
        if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
            testDataExcelPath = System.getProperty("user.dir") + "//src//main//resource//";
        } else if (Platform.getCurrent().toString().contains("WIN")) {
            testDataExcelPath = System.getProperty("user.dir") + "\\src\\main\\resource\\";
        }

        try {
            FileInputStream ExcelFile = new FileInputStream(testDataExcelPath + testDataExcelFileName);
            excelWBook = new XSSFWorkbook(ExcelFile);
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return excelWBook;
    }

    private static XSSFSheet setExcelSheet(XSSFWorkbook workbook, String sheetName) {
        XSSFSheet excelWSheet = null;
        try {
            excelWSheet = workbook.getSheet(sheetName);
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return excelWSheet;
    }


    private static String getCellData(int RowNum, int ColNum, XSSFSheet excelWSheet) {
        String cellData = null;
        try {
            XSSFCell cell = excelWSheet.getRow(RowNum).getCell(ColNum);
            DataFormatter formatter = new DataFormatter();
            cellData = formatter.formatCellValue(cell);
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return cellData;
    }

    private static XSSFRow getRowData(int RowNum, XSSFSheet excelWSheet) {
        XSSFRow row = null;
        try {
            row = excelWSheet.getRow(RowNum);
        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return row;
    }

    public static void setCellData(String value, int RowNum, int ColNum, String sheetName, String filePath) {
        try {
            XSSFWorkbook excelWBook = setExcelWorkbook(filePath);
            XSSFSheet excelWSheet = setExcelSheet(excelWBook, sheetName);
            XSSFRow row = getRowData(RowNum, excelWSheet);
            XSSFCell cell = row.getCell(ColNum);

            if (cell == null) {
                cell = row.createCell(ColNum);
                cell.setCellValue(value);
            } else {
                cell.setCellValue(value);
            }

            FileOutputStream fileOut = new FileOutputStream(filePath);
            excelWBook.write(fileOut);
            fileOut.flush();
            fileOut.close();

        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
    }

    private static int getRowCount(XSSFSheet excelWSheet) {
        return excelWSheet.getLastRowNum() + 1;
    }

    private static int getColumnCount(XSSFSheet excelWSheet) {
        return excelWSheet.getRow(0).getLastCellNum() + 1;
    }

    private static int getColumnIndex(String columnName, XSSFSheet excelWSheet) {
        int index = 0;
        int count = getColumnCount(excelWSheet);

        for (int i = 0; i < count; i++) {
            if (getCellData(0, i, excelWSheet).equalsIgnoreCase(columnName)) {
                index = i;
                break;
            }
        }
        return index;
    }

    public static int getRowContainsTestCase(String testCaseName, int colNum, XSSFSheet excelWSheet) throws Exception {
        int i;
        int rowCount = getRowCount(excelWSheet);
        for (i = 0; i < rowCount; i++) {
            if (getCellData(i, colNum, excelWSheet).equalsIgnoreCase(testCaseName)) {
                break;
            }
        }
        return i;
    }

    public static HashSet<String> getRunnableTestCases(String testDataExcelFileName, String runModeSheetName) {
        HashSet<String> hashSet = new HashSet<String>();
        try {
            // Create Workbook and Worksheet
            XSSFWorkbook workbook = setExcelWorkbook(testDataExcelFileName);
            XSSFSheet worksheet = setExcelSheet(workbook, runModeSheetName);

            // Get No Of Rows
            int totalRows = getRowCount(worksheet);

            // Get All Test Case with RunMode 'Y'
            int TC_ID_Index = getColumnIndex(prop.getProperty("TCIDColumn"), worksheet);
            int RunMode_Index = getColumnIndex(prop.getProperty("RunmodeColumn"), worksheet);

            for (int i = 1; i < totalRows; i++) {
                String runModeValue = getCellData(i, RunMode_Index, worksheet);

                if (runModeValue.equals("Y")) {
                    String testCaseID = getCellData(i, TC_ID_Index, worksheet);
                    hashSet.add(testCaseID);
                }
            }

        } catch (Exception Ex) {
            Ex.printStackTrace();
        }
        return hashSet;
    }

    private static String[] getColumnHeaders(XSSFSheet worksheet){
        String[] columnNames = null;
        try{
            int columnCount = getColumnCount(worksheet);
            columnNames = new String[columnCount];

            for (int i = 0; i < columnCount; i++) {
                columnNames[i] = (getCellData(0, i, worksheet));
            }

        }catch(Exception Ex){
            Ex.printStackTrace();
        }

        return columnNames;
    }

    public static HashMap<String, HashMap<String, String>> testDataMappedToTestName(String testDataExcelFileName, String testDataSheetName){
        HashMap<String, String> childHashMap = null;
        HashMap<String, HashMap<String, String>> hashMap = new HashMap<String, HashMap<String, String>>();

        try {
            // Create Workbook and Worksheet
            XSSFWorkbook workbook = setExcelWorkbook(testDataExcelFileName);
            XSSFSheet worksheet = setExcelSheet(workbook, testDataSheetName);

            // RowCount and ColumnCount

            int rowCount = getRowCount(worksheet);
            int columnCount = getColumnCount(worksheet);

            // Get Column Names From Sheet
            String[] columnNames = getColumnHeaders(worksheet);

            for (int j = 1; j < rowCount; j++) {
                childHashMap = new HashMap<String, String>();
                for (int i = 1; i < columnCount; i++) {
                    childHashMap.put(columnNames[i], getCellData(j, i, worksheet));
                }
                hashMap.put(getCellData(j, 1, worksheet), childHashMap);
            }
        }catch (Exception Ex){
            Ex.printStackTrace();
        }
        return hashMap;
    }

    public static Boolean isRunnable(String testCaseName, HashMap<String, HashMap<String, String>> testData){
        boolean flag = true;
        try{
            String runmodeValue = testData.get(testCaseName).get(prop.getProperty("RunmodeColumn"));

            if (runmodeValue.equalsIgnoreCase("N")){
                flag = false;
            }

        }catch(Exception Ex){
            Ex.printStackTrace();
        }
        return flag;
    }

}
