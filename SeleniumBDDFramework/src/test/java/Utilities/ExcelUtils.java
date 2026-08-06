package Utilities;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.CRMActivity;

public class ExcelUtils {

    private static final String FILE_PATH =
            "src/test/resources/TestData/CRMActivityData.xlsx";

    private static final String SHEET_NAME = "Sheet1";

    @SuppressWarnings("resource")
    public static String getCellData(int row, int column) {

        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet(SHEET_NAME);

            if (sheet == null) {
                throw new RuntimeException("Sheet '" + SHEET_NAME + "' not found.");
            }

            Row excelRow = sheet.getRow(row);

            if (excelRow == null) {
                throw new RuntimeException("Row " + row + " not found.");
            }

            DataFormatter formatter = new DataFormatter();

            String value = formatter.formatCellValue(excelRow.getCell(column));

            System.out.println("Row " + row + ", Col " + column + " = [" + value + "]");

            return value;

        } catch (Exception e) {
            throw new RuntimeException("Unable to read Excel : " + e.getMessage(), e);
        }
    }
    public static CRMActivity getCRMActivity(int row) {

        return new CRMActivity(

                getCellData(row,0),   // Activity Type
                getCellData(row,1),   // Purpose
                getCellData(row,2),   // Description
                getCellData(row,3),   // Date
                getCellData(row,4),   // Start Time
                getCellData(row,5),   // End Time
                getCellData(row,6),   // Assignment Type
                getCellData(row,7),   // User
                getCellData(row,8)    // Reason
        );
    }

}