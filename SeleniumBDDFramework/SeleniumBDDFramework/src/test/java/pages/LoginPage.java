package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // =========================================================
    // LOCATORS
    // =========================================================

    // core-login-email-field is a DIV.
    // Target the INPUT inside it.
    private final By emailField =
            By.cssSelector("#core-login-email-field input");

    private final By passwordField =
            By.cssSelector("#core-login-password-field input");

    private final By loginButton =
            By.id("login_button");

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public LoginPage(WebDriver driver) {

        if (driver == null) {

            throw new IllegalArgumentException(
                    "WebDriver cannot be null");
        }

        this.driver = driver;

        this.wait =
                new WebDriverWait(
                        driver,
                        Duration.ofSeconds(30));
    }

    // =========================================================
    // ENTER EMAIL
    // =========================================================

    public void enterEmail(String email) {

        if (email == null || email.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Login email cannot be empty");
        }

        System.out.println(
                "Entering username: [" + email + "]");

        enterText(emailField, email);

        System.out.println(
                "Username entered successfully");
    }

    // =========================================================
    // ENTER PASSWORD
    // =========================================================

    public void enterPassword(String password) {

        if (password == null || password.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Login password cannot be empty");
        }

        System.out.println(
                "Entering password");

        enterText(passwordField, password);

        System.out.println(
                "Password entered successfully");
    }

    // =========================================================
    // CLICK LOGIN
    // =========================================================

    public void clickLogin() {

        WebElement button =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                loginButton));

        button.click();

        System.out.println(
                "Login button clicked");
    }

        private void enterText(By locator, String value) {

                WebElement element = wait.until(
                                ExpectedConditions.elementToBeClickable(locator));

                element.click();
                element.clear();
                element.sendKeys(value.trim());
        }

    // =========================================================
    // LOGIN
    // =========================================================

    public void login(
            String email,
            String password) {

        System.out.println();
        System.out.println("# Starting Login");

        enterEmail(email);

        enterPassword(password);

        clickLogin();

        System.out.println(
                "# Login submitted");
    }

    // =========================================================
    // VERIFY DASHBOARD
    // =========================================================

    public boolean isDashboardDisplayed() {

        try {

            WebDriverWait dashboardWait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(20));

            dashboardWait.until(
                    ExpectedConditions.not(
                            ExpectedConditions.urlContains(
                                    "/qa-core/login")));

            String currentUrl =
                    driver.getCurrentUrl();

            System.out.println(
                    "Current URL after login = "
                            + currentUrl);

            return currentUrl.contains(
                    "/qa-core/dashboard");

        } catch (Exception e) {

            System.out.println(
                    "Dashboard was not displayed.");

            System.out.println(
                    "Current URL = "
                            + driver.getCurrentUrl());

            return false;
        }
    }

    // =========================================================
    // VERIFY LOGIN SUCCESSFUL
    // =========================================================

    public void verifyLoginSuccessful() {

        if (!isDashboardDisplayed()) {

            throw new AssertionError(
                    "Login failed. Dashboard was not displayed. "
                    + "Current URL = "
                    + driver.getCurrentUrl());
        }

        System.out.println(
                "Login successful.");

        System.out.println(
                "Dashboard displayed successfully.");
    }

    // =========================================================
    // ENTER USERNAME
    // =========================================================

    public void enterUsername(String username) {

        enterEmail(username);
    }
}