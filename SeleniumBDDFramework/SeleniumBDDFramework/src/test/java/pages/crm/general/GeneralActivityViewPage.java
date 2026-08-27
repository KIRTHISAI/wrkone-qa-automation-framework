package pages.crm.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pages.crm.ActivityCommonPage;

public class GeneralActivityViewPage extends ActivityCommonPage {

    public GeneralActivityViewPage(WebDriver driver) {

        super(driver);
    }

    // =========================================================
    // VIEW LOCATORS
    // =========================================================

    private By purpose =
            By.xpath("//*[normalize-space()='Purpose']/following::*[normalize-space()][1]");

    private By description =
            By.xpath("//*[normalize-space()='Description']/following::*[normalize-space()][1]");

    // =========================================================
    // VERIFY ACTIVITY DETAILS
    // =========================================================

    public void verifyActivityDetails(
            String expectedPurpose,
            String expectedDescription) {

        String[] actualDetails = wait.until(webDriver -> {
            try {
                String actualPurpose = webDriver.findElement(purpose).getText().trim();
                String actualDescription = webDriver.findElement(description).getText().trim();

                return actualPurpose.isEmpty() || actualDescription.isEmpty()
                        ? null
                        : new String[] {actualPurpose, actualDescription};
            } catch (org.openqa.selenium.StaleElementReferenceException exception) {
                return null;
            }
        });

        String actualPurpose = actualDetails[0];
        String actualDescription = actualDetails[1];

        System.out.println(
                "Expected Purpose      = "
                        + expectedPurpose);

        System.out.println(
                "Actual Purpose        = "
                        + actualPurpose);

        System.out.println(
                "Expected Description  = "
                        + expectedDescription);

        System.out.println(
                "Actual Description    = "
                        + actualDescription);

        if (!expectedPurpose.equals(actualPurpose)) {

            throw new AssertionError(
                    "Purpose mismatch. Expected = "
                            + expectedPurpose
                            + ", Actual = "
                            + actualPurpose);
        }

        if (!expectedDescription.equals(actualDescription)) {

            throw new AssertionError(
                    "Description mismatch. Expected = "
                            + expectedDescription
                            + ", Actual = "
                            + actualDescription);
        }

        System.out.println(
                "Activity details verified successfully.");
    }
}