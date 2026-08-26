package pages.crm.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.crm.ActivityCommonPage;

public class GeneralActivityViewPage extends ActivityCommonPage {

    public GeneralActivityViewPage(WebDriver driver) {

        super(driver);
    }

    // =========================================================
    // VIEW LOCATORS
    // =========================================================

    /*
     * Adjust this locator if your application uses a different
     * View button/icon.
     */
    private By viewButton =
            By.xpath("//button[normalize-space()='View']");

    // =========================================================
    // DETAIL LOCATORS
    // =========================================================

    private By purpose =
            By.id("purpose");

    private By description =
            By.id("description");

    // =========================================================
    // CLICK VIEW
    // =========================================================

    public void clickView() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(viewButton))
                .click();

        System.out.println(
                "View button clicked successfully.");
    }

    // =========================================================
    // VERIFY ACTIVITY DETAILS
    // =========================================================

    public void verifyActivityDetails(
            String expectedPurpose,
            String expectedDescription) {

        WebElement purposeField =
                wait.until(
                        ExpectedConditions
                                .visibilityOfElementLocated(
                                        purpose));

        WebElement descriptionField =
                wait.until(
                        ExpectedConditions
                                .visibilityOfElementLocated(
                                        description));

        String actualPurpose =
                purposeField.getAttribute("value");

        String actualDescription =
                descriptionField.getAttribute("value");

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