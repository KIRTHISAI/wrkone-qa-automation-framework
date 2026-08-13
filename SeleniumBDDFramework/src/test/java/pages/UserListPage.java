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

        System.out.println("Opening user profile for: " + userName);

        By userLocator = By.xpath(
                "//button[normalize-space()='" + userName + "']"
        );

        int maxAttempts = 3;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {

            try {

                System.out.println(
                        "Attempt " + attempt +
                        " to open profile for: " + userName
                );

                // -------------------------------------------------
                // 1. Wait until user is present
                // -------------------------------------------------
                wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                userLocator
                        )
                );

                System.out.println(
                        "User found: " + userName
                );

                // -------------------------------------------------
                // 2. Find the user element
                // -------------------------------------------------
                WebElement userElement = driver.findElement(
                        userLocator
                );

                // -------------------------------------------------
                // 3. Scroll user into view
                // -------------------------------------------------
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].scrollIntoView({block:'center', inline:'nearest'});",
                        userElement
                );

                Thread.sleep(500);

                // -------------------------------------------------
                // 4. Re-find the element after scrolling
                // -------------------------------------------------
                userElement = wait.until(
                        ExpectedConditions.presenceOfElementLocated(
                                userLocator
                        )
                );

                // -------------------------------------------------
                // 5. Click user using JavaScript
                // -------------------------------------------------
                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();",
                        userElement
                );

                System.out.println(
                        "User clicked successfully: " + userName
                );

                // -------------------------------------------------
                // 6. Wait for page to finish loading
                // -------------------------------------------------
                wait.until(driver -> {

                    Object state = ((JavascriptExecutor) driver)
                            .executeScript(
                                    "return document.readyState"
                            );

                    return "complete".equals(state);
                });

                // -------------------------------------------------
                // 7. Wait for Edit User button
                // -------------------------------------------------
                By editUserButton = By.xpath(
                        "//a[.//span[normalize-space()='Edit User']]" +
                        " | //button[normalize-space()='Edit User']" +
                        " | //*[@role='button' and normalize-space()='Edit User']"
                );

                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                editUserButton
                        )
                );

                System.out.println(
                        "User profile opened successfully."
                );

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Stale element detected on attempt "
                        + attempt +
                        ". Re-finding user..."
                );

                // If this was the final attempt, fail the test
                if (attempt == maxAttempts) {
                    throw e;
                }

                try {

                    Thread.sleep(1000);

                } catch (InterruptedException ie) {

                    Thread.currentThread().interrupt();

                    throw new RuntimeException(
                            "Thread interrupted while retrying user profile",
                            ie
                    );
                }

            } catch (Exception e) {

                System.out.println(
                        "Failed to open user profile."
                );

                System.out.println(
                        "User Name = " + userName
                );

                System.out.println(
                        "Current URL = " + driver.getCurrentUrl()
                );

                throw new RuntimeException(
                        "Failed to open user profile for: " + userName,
                        e
                );
            }
        }
    }
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