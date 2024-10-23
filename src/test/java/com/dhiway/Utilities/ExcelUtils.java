package com.dhiway.Utilities;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    private Workbook workbook;
    private Sheet sheet;

    public ExcelUtils(String sheetName) throws IOException {
        FileInputStream fis = new FileInputStream("TestData/Testexcel.xlsx");
        workbook = new XSSFWorkbook(fis);
        sheet = workbook.getSheet(sheetName);
    }

    public String getCellData(int rowNum, int colNum) {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(colNum);
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        } else {
            return "";
        }
    }

    // Method to get all data from the first row
    public Map<String, String> getTestData() {
        Map<String, String> firstRowData = new HashMap<>();
        Row firstRow = sheet.getRow(0); // Assuming first row is the header row

        if (firstRow != null) {
            for (Cell cell : firstRow) {
                int colIndex = cell.getColumnIndex();
                String header = cell.getStringCellValue();
                Row secondRow = sheet.getRow(1); // Assuming data starts from the second row
                if (secondRow != null) {
                    Cell dataCell = secondRow.getCell(colIndex);
                    String dataValue = dataCell != null ? dataCell.getStringCellValue() : "";
                    firstRowData.put(header, dataValue);
                }
            }
        }
        return firstRowData;
    }

    public int getRowCount() {
        return sheet.getLastRowNum();
    }

    public int getColumnCount() {
        return sheet.getRow(0).getLastCellNum();
    }

    public void close() throws IOException {
        workbook.close();
    }

    public void writeDataToSheet(String sheetName, String name, List<String> data) throws IOException {
        Sheet writeSheet = workbook.getSheet(sheetName);
        if (writeSheet == null) {
            writeSheet = workbook.createSheet(sheetName);
        }

        boolean isNameFound = false;

        // Iterate through the rows to find the name in the first column
        for (int rowIndex = 0; rowIndex <= writeSheet.getLastRowNum(); rowIndex++) {
            Row row = writeSheet.getRow(rowIndex);
            if (row != null) {
                Cell nameCell = row.getCell(0); // Assuming names are in the first column (index 0)
                if (nameCell != null && nameCell.getCellType() == CellType.STRING) {
                    String cellValue = nameCell.getStringCellValue();
                    if (cellValue.equals(name)) {
                        // Name found, now update the cells starting from the third column (index 2)
                        for (int i = 0; i < data.size(); i++) {
                            Cell dataCell = row.getCell(2 + i); // Start from the third column
                            if (dataCell == null) {
                                dataCell = row.createCell(2 + i);
                            }
                            dataCell.setCellValue(data.get(i)); // Set each string in the list
                        }
                        isNameFound = true;
                        break; // Exit loop once the name is found and updated
                    }
                }
            }
        }

        if (!isNameFound) {
            // If name not found, add it as a new row
            int rowCount = writeSheet.getLastRowNum();
            Row newRow = writeSheet.createRow(rowCount + 1);
            Cell nameCell = newRow.createCell(0);
            nameCell.setCellValue(name);

            // Write the data to the third column and onward
            for (int i = 0; i < data.size(); i++) {
                Cell dataCell = newRow.createCell(2 + i);
                dataCell.setCellValue(data.get(i));
            }
        }

        // Write the output to the file
        try (FileOutputStream fos = new FileOutputStream("TestData/Testexcel.xlsx")) {
            workbook.write(fos);
        }
    }

}