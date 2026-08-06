package pages;

import java.time.Duration;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserListPage {

    WebDriver driver;
    WebDriverWait wait;

    public UserListPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private By searchBox = By.xpath("//input[@type='search']");

    /**
     * Search user from Users list
     */
    public void searchUser(String userName) {

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBox));

        search.clear();
        search.sendKeys(userName);
        search.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tbody/tr")));
    }

    /**
     * Open User Profile
     */
    public void openUserProfile(String userName) {

        By user = By.xpath("//button[normalize-space()='" + userName + "']");

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(user));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);

        // Wait for page to finish loading
        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));

        // Wait until Edit User button is visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[.//span[normalize-space()='Edit User']]")));

        System.out.println("User profile opened successfully.");
    }

    /**
     * Verify User Status after Deactivation
     */
    /**
     * Verify User Status
     */
    public void verifyUserStatus(String userName, String expectedStatus) {

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBox));

        search.clear();
        search.sendKeys(userName);
        search.sendKeys(Keys.ENTER);

        By statusLocator = By.xpath(
            "//button[normalize-space()='" + userName + "']" +
            "/ancestor::tr//span[contains(@class,'rounded-full')]");

        WebElement status = wait.until(
                ExpectedConditions.visibilityOfElementLocated(statusLocator));

        String actualStatus = status.getText().trim();

        System.out.println("Expected : " + expectedStatus);
        System.out.println("Actual   : " + actualStatus);

        Assert.assertEquals(
                expectedStatus.toLowerCase(),
                actualStatus.toLowerCase());
    }
}