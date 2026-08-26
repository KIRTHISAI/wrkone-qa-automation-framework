package Utilities;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentManager {

    private static ExtentReports extent;

    private static ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    // ============================================================
    // GET EXTENT INSTANCE
    // ============================================================

    public static synchronized ExtentReports getInstance() {

        if (extent == null) {

            String reportPath =
                    System.getProperty("user.dir")
                    + File.separator
                    + "test-output"
                    + File.separator
                    + "ExtentReport.html";

            ExtentSparkReporter spark =
                    new ExtentSparkReporter(reportPath);

            spark.config().setReportName(
                    "Wrkone Automation Report"
            );

            spark.config().setDocumentTitle(
                    "Automation Test Results"
            );

            extent = new ExtentReports();

            extent.attachReporter(spark);

            extent.setSystemInfo(
                    "Application",
                    "Wrkone"
            );

            extent.setSystemInfo(
                    "Environment",
                    "QA"
            );

            extent.setSystemInfo(
                    "Automation",
                    "Selenium + Java + Cucumber"
            );

            extent.setSystemInfo(
                    "Browser",
                    "Chrome"
            );
        }

        return extent;
    }

    // ============================================================
    // CREATE TEST
    // ============================================================

    public static ExtentTest createTest(String testName) {

        ExtentTest test =
                getInstance().createTest(testName);

        extentTest.set(test);

        return test;
    }

    // ============================================================
    // GET CURRENT TEST
    // ============================================================

    public static ExtentTest getTest() {

        return extentTest.get();
    }

    // ============================================================
    // INFO
    // ============================================================

    public static void info(String message) {

        if (getTest() != null) {
            getTest().info(message);
        }
    }

    // ============================================================
    // PASS
    // ============================================================

    public static void pass(String message) {

        if (getTest() != null) {
            getTest().pass(message);
        }
    }

    // ============================================================
    // FAIL
    // ============================================================

    public static void fail(String message) {

        if (getTest() != null) {
            getTest().fail(message);
        }
    }

    // ============================================================
    // FAILURE SCREENSHOT
    // ============================================================

    public static void failWithScreenshot(
            String message,
            String screenshotPath) {

        if (getTest() != null) {

            try {

                getTest().fail(
                        message,
                        MediaEntityBuilder
                                .createScreenCaptureFromPath(
                                        screenshotPath)
                                .build()
                );

            } catch (Exception e) {

                getTest().fail(
                        message
                        + " | Screenshot attachment failed: "
                        + e.getMessage()
                );
            }
        }
    }

    // ============================================================
    // FLUSH
    // ============================================================

    public static synchronized void flush() {

        if (extent != null) {
            extent.flush();
        }
    }

    // ============================================================
    // REMOVE CURRENT TEST
    // ============================================================

    public static void removeTest() {

        extentTest.remove();
    }
}