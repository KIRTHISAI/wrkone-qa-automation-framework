package stepdefinitions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import base.baseClass;
import Utilities.ExtentManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

import com.aventstack.extentreports.MediaEntityBuilder;

public class Hooks extends baseClass {

    // ============================================================
    // BEFORE SCENARIO
    // ============================================================

    @Before
    public void beforeScenario(Scenario scenario) {

        System.out.println("======================================");
        System.out.println("Starting Scenario: " + scenario.getName());
        System.out.println("Starting browser...");

        loadConfig();
        launchBrowser1();

        System.out.println("Scenario browser ready.");

        // Extent
        ExtentManager.createTest(scenario.getName());

        ExtentManager.info(
                "Scenario started: " + scenario.getName());

        ExtentManager.info("Browser: Chrome");

        try {
            ExtentManager.info(
                    "Current URL: "
                            + getDriver().getCurrentUrl());
        } catch (Exception e) {
            ExtentManager.info(
                    "Unable to read current URL");
        }

        System.out.println("======================================");
    }


    // ============================================================
    // AFTER SCENARIO
    // ============================================================

    @After
    public void afterScenario(Scenario scenario) {

        System.out.println(
                "Scenario completed: "
                        + scenario.getName());

        WebDriver driver = null;

        try {
            driver = getDriver();
        } catch (Exception e) {
            System.out.println("No active browser found.");
        }


        // ========================================================
        // FAILED SCENARIO
        // ========================================================

        if (scenario.isFailed()) {

            System.out.println("Scenario FAILED.");

            ExtentManager.fail(
                    "Scenario Failed: "
                            + scenario.getName());

            captureFailureScreenshot(
                    driver,
                    scenario);
        }


        // ========================================================
        // PASSED SCENARIO
        // ========================================================

        else {

            ExtentManager.pass(
                    "Scenario Passed: "
                            + scenario.getName());
        }


        // ========================================================
        // EXTENT CLEANUP
        // ========================================================

        ExtentManager.flush();
        ExtentManager.removeTest();


        // ========================================================
        // BROWSER CLEANUP
        // ========================================================

        closeBrowser();

        System.out.println(
                "Browser cleanup completed.");

        System.out.println(
                "======================================");
    }


    // ============================================================
    // FAILURE SCREENSHOT
    // ============================================================

    private void captureFailureScreenshot(
            WebDriver driver,
            Scenario scenario) {

        if (driver == null) {
            return;
        }

        try {

            String timestamp =
                    new SimpleDateFormat(
                            "yyyyMMdd_HHmmss_SSS")
                            .format(new Date());

            String scenarioName =
                    scenario.getName()
                            .replaceAll(
                                    "[^a-zA-Z0-9_-]",
                                    "_");

            String directoryPath =
                    System.getProperty("user.dir")
                            + File.separator
                            + "test-output"
                            + File.separator
                            + "screenshots";

            File directory =
                    new File(directoryPath);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            String screenshotPath =
                    directoryPath
                            + File.separator
                            + scenarioName
                            + "_"
                            + timestamp
                            + ".png";


            // ----------------------------------------------------
            // TAKE SCREENSHOT
            // ----------------------------------------------------

            File screenshot =
                    ((TakesScreenshot) driver)
                            .getScreenshotAs(
                                    OutputType.FILE);


            File destination =
                    new File(screenshotPath);

            java.nio.file.Files.copy(
                    screenshot.toPath(),
                    destination.toPath(),
                    java.nio.file.StandardCopyOption
                            .REPLACE_EXISTING);


            // ----------------------------------------------------
            // EXTENT REPORT
            // ----------------------------------------------------

            ExtentManager.getTest().fail(
                    "Failure Screenshot",
                    MediaEntityBuilder
                            .createScreenCaptureFromPath(
                                    screenshotPath)
                            .build());


            // ----------------------------------------------------
            // CUCUMBER REPORT
            // ----------------------------------------------------

            byte[] screenshotBytes =
                    java.nio.file.Files.readAllBytes(
                            destination.toPath());

            scenario.attach(
                    screenshotBytes,
                    "image/png",
                    "Failure Screenshot");


            System.out.println(
                    "Failure screenshot saved: "
                            + screenshotPath);

        } catch (Exception e) {

            System.out.println(
                    "Unable to capture failure screenshot: "
                            + e.getMessage());

            ExtentManager.fail(
                    "Unable to capture failure screenshot: "
                            + e.getMessage());
        }
    }
}