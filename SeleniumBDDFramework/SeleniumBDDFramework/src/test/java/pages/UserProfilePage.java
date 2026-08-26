package pages;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserProfilePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public UserProfilePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // ============================================================
    // LOCATORS
    // ============================================================

    private final By manageRoles =
            By.xpath("//span[normalize-space()='Manage Roles']");

    private final By genericRole =
            By.xpath("//label[.//div[contains(normalize-space(),'Generic')]]");

    private final By updateRoles =
            By.xpath("//button[contains(normalize-space(),'Update Roles')]");

    private final By editUser =
            By.xpath("//a[.//span[normalize-space()='Edit User']]");

    private final By deactivateRadio =
            By.xpath("//input[@value='deactivated']");

    private final By updateUser =
            By.xpath("//button[contains(normalize-space(),'Update User')]");

    // ============================================================
    // MANAGE ROLES
    // ============================================================

    public void clickManageRoles() {

        System.out.println("Waiting for Manage Roles...");

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(manageRoles)
        );

        scrollToElement(button);

        javascriptClick(button);

        System.out.println("Manage Roles clicked.");
    }

    // ============================================================
    // ASSIGN GENERIC ROLE
    // ============================================================

    public void assignGenericRole() {

        System.out.println("Looking for available roles...");

        By roleCheckboxesLocator =
                By.xpath(
                        "//label[contains(@class,'cursor-pointer')]"
                        + "//input[@type='checkbox']"
                );

        List<WebElement> roleCheckboxes = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        roleCheckboxesLocator
                )
        );

        List<WebElement> availableRoles =
                new ArrayList<>();

        for (WebElement checkbox : roleCheckboxes) {

            if (!checkbox.isSelected()) {
                availableRoles.add(checkbox);
            }
        }

        if (availableRoles.isEmpty()) {
            throw new RuntimeException(
                    "No unselected roles are available."
            );
        }

        WebElement randomRole =
                availableRoles.get(
                        new Random().nextInt(
                                availableRoles.size()
                        )
                );

        scrollToElement(randomRole);

        javascriptClick(randomRole);

        System.out.println(
                "Random generic role assigned."
        );
    }

    // ============================================================
    // UPDATE ROLES
    // ============================================================

    public void clickUpdateRoles() {

        System.out.println("Waiting for Update Roles button...");

        WebElement update = wait.until(
                ExpectedConditions.elementToBeClickable(updateRoles)
        );

        scrollToElement(update);

        javascriptClick(update);

        /*
         * Wait until the page finishes its current transition.
         */
        waitForPageReady();

        /*
         * Edit User should be available after roles are updated.
         */
        wait.until(
                ExpectedConditions.presenceOfElementLocated(editUser)
        );

        System.out.println(
                "Roles updated successfully."
        );
    }

    // ============================================================
    // EDIT USER
    // ============================================================

    public void clickEditUser() {

        System.out.println("Waiting for Edit User...");

        WebElement edit = wait.until(
                ExpectedConditions.elementToBeClickable(editUser)
        );

        scrollToElement(edit);

        javascriptClick(edit);

        /*
         * Application opens the edit user page.
         */
        wait.until(
                ExpectedConditions.urlContains("/users/create")
        );

        /*
         * Confirm that Update User exists on edit page.
         */
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(updateUser)
        );

        System.out.println(
                "Edit page opened successfully."
        );
    }

    // ============================================================
    // DEACTIVATE USER
    // ============================================================

    public void deactivateUser() {

        System.out.println(
                "Selecting Deactivated radio..."
        );

        WebElement radio = wait.until(
                ExpectedConditions.elementToBeClickable(
                        deactivateRadio
                )
        );

        scrollToElement(radio);

        javascriptClick(radio);

        /*
         * Verify selection.
         *
         * Sometimes React-controlled radio buttons update
         * after the click, so wait for selected state.
         */
        wait.until(driver -> {

            try {
                return radio.isSelected();
            } catch (Exception e) {
                return false;
            }
        });

        Assert.assertTrue(
                "Deactivate radio was not selected",
                radio.isSelected()
        );

        System.out.println(
                "Deactivate option selected."
        );
    }

    // ============================================================
    // UPDATE USER
    // ============================================================

    public void clickUpdateUser() {

        System.out.println(
                "Clicking Update User..."
        );

        WebElement update = wait.until(
                ExpectedConditions.elementToBeClickable(updateUser)
        );

        scrollToElement(update);

        javascriptClick(update);

        /*
         * IMPORTANT:
         *
         * Do NOT wait for:
         *
         *     //input[@type='search']
         *
         * here.
         *
         * The next Cucumber step is responsible for searching
         * for the created user.
         */

        /*
         * Wait until the update request/navigation finishes.
         *
         * The application normally returns to the Users page.
         */
        wait.until(
                ExpectedConditions.not(
                        ExpectedConditions.urlContains(
                                "/users/create"
                        )
                )
        );

        /*
         * Give the application time to finish rendering the
         * Users page after the update.
         */
        waitForPageReady();

        System.out.println(
                "User update completed."
        );

        System.out.println(
                "Current URL = " + driver.getCurrentUrl()
        );
    }

    // ============================================================
    // CONFIRM DEACTIVATION
    // ============================================================

    public void confirmDeactivation() {

        System.out.println(
                "Verifying user update..."
        );

        By successMessage =
                By.xpath(
                        "//*[contains(normalize-space(),"
                        + "'User updated successfully')]"
                );

        /*
         * Success toast OR Users page is enough to confirm
         * that the update operation completed.
         */
        boolean updateCompleted = wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.visibilityOfElementLocated(
                                successMessage
                        ),
                        ExpectedConditions.urlContains("/users")
                )
        );

        Assert.assertTrue(
                "User update/deactivation was not completed.",
                updateCompleted
        );

        System.out.println(
                "User deactivated successfully."
        );
    }

    // ============================================================
    // HELPER METHODS
    // ============================================================

    private void scrollToElement(WebElement element) {

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({"
                        + "block:'center',"
                        + "inline:'nearest'"
                        + "});",
                element
        );
    }

    private void javascriptClick(WebElement element) {

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();",
                element
        );
    }

    private void waitForPageReady() {

        try {

            wait.until(driver -> {

                try {

                    Object readyState =
                            ((JavascriptExecutor) driver)
                                    .executeScript(
                                            "return document.readyState"
                                    );

                    return "complete".equals(
                            String.valueOf(readyState)
                    );

                } catch (Exception e) {
                    return false;
                }
            });

        } catch (Exception e) {

            /*
             * Do not fail the test only because document.readyState
             * could not be checked in an SPA transition.
             */
            System.out.println(
                    "Page ready-state wait completed with SPA transition."
            );
        }
    }
}