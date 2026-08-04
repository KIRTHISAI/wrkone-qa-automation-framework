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
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    private By searchBox = By.xpath("//input[@type='search']");

    public void searchUser(String userName) {

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBox));

        search.clear();
        search.sendKeys(userName);
        search.sendKeys(Keys.ENTER);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//tbody/tr")));
    }
    public void openUserProfile(String userName) {

        By user = By.xpath("//button[normalize-space()='" + userName + "']");

        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(user));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", element);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", element);

        // Wait until URL changes from Users list to User Details page
        wait.until(driver ->
                driver.getCurrentUrl().matches(".*/users/[a-zA-Z0-9]+$"));

        // Wait until page is completely loaded
        wait.until(driver ->
                ((JavascriptExecutor) driver)
                        .executeScript("return document.readyState")
                        .equals("complete"));

        // Wait for Edit User button
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//a[.//span[normalize-space()='Edit User']]")));

        System.out.println("User profile opened successfully.");
    }

    public void verifyUserStatus(String userName) {

        // If still on Edit User page, go back to Users list
        if (!driver.findElements(searchBox).isEmpty()) {
            // already on Users page
        } else {
            driver.navigate().back();

            wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox));
        }

        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchBox));

        search.clear();
        search.sendKeys(userName);
        search.sendKeys(Keys.ENTER);

        By rowLocator = By.xpath(
                "//tbody//tr[.//button[normalize-space()='" + userName + "']]");

        WebElement row = wait.until(
                ExpectedConditions.visibilityOfElementLocated(rowLocator));

        WebElement status = row.findElement(
                By.xpath(".//span[contains(@class,'rounded-full')]"));

        String actualStatus = status.getText().trim();

        System.out.println("Expected : deactivated");
        System.out.println("Actual   : " + actualStatus);

        Assert.assertEquals(
                "User status is incorrect",
                "deactivated",
                actualStatus.toLowerCase());
    }
}