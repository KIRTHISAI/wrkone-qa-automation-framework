package pages.crm.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.crm.ActivityCommonPage;

public class GeneralActivityDeletePage extends ActivityCommonPage {

    public GeneralActivityDeletePage(WebDriver driver) {

        super(driver);
    }

    private By deleteButton =
            By.xpath("//button[normalize-space()='Delete']");

    private By confirmDelete =
            By.xpath("//button[normalize-space()='Confirm']");

    public void clickDelete() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(deleteButton))
                .click();
    }

    public void confirmDelete() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(confirmDelete))
                .click();
    }
}