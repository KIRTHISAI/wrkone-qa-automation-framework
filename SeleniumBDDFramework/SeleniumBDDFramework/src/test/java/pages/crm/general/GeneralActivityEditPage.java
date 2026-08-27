package pages.crm.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.crm.ActivityCommonPage;

public class GeneralActivityEditPage extends ActivityCommonPage {

    public GeneralActivityEditPage(WebDriver driver) {

        super(driver);
    }

    // =========================================================
    // EDIT LOCATORS
    // =========================================================

    private By purpose =
            By.id("activity-purpose-input");

    private By description =
            By.id("activity-description-textarea");

    private By saveButton =
            By.id("btn-submit");

    // =========================================================
    // SEARCH
    // =========================================================

    // =========================================================
    // EDIT
    // =========================================================

    public void updatePurpose(String value) {

        replaceText(purpose, value);
    }

    public void updateDescription(String value) {

        replaceText(description, value);
    }

    public void saveChanges() {

        WebElement save = wait.until(
                ExpectedConditions
                        .elementToBeClickable(saveButton));
        scrollIntoView(save);
        save.click();
    }

    private void replaceText(By locator, String value) {
        WebElement field = wait.until(
                ExpectedConditions.visibilityOfElementLocated(locator));
        scrollIntoView(field);
        field.clear();
        field.sendKeys(value);
    }
}