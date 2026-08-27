package Utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.CRMActivity;
import model.LinkedCRMActivity;

public class ExcelUtils {

    // =========================================================
    // EXCEL FILE PATH
    // =========================================================

    private static final String FILE_PATH =
            "src/test/resources/TestData/CRMActivityData.xlsx";

    // =========================================================
    // SHEET NAMES
    // =========================================================

    private static final String GENERAL_ACTIVITY_SHEET =
            "General Activity";

    private static final String LINKED_ACTIVITY_SHEET =
            "Linked Activity";

    // =========================================================
    // GET CELL DATA
    // =========================================================

    public static String getCellData(
            int rowNumber,
            int columnNumber) {

        return getCellData(
                GENERAL_ACTIVITY_SHEET,
                rowNumber,
                columnNumber);
    }

    // =========================================================
    // GET CELL DATA - SPECIFIC SHEET
    // =========================================================

    public static String getCellData(
            String sheetName,
            int rowNumber,
            int columnNumber) {

        try (
                FileInputStream fis =
                        new FileInputStream(FILE_PATH);

                Workbook workbook =
                        new XSSFWorkbook(fis)
        ) {

            Sheet sheet =
                    workbook.getSheet(sheetName);

            if (sheet == null) {

                throw new IllegalArgumentException(
                        "Excel sheet not found: "
                                + sheetName);
            }

            Row row =
                    sheet.getRow(rowNumber);

            if (row == null) {

                throw new IllegalArgumentException(
                        "Excel row not found: "
                                + rowNumber
                                + " in sheet "
                                + sheetName);
            }

            DataFormatter formatter =
                    new DataFormatter();

            if (row.getCell(columnNumber) == null) {
                return "";
            }

            String value =
                    formatter.formatCellValue(
                            row.getCell(columnNumber));

            return value == null
                    ? ""
                    : value.trim();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read Excel file: "
                            + FILE_PATH,
                    e);
        }
    }

    // =========================================================
    // CRM LOGIN EMAIL
    // =========================================================

    public static String getCRMEmail(int rowNumber) {

        return getCellData(
                GENERAL_ACTIVITY_SHEET,
                rowNumber,
                0);
    }

    // =========================================================
    // CRM LOGIN PASSWORD
    // =========================================================

    public static String getCRMPassword(int rowNumber) {

        return getCellData(
                GENERAL_ACTIVITY_SHEET,
                rowNumber,
                1);
    }

    // =========================================================
    // GET GENERAL CRM ACTIVITY
    //
    // Excel columns:
    //
    // 0 - Login Email
    // 1 - Login Password
    // 2 - Activity Type
    // 3 - Purpose
    // 4 - Description
    // 5 - Date
    // 6 - Start Time
    // 7 - End Time
    // 8 - Assignment Type
    // 9 - User
    // 10 - Assignment Reason
    //
    // =========================================================

    public static CRMActivity getCRMActivity(int row) {

        System.out.println();
        System.out.println(
                "==========================================");
        System.out.println(
                "READING CRM ACTIVITY FROM EXCEL");
        System.out.println(
                "Sheet = " + GENERAL_ACTIVITY_SHEET);
        System.out.println(
                "Excel Row = " + row);
        System.out.println(
                "==========================================");

        String loginEmail =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        0);

        String loginPassword =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        1);

        String activityType =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        2);

        String purpose =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        3);

        String description =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        4);

        String date =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        5);

        String startTime =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        6);

        String endTime =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        7);

        String assignmentType =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        8);

        String user =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        9);

        String reason =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        10);

        String editRequired =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        11);

        String editPurpose =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        12);

        String editDescription =
                getCellData(
                        GENERAL_ACTIVITY_SHEET,
                        row,
                        13);

        // =====================================================
        // DEBUG OUTPUT
        // =====================================================

        System.out.println(
                "Login Email     = " + loginEmail);

        System.out.println(
                "Activity Type   = " + activityType);

        System.out.println(
                "Purpose         = " + purpose);

        System.out.println(
                "Description     = " + description);

        System.out.println(
                "Date            = " + date);

        System.out.println(
                "Start Time      = " + startTime);

        System.out.println(
                "End Time        = " + endTime);

        System.out.println(
                "Assignment Type = " + assignmentType);

        System.out.println(
                "User            = " + user);

        System.out.println(
                "Reason          = " + reason);

        System.out.println(
                "==========================================");

        // =====================================================
        // CREATE CRM ACTIVITY OBJECT
        // =====================================================

        CRMActivity activity = new CRMActivity(
                loginEmail,
                loginPassword,
                activityType,
                purpose,
                description,
                date,
                startTime,
                endTime,
                assignmentType,
                user,
                reason
        );

        activity.setEditRequired(editRequired);
        activity.setEditPurpose(editPurpose);
        activity.setEditDescription(editDescription);

        return activity;
    }

    // =========================================================
    // GET LINKED ACTIVITY CELL DATA
    // =========================================================

    public static String getLinkedActivityCellData(
            int rowNumber,
            int columnNumber) {

        return getCellData(
                LINKED_ACTIVITY_SHEET,
                rowNumber,
                columnNumber);
    }

    // =========================================================
    // CHECK WHETHER ROW EXISTS
    // =========================================================

    public static boolean rowExists(
            String sheetName,
            int rowNumber) {

        try (
                FileInputStream fis =
                        new FileInputStream(FILE_PATH);

                Workbook workbook =
                        new XSSFWorkbook(fis)
        ) {

            Sheet sheet =
                    workbook.getSheet(sheetName);

            if (sheet == null) {
                return false;
            }

            Row row =
                    sheet.getRow(rowNumber);

            return row != null;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to check Excel row.",
                    e);
        }
    }

    // =========================================================
    // GET TOTAL ROWS
    // =========================================================

    public static int getRowCount(
            String sheetName) {

        try (
                FileInputStream fis =
                        new FileInputStream(FILE_PATH);

                Workbook workbook =
                        new XSSFWorkbook(fis)
        ) {

            Sheet sheet =
                    workbook.getSheet(sheetName);

            if (sheet == null) {

                throw new IllegalArgumentException(
                        "Excel sheet not found: "
                                + sheetName);
            }

            return sheet.getLastRowNum();

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to read Excel row count.",
                    e);
        }
    }

	public static LinkedCRMActivity getLinkedCRMActivity(int row) {
                return new LinkedCRMActivity(
                                getLinkedActivityCellData(row, 0),
                                getLinkedActivityCellData(row, 1),
                                getLinkedActivityCellData(row, 2),
                                getLinkedActivityCellData(row, 3),
                                getLinkedActivityCellData(row, 4),
                                getLinkedActivityCellData(row, 5),
                                getLinkedActivityCellData(row, 6),
                                getLinkedActivityCellData(row, 7),
                                getLinkedActivityCellData(row, 8),
                                getLinkedActivityCellData(row, 9),
                                getLinkedActivityCellData(row, 10),
                                getLinkedActivityCellData(row, 11),
                                getLinkedActivityCellData(row, 12),
                                getLinkedActivityCellData(row, 13),
                                getLinkedActivityCellData(row, 14)
                );
	}
}