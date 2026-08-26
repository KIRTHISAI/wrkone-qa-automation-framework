package pages.crm;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
public class ActivityCommonPage {

    // =========================================================
    // DRIVER
    // =========================================================

    protected WebDriver driver;

    // =========================================================
    // WAIT
    // =========================================================

    protected WebDriverWait wait;

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public ActivityCommonPage(WebDriver driver) {

        if (driver == null) {
            throw new IllegalArgumentException(
                    "WebDriver cannot be null"
            );
        }

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );

        System.out.println("ActivityCommonPage initialized.");
        System.out.println(
                "Driver initialized = " + (this.driver != null)
        );
        System.out.println(
                "Wait initialized = " + (this.wait != null)
        );
    }

    // =========================================================
    // VERIFY CRM PAGE
    // =========================================================

    public void verifyCRMPageLoaded() {

        System.out.println(
                "Waiting for CRM page to load..."
        );

        wait.until(webDriver ->
                webDriver.getCurrentUrl()
                        .contains("/qa-crm")
        );

        System.out.println(
                "CRM page loaded successfully."
        );

        System.out.println(
                "Current URL = " +
                driver.getCurrentUrl()
        );
    }

    // =========================================================
    // ACTIVITIES
    // =========================================================

    private final By activitiesMenu =
            By.xpath("//span[normalize-space()='Activities']");

    private final By allActivities =
            By.xpath(
                    "//*[normalize-space()='All Activities']"
            );

    protected final By searchBox =
            By.xpath("//input[@placeholder='Search']");

    private final By createActivityButton = By.xpath(
    	    "//button[.//text()[normalize-space()='Create Activity'] "
    	    + "or normalize-space()='Create Activity']"
    	);

    // =========================================================
    // CLICK ACTIVITIES
    // =========================================================

    public void clickActivities() {

        System.out.println(
                "Waiting for Activities menu..."
        );

        verifyCRMPageLoaded();

        WebElement activities =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                activitiesMenu
                        )
                );

        scrollIntoView(activities);

        try {

            activities.click();

        } catch (Exception e) {

            System.out.println(
                    "Normal Activities click failed. "
                    + "Using JavaScript click."
            );

            jsClick(activities);
        }

        System.out.println(
                "Activities clicked."
        );
    }

    // =========================================================
    // CLICK ALL ACTIVITIES
    // =========================================================

    public void clickAllActivities() {

        System.out.println(
                "Waiting for All Activities..."
        );

        WebElement element =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                allActivities
                        )
                );

        scrollIntoView(element);

        try {

            element.click();

        } catch (Exception e) {

            jsClick(element);
        }

        System.out.println(
                "All Activities clicked."
        );
    }

    // =========================================================
    // CLICK CREATE ACTIVITY
    // =========================================================

 // =========================================================
 // CLICK CREATE ACTIVITY FROM ALL ACTIVITIES PAGE
 // =========================================================

 public void clickCreateActivity() {

     System.out.println("==========================================");
     System.out.println("Clicking Create Activity button");
     System.out.println("Current URL = " + driver.getCurrentUrl());
     System.out.println("==========================================");

     try {

         WebElement button = this.wait.until(
                 ExpectedConditions.presenceOfElementLocated(
                         createActivityButton
                 )
         );

         System.out.println(
                 "Create Activity button found."
         );

         // Scroll button into view
         ((JavascriptExecutor) driver).executeScript(
                 "arguments[0].scrollIntoView({block:'center'});",
                 button
         );

         this.wait.until(
                 ExpectedConditions.visibilityOf(button)
         );

         this.wait.until(
                 ExpectedConditions.elementToBeClickable(button)
         );

         System.out.println(
                 "Create Activity button is clickable."
         );

         try {

             button.click();

         } catch (ElementClickInterceptedException e) {

             System.out.println(
                     "Normal click intercepted. Using JavaScript click..."
             );

             ((JavascriptExecutor) driver).executeScript(
                     "arguments[0].click();",
                     button
             );
         }

         // Wait for Create Activity page
         this.wait.until(
                 ExpectedConditions.urlContains(
                         "/qa-crm/activities/create"
                 )
         );

         System.out.println(
                 "Create Activity page opened successfully."
         );

         System.out.println(
                 "Current URL = " + driver.getCurrentUrl()
         );

     } catch (TimeoutException e) {

         System.out.println(
                 "ERROR: Create Activity button was not clickable."
         );

         System.out.println(
                 "Current URL = " + driver.getCurrentUrl()
         );

         throw new AssertionError(
                 "Unable to click Create Activity button.",
                 e
         );
     }
 }
    // =========================================================
    // REQUIRED VALUE
    // =========================================================

    protected String required(
            String value,
            String fieldName) {

        if (value == null ||
                value.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    fieldName +
                    " cannot be null or empty"
            );
        }

        return value.trim();
    }

        public void searchActivity(String purpose) {

                WebElement search = wait.until(
                                ExpectedConditions.elementToBeClickable(searchBox));

                search.clear();
                search.sendKeys(required(purpose, "Activity Purpose"));
        }

    // =========================================================
    // SCROLL
    // =========================================================

    protected void scrollIntoView(
            WebElement element) {

        try {

            JavascriptExecutor js =
                    (JavascriptExecutor) driver;

            js.executeScript(
                    "arguments[0].scrollIntoView(" +
                    "{block:'center', inline:'nearest'});",
                    element
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to scroll element: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // JAVASCRIPT CLICK
    // =========================================================

    protected void jsClick(
            WebElement element) {

        JavascriptExecutor js =
                (JavascriptExecutor) driver;

        js.executeScript(
                "arguments[0].click();",
                element
        );
    }
}