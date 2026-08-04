package stepdefinitions;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import Utilities.ExtentManager;
import base.baseClass;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import pages.LoginPage;

public class Hooks {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ExtentTest test;

    @Before
    public void beforeScenario(Scenario scenario) {

        // Launch browser
        baseClass.launchBrowser();

        // Create Extent Report test
        test = extent.createTest(scenario.getName());

        // Login
        LoginPage login = new LoginPage(baseClass.driver);
        login.enterEmail("org2.1admin@onelern.com");
        login.enterPassword("123456");
        login.clickLogin();
    }

    @After
    public void afterScenario(Scenario scenario) throws IOException {

        if (scenario.isFailed() && baseClass.driver != null) {

            File src = ((TakesScreenshot) baseClass.driver)
                    .getScreenshotAs(OutputType.FILE);

            String screenshotPath = "test-output/screenshots/"
                    + scenario.getName().replaceAll(" ", "_")
                    + ".png";

            File dest = new File(screenshotPath);
            FileUtils.copyFile(src, dest);

            test.fail("Scenario Failed");
            test.addScreenCaptureFromPath(dest.getAbsolutePath());

        } else {
            test.pass("Scenario Passed");
        }

        extent.flush();

        if (baseClass.driver != null) {
            baseClass.closeBrowser();
        }
    }
}