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
import org.openqa.selenium.StaleElementReferenceException;

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

        try {

            System.out.println(
                    "Opening user profile for: " + userName);

            WebDriverWait wait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(15));

            // -------------------------------------------------
            // WAIT FOR SEARCH RESULT
            // -------------------------------------------------

            By userResult = By.xpath(
                    "//tr[.//*[normalize-space()='"
                            + userName
                            + "']]"
            );

            WebElement row =
                    wait.until(
                            ExpectedConditions
                                    .visibilityOfElementLocated(
                                            userResult));

            System.out.println(
                    "User row found: " + userName);


            // -------------------------------------------------
            // FIND CLICKABLE USER NAME
            // -------------------------------------------------

            WebElement userElement =
                    row.findElement(
                            By.xpath(
                                    ".//*[normalize-space()='"
                                            + userName
                                            + "']"
                            )
                    );

            wait.until(
                    ExpectedConditions
                            .elementToBeClickable(
                                    userElement));


            // -------------------------------------------------
            // CLICK
            // -------------------------------------------------

            userElement.click();

            System.out.println(
                    "User profile opened successfully.");


        } catch (Exception e) {

            System.out.println(
                    "Failed to open user profile.");

            System.out.println(
                    "User Name = " + userName);

            System.out.println(
                    "Current URL = "
                            + driver.getCurrentUrl());

            throw new RuntimeException(
                    "Failed to open user profile for: "
                            + userName,
                    e);
        }
    }
    public void verifyUserStatus(
            String userName,
            String expectedStatus) {

        System.out.println(
                "Verifying status for user: "
                        + userName
                        + " | Expected status: "
                        + expectedStatus);

        WebDriverWait wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(30));

        // ---------------------------------------------------------
        // WAIT FOR USERS PAGE
        // ---------------------------------------------------------

        wait.until(
                ExpectedConditions.urlContains("/users")
        );

        // ---------------------------------------------------------
        // WAIT FOR USER NAME
        // ---------------------------------------------------------

        By userNameLocator =
                By.xpath(
                        "//*[normalize-space()='"
                                + userName
                                + "']"
                );

        WebElement userElement =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                userNameLocator
                        )
                );

        System.out.println(
                "User found in list: "
                        + userName
        );

        // ---------------------------------------------------------
        // FIND STATUS NEAR USER
        // ---------------------------------------------------------

        WebElement row =
                userElement.findElement(
                        By.xpath("./ancestor::tr")
                );

        WebElement statusElement =
                wait.until(
                        ExpectedConditions.visibilityOf(
                                row.findElement(
                                        By.xpath(
                                                ".//*[contains("
                                                        + "translate("
                                                        + "normalize-space(.),"
                                                        + " 'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                                        + " 'abcdefghijklmnopqrstuvwxyz'"
                                                        + "),"
                                                        + " '"
                                                        + expectedStatus.toLowerCase()
                                                        + "'"
                                                        + ")]"
                                        )
                                )
                        )
                );

        String actualStatus =
                statusElement.getText().trim();

        System.out.println(
                "Actual status = "
                        + actualStatus
        );

        // ---------------------------------------------------------
        // VERIFY
        // ---------------------------------------------------------

        if (!actualStatus.equalsIgnoreCase(
                expectedStatus)) {

            throw new AssertionError(
                    "User status mismatch. "
                            + "Expected = "
                            + expectedStatus
                            + ", Actual = "
                            + actualStatus
            );
        }

        System.out.println(
                "User status verified successfully: "
                        + actualStatus
        );
    }
}