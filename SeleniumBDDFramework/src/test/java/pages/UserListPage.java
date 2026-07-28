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
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    By searchBox = By.xpath("//input[@type='search']");

    public void searchUser(String userName) {

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBox));

        search.clear();
        search.sendKeys(userName);
        search.sendKeys(Keys.ENTER);

        // Wait until at least one row is displayed
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tbody/tr")));
    }
    public void openUserProfile(String userName) {

        By user = By.xpath("//button[normalize-space()='" + userName + "']");

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(user));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        wait.until(ExpectedConditions.elementToBeClickable(element));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);

        // Wait until profile page opens
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[normalize-space()='Edit User']")));
    }
    public void verifyUserStatus(String userName) {

        // Wait for search box
        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBox));

        search.clear();
        search.sendKeys(userName);
        search.sendKeys(Keys.ENTER);

        // Wait until the searched user appears
        By userButton = By.xpath("//button[normalize-space()='" + userName + "']");

        wait.until(ExpectedConditions.visibilityOfElementLocated(userButton));

        // Get entire row
        WebElement row = driver.findElement(
                By.xpath("//tbody/tr[.//button[normalize-space()='" + userName + "']]"));

        // Status column
        WebElement status = row.findElement(
                By.xpath(".//span[contains(@class,'rounded-full')]"));

        String actualStatus = status.getText().trim();

        System.out.println("Status = " + actualStatus);

        Assert.assertEquals("deactivated", actualStatus.toLowerCase());
        By rowLocator = By.xpath(
        	    "//tbody//tr[.//button[normalize-space()='" + userName + "']]");

        System.out.println("Expected : deactivated");
        System.out.println("Actual   : " + actualStatus);

        Assert.assertEquals("deactivated", actualStatus.toLowerCase());
    }
}