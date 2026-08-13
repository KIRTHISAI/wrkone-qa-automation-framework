package pages;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Login page
    private final By email =
            By.id("email");

    private final By password =
            By.id("password");

    private final By loginButton =
            By.xpath("//button[@type='submit']");

    /*
     * We don't depend only on URL.
     *
     * Change this locator if your dashboard has a more
     * reliable unique element.
     */
    private final By dashboard =
            By.xpath(
                "//*[contains(normalize-space(.),'Dashboard')]"
            );

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }

    public void enterEmail(String emailValue) {

        System.out.println(
                "Entering username: [" + emailValue + "]"
        );

        WebElement emailField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                email
                        )
                );

        emailField.clear();
        emailField.sendKeys(emailValue);
    }

    public void enterPassword(String passwordValue) {

        System.out.println("Entering password...");

        WebElement passwordField =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                password
                        )
                );

        passwordField.clear();
        passwordField.sendKeys(passwordValue);
    }

    public void clickLogin() {

        System.out.println("Waiting for Login button...");

        WebElement button =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                loginButton
                        )
                );

        button.click();

        System.out.println("Login button clicked.");

        /*
         * IMPORTANT:
         * Your application may show an alert after login.
         *
         * Wait a short time for the alert and accept it.
         */
        handleLoginAlert();

        /*
         * Give the application time to process the login.
         */
        waitForDashboard();
    }

    private void handleLoginAlert() {

        try {

            WebDriverWait alertWait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(5)
                    );

            Alert alert =
                    alertWait.until(
                            ExpectedConditions.alertIsPresent()
                    );

            System.out.println(
                    "Login alert found: " + alert.getText()
            );

            alert.accept();

            System.out.println(
                    "Login alert OK clicked."
            );

        } catch (NoAlertPresentException e) {

            System.out.println(
                    "No login alert present."
            );

        } catch (Exception e) {

            System.out.println(
                    "No login alert appeared within 5 seconds."
            );
        }
    }

    private void waitForDashboard() {

        System.out.println(
                "Waiting for dashboard..."
        );

        try {

            /*
             * First try dashboard element.
             */
            WebDriverWait dashboardWait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(20)
                    );

            dashboardWait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            dashboard
                    )
            );

            System.out.println(
                    "Dashboard detected successfully."
            );

            System.out.println(
                    "Current URL = [" +
                    driver.getCurrentUrl() +
                    "]"
            );

        } catch (Exception e) {

            /*
             * Do NOT immediately fail only because URL
             * remains /login. Print useful diagnostics.
             */
            System.out.println(
                    "Dashboard element was not detected."
            );

            System.out.println(
                    "Current URL = [" +
                    driver.getCurrentUrl() +
                    "]"
            );

            System.out.println(
                    "Page title = [" +
                    driver.getTitle() +
                    "]"
            );
        }
    }

    public void login(
            String username,
            String passwordValue
    ) {

        System.out.println("# Starting Login");

        enterEmail(username);

        enterPassword(passwordValue);

        clickLogin();
    }

    public boolean isDashboardDisplayed() {

        try {

            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            dashboard
                    )
            ).isDisplayed();

        } catch (Exception e) {

            return false;
        }
    }
}
