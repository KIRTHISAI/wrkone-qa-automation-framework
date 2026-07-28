package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class UserProfilePage {

    WebDriver driver;
    WebDriverWait wait;

    public UserProfilePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    By userName = By.linkText(AdduserPage.generatedName);

    By manageRoles = By.xpath("//a[.//span[normalize-space()='Manage Roles']]");

    By qaRoleCheckbox = By.xpath("//div[normalize-space()='Qa Role']/ancestor::label//input[@type='checkbox']");

    By updateRoles =
            By.xpath("//button[contains(.,'Update Roles')]");

    By editUser = By.xpath("//a[.//span[normalize-space()='Edit User']]");
    By deactivate =
            By.xpath("//span[text()='Deactivate']");

    By updateUser =
            By.xpath("//button[contains(.,'Update User')]");
    By accountStatus = By.id("accountStatus");
    By deactivateRadio = By.xpath("//input[@type='radio' and @value='deactivated']");
    public void openUserProfile(String userName) {

        By user = By.xpath("//button[normalize-space()='" + userName + "']");

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(user));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);

        // Wait until User Details page is opened
        wait.until(ExpectedConditions.urlContains("/users/"));

        // Wait for Edit User button to appear
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[normalize-space()='Edit User']")));
    }
    public void clickManageRoles() {

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//span[normalize-space()='Manage Roles']")));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);
    }
    public void assignQaRole() {

        WebElement role = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//label[.//div[normalize-space()='Qa Role']]")));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", role);

        wait.until(ExpectedConditions.elementToBeClickable(role));

        role.click();
    }

    public void clickUpdateRoles() {
        wait.until(ExpectedConditions.elementToBeClickable(updateRoles)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class,'toast')]")));
    }

    public void clickEditUser() {

        WebElement editBtn = wait.until(
                ExpectedConditions.elementToBeClickable(editUser));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", editBtn);

        editBtn.click();
    }
    public void deactivateUser() {

        WebElement radio = wait.until(
                ExpectedConditions.elementToBeClickable(deactivateRadio));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", radio);

        radio.click();

        Assert.assertTrue("Deactivate radio is not selected", radio.isSelected());

        WebElement updateBtn = wait.until(
                ExpectedConditions.elementToBeClickable(updateUser));

        updateBtn.click();
    }
    public void clickUpdateUser() {

        WebElement update = wait.until(
                ExpectedConditions.elementToBeClickable(updateUser));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", update);

        // Wait until Users page is displayed again
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//input[@type='search']")));
    }
      public void verifyDeactivatedStatus() {

        WebElement radio = wait.until(
                ExpectedConditions.visibilityOfElementLocated(deactivateRadio));

        Assert.assertTrue(radio.isSelected());
    } 
}