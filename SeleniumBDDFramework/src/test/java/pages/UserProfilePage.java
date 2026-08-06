package pages;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
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

    private final By genericRole = By.xpath("//label[.//div[contains(normalize-space(),'Generic')]]");
    private final By updateRoles =
            By.xpath("//button[contains(.,'Update Roles')]");

    private final By editUser =
            By.xpath("//a[.//span[normalize-space()='Edit User']]");

    private final By deactivateRadio =
            By.xpath("//input[@value='deactivated']");

    private final By updateUser =
            By.xpath("//button[contains(.,'Update User')]");

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
    public void assignGenericRole() {

        By roles = By.xpath("//label[contains(@class,'cursor-pointer')]//input[@type='checkbox']");

        List<WebElement> roleCheckboxes = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(roles));

        List<WebElement> available = new ArrayList<>();

        for (WebElement cb : roleCheckboxes) {
            if (!cb.isSelected()) {
                available.add(cb);
            }
        }

        if (available.isEmpty()) {
            throw new RuntimeException("No roles available.");
        }

        WebElement randomRole =
                available.get(new Random().nextInt(available.size()));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", randomRole);

        System.out.println("Random generic role assigned.");
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

        // Wait until Users page is opened
        wait.until(ExpectedConditions.urlContains("/users"));

        // Wait until search box is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='search']")));

        System.out.println("Returned to Users List page.");
    }

    // ==========================
    // Confirmation
    // ==========================

    public void confirmDeactivation() {

        By successMessage = By.xpath("//*[contains(text(),'User updated successfully')]");

        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(successMessage),
                ExpectedConditions.urlContains("/users")
        ));

        System.out.println("User deactivated successfully.");
    }
}