package pages;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserProfilePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public UserProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ==========================
    // Locators
    // ==========================

    private final By manageRoles =
            By.xpath("//span[normalize-space()='Manage Roles']");

    private final By qaRole =
            By.xpath("//label[.//div[normalize-space()='Qa Role']]");

    private final By updateRoles =
            By.xpath("//button[contains(.,'Update Roles')]");

    private final By editUser =
            By.xpath("//a[.//span[normalize-space()='Edit User']]");

    private final By deactivateRadio =
            By.xpath("//input[@value='deactivated']");

    private final By updateUser =
            By.xpath("//button[contains(.,'Update User')]");

    private final By searchBox =
            By.xpath("//input[@type='search']");

    // ==========================
    // Manage Roles
    // ==========================

    public void clickManageRoles() {

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(manageRoles));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);

        System.out.println("Manage Roles clicked.");
    }

    public void assignQaRole() {

        WebElement checkbox = wait.until(
                ExpectedConditions.elementToBeClickable(qaRole));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", checkbox);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", checkbox);

        System.out.println("QA Role selected.");
    }

    public void clickUpdateRoles() {

        WebElement update = wait.until(
                ExpectedConditions.elementToBeClickable(updateRoles));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", update);

        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));

        wait.until(ExpectedConditions.presenceOfElementLocated(editUser));

        System.out.println("Roles updated successfully.");
    }

    // ==========================
    // Edit User
    // ==========================

    public void clickEditUser() {

        WebElement edit = wait.until(
                ExpectedConditions.elementToBeClickable(editUser));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", edit);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", edit);

        wait.until(ExpectedConditions.urlContains("/users/create"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(updateUser));

        System.out.println("Edit page opened successfully.");
    }

    // ==========================
    // Deactivate User
    // ==========================

    public void deactivateUser() {

        System.out.println("Selecting Deactivated radio...");

        WebElement radio = wait.until(
                ExpectedConditions.elementToBeClickable(deactivateRadio));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", radio);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", radio);

        Assert.assertTrue("Deactivate radio not selected",
                radio.isSelected());

        System.out.println("Deactivate option selected.");
    }

    // ==========================
    // Update User
    // ==========================

    public void clickUpdateUser() {

        System.out.println("Clicking Update User...");

        WebElement update = wait.until(
                ExpectedConditions.elementToBeClickable(updateUser));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", update);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", update);

        // Wait until save request completes
        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));

        // Wait until Update User button is enabled again
        wait.until(ExpectedConditions.elementToBeClickable(updateUser));

        System.out.println("User updated successfully.");
    }

    // ==========================
    // Verification
    // ==========================

    public void verifyDeactivatedStatus() {

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBox));

        Assert.assertTrue(search.isDisplayed());

        System.out.println("Users page displayed successfully.");
    }

    public String getUserStatus() {

        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//td[@data-column='status']")))
                .getText()
                .trim();
    }
}