package com.logmein.utils;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class ExcelUtil {

    // This method will read and return Excel data into a double array
    public static Object[][] readExcelData(String sheetName) throws FileNotFoundException {
        Object[][] dataTable = null;

        try {
            // Create a file input stream to read Excel workbook and worksheet
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            File excelFile = new File(Objects.requireNonNull(classLoader.getResource("LogMeIn_TestData.xlsx")).getFile());
            FileInputStream excelIn = new FileInputStream(excelFile);
            XSSFWorkbook xlWb = new XSSFWorkbook(excelIn);
            XSSFSheet xlSheet = xlWb.getSheet(sheetName);
            // Get the number of rows and columns
            int numRows = xlSheet.getLastRowNum() + 1;
            int numCols = xlSheet.getRow(0).getLastCellNum();

            // Create double array data table - rows x cols
            // We will return this data table
            dataTable = new Object[numRows][numCols];

            // For each row, create a HSSFRow, then iterate through the "columns"
            // For each "column" create an HSSFCell to grab the value at the specified cell (i,j)
            for (int i = 0; i < numRows; i++) {
                XSSFRow xlRow = xlSheet.getRow(i);
                for (int j = 0; j < numCols; j++) {
                    XSSFCell xlCell = xlRow.getCell(j);
                    dataTable[i][j] = xlCell.toString();
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR FILE HANDLING " + e.toString());
        }
        return dataTable;
    }

}