package pages.crm.general;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import pages.crm.ActivityCommonPage;

public class GeneralActivityDeletePage extends ActivityCommonPage {

    public GeneralActivityDeletePage(WebDriver driver) {

        super(driver);
    }

        private final By confirmDelete =
            By.id("activities-page-delete-modal-confirm");

    public void confirmDelete() {

        wait.until(
                ExpectedConditions
                        .elementToBeClickable(confirmDelete))
                .click();
    }
}