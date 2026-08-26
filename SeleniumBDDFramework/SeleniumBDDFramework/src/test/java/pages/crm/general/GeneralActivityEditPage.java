package pages.crm.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.crm.ActivityCommonPage;

public class GeneralActivityEditPage extends ActivityCommonPage {

    public GeneralActivityEditPage(WebDriver driver) {

        super(driver);
    }

    // =========================================================
    // EDIT LOCATORS
    // =========================================================

    private By editButton =
            By.xpath("//button[normalize-space()='Edit']");

    private By purpose =
            By.id("purpose");

    private By description =
            By.id("description");

    private By saveButton =
            By.xpath("//button[normalize-space()='Save']");

    // =========================================================
    // SEARCH
    // =========================================================

    // =========================================================
    // EDIT
    // =========================================================

    public void clickEdit() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(editButton))
                .click();
    }

    public void updatePurpose(String value) {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(purpose))
                .clear();

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(purpose))
                .sendKeys(value);
    }

    public void updateDescription(String value) {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(description))
                .clear();

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(description))
                .sendKeys(value);
    }

    public void saveChanges() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(saveButton))
                .click();
    }
}