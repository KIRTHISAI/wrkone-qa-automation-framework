package pages.crm.linked;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import pages.crm.general.GeneralActivityEditPage;

public class LinkedActivityEditPage extends GeneralActivityEditPage {

    private final By linkedDescription =
            By.cssSelector("textarea#activity-description-textarea, textarea[name='description']");

    public LinkedActivityEditPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public void updatePurpose(String value) {
        // Linked activity edit is only updating the description as per Excel sheet.
        // Purpose changes are intentionally not applied.
    }

    @Override
    public void updateDescription(String value) {
        WebElement descriptionField = wait.until(
            ExpectedConditions.elementToBeClickable(linkedDescription));
        scrollIntoView(descriptionField);
        descriptionField.clear();
        descriptionField.sendKeys(value);
    }
}

