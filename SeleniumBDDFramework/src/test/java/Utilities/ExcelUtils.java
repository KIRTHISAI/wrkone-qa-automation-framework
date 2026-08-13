package Utilities;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.CRMActivity;
import model.LinkedCRMActivity;

public class ExcelUtils {

    private static final String FILE_PATH =
            "src/test/resources/TestData/CRMActivityData.xlsx";

    private static final String SHEET_NAME =
            "General Activity";

    private static final String LINKED_ACTIVITY_SHEET =
            "Linked Activity";


    // =========================================================
    // GET CELL DATA - DEFAULT SHEET
    // =========================================================

    public static String getCellData(int row, int column) {

        return getCellData(row, column, SHEET_NAME);
    }


    // =========================================================
    // GET CELL DATA - SPECIFIC SHEET
    // =========================================================

    public static String getCellData(
            int row,
            int column,
            String sheetName) {

        try (
                FileInputStream fis =
                        new FileInputStream(FILE_PATH);

                Workbook workbook =
                        new XSSFWorkbook(fis)
        ) {

            Sheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {

                throw new RuntimeException(
                        "Sheet '" + sheetName + "' not found.");
            }

            Row excelRow = sheet.getRow(row);

            if (excelRow == null) {

                throw new RuntimeException(
                        "Row " + row +
                        " not found in sheet '" +
                        sheetName + "'.");
            }

            DataFormatter formatter =
                    new DataFormatter();

            String value =
                    formatter.formatCellValue(
                            excelRow.getCell(column));

            System.out.println(
                    "Sheet = " + sheetName +
                    " | Row = " + row +
                    " | Column = " + column +
                    " | Value = [" + value + "]");

            return value.trim();

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to read Excel data. " +
                    "Sheet = " + sheetName +
                    ", Row = " + row +
                    ", Column = " + column +
                    " : " + e.getMessage(),
                    e);
        }
    }


    // =========================================================
    // GENERAL CRM ACTIVITY
    // DO NOT CHANGE
    // =========================================================

    public static CRMActivity getCRMActivity(int row) {

        return new CRMActivity(

                getCellData(row, 0),   // Activity Type
                getCellData(row, 1),   // Purpose
                getCellData(row, 2),   // Description
                getCellData(row, 3),   // Date
                getCellData(row, 4),   // Start Time
                getCellData(row, 5),   // End Time
                getCellData(row, 6),   // Assignment Type
                getCellData(row, 7),   // User
                getCellData(row, 8)    // Reason
        );
    }
 // =========================================================
 // LINKED CRM ACTIVITY
 // =========================================================

 public static LinkedCRMActivity getLinkedCRMActivity(int row) {

     return new LinkedCRMActivity(

             getCellData(
                     row,
                     0,
                     LINKED_ACTIVITY_SHEET), // Lead Name

             getCellData(
                     row,
                     1,
                     LINKED_ACTIVITY_SHEET), // Activity Type

             getCellData(
                     row,
                     2,
                     LINKED_ACTIVITY_SHEET), // Purpose

             getCellData(
                     row,
                     3,
                     LINKED_ACTIVITY_SHEET), // Description

             getCellData(
                     row,
                     4,
                     LINKED_ACTIVITY_SHEET), // Link to Stage

             getCellData(
                     row,
                     5,
                     LINKED_ACTIVITY_SHEET), // Date

             getCellData(
                     row,
                     6,
                     LINKED_ACTIVITY_SHEET), // Start Time

             getCellData(
                     row,
                     7,
                     LINKED_ACTIVITY_SHEET), // End Time

             getCellData(
                     row,
                     8,
                     LINKED_ACTIVITY_SHEET), // Assignment Type

             getCellData(
                     row,
                     9,
                     LINKED_ACTIVITY_SHEET), // User

             getCellData(
                     row,
                     10,
                     LINKED_ACTIVITY_SHEET)  // Reason
     );
 }
}