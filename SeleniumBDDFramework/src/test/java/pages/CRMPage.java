package pages;

import java.time.Duration;

import model.CRMActivity;
import Utilities.DateTimeUtils;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CRMPage {

    WebDriver driver;
    WebDriverWait wait;

    public CRMPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    // ==========================================================
    // Navigation Locators
    // ==========================================================

    private By applicationsMenu =
            By.xpath("//span[text()='Applications']");

    private By crmMenu =
            By.xpath("//span[text()='CRM']");

    private By activities =
            By.xpath("//span[text()='Activities']");

    private By allActivities =
            By.xpath("//span[normalize-space()='All Activities']");

    private By createActivityMenu =
            By.xpath("//button[contains(text(),'Create Activity')]");
    

    // ==========================================================
    // Activity Details
    // ==========================================================

    private By activityTypeDropdown =
            By.id("activity-type-select");

    private By purpose =
            By.id("activity-purpose-input");

    private By description =
            By.id("activity-description-textarea");

    // ==========================================================
    // Date & Time
    // ==========================================================

    private By dateInput =
            By.id("activity-date-input");

    private By startTimeButton =
            By.id("start-time-btn");

    private By endTimeButton =
            By.id("end-time-btn");

    private By confirmButton =
            By.xpath("//button[normalize-space()='Confirm']");

    // ==========================================================
    // Assignment
    // ==========================================================

    private By selfAssignment =
            By.id("assignmentType-self");

    private By teamAssignment =
            By.id("assignmentType-team");

    private By userSearch =
            By.id("itemSearch");

    private By reason =
            By.id("assignment-reason");
    private By collaborationAssignment = By.id("assignmentType-collaboration");


    // ==========================================================
    // Submit
    // ==========================================================

    private By createActivityButton =
            By.id("btn-submit");

    // ==========================================================
    // Navigation
    // ==========================================================

    public void clickApplications() {

        wait.until(ExpectedConditions
                .elementToBeClickable(applicationsMenu))
                .click();
    }

    public void clickCRM() {

        wait.until(ExpectedConditions
                .elementToBeClickable(crmMenu))
                .click();
    }

    public void verifyCRMPage() {

        wait.until(ExpectedConditions
                .urlContains("qa-crm"));
    }

    public void clickActivities() {

        wait.until(ExpectedConditions
                .elementToBeClickable(activities))
                .click();
    }

    public void clickAllActivities() {

        wait.until(ExpectedConditions
                .elementToBeClickable(allActivities))
                .click();
    }

    public void clickCreateActivityMenu() {

        wait.until(ExpectedConditions
                .elementToBeClickable(createActivityMenu))
                .click();
    }

    // ==========================================================
    // Activity Type
    // ==========================================================

    public void selectActivityType(String type) {

        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        activityTypeDropdown));

        new Select(dropdown)
                .selectByVisibleText(type);
    }

    // ==========================================================
    // Purpose
    // ==========================================================

    public void enterPurpose(String value) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        purpose));

        element.clear();

        element.sendKeys(value);
    }

    // ==========================================================
    // Description
    // ==========================================================

    public void enterDescription(String value) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        description));

        element.clear();

        element.sendKeys(value);
    }

    // ==========================================================
    // Date
    // ==========================================================

    public void selectDate(String date) {

        WebElement txtDate = wait.until(
                ExpectedConditions.elementToBeClickable(dateInput));

        txtDate.clear();

        txtDate.sendKeys(date);

        txtDate.sendKeys(Keys.TAB);
    }
    // ==========================================================
    // Start Time
    // ==========================================================
    public void selectStartTime(String time) {

        // Convert AM/PM to uppercase
        time = time.toUpperCase();

        System.out.println("Start Time = " + time);

        wait.until(ExpectedConditions.elementToBeClickable(startTimeButton)).click();

        By quickSelect = By.xpath("//button[normalize-space()='" + time + "']");

        wait.until(ExpectedConditions.elementToBeClickable(quickSelect)).click();

        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }
    // ==========================================================
    // End Time
    // ==========================================================
    public void selectEndTime(String time) {

        time = time.toUpperCase();

        System.out.println("End Time = " + time);

        wait.until(ExpectedConditions.elementToBeClickable(endTimeButton)).click();

        By quickSelect = By.xpath("//button[normalize-space()='" + time + "']");

        wait.until(ExpectedConditions.elementToBeClickable(quickSelect)).click();

        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
    }
    // ==========================================================
    // Assignment Type
    // ==========================================================

    public void selectAssignmentType(String assignmentType, String user, String reason) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        switch (assignmentType.trim()) {

        case "I am doing this activity myself":

            WebElement self = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("assignmentType-self-label")));

            js.executeScript("arguments[0].scrollIntoView({block:'center'});", self);

            wait.until(ExpectedConditions.elementToBeClickable(self));

            js.executeScript("arguments[0].click();", self);

            break;

        case "I want to assign this activity to my teammate":

            WebElement teammate = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.id("assignmentType-teammate-label")));

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    teammate);

            js.executeScript("arguments[0].click();", teammate);

            selectUser(user);

            WebElement reasonBox = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.id("assignment-reason")));

            js.executeScript(
                    "arguments[0].scrollIntoView({block:'center'});",
                    reasonBox);

            reasonBox.clear();
            reasonBox.sendKeys(reason);

            break;
        case "I want to tag users for collaboration":

            WebElement label = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.xpath("//label[@id='assignmentType-tagUsers']")));

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", label);

            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", label);

            selectUser(user);

            break;
        default:
            throw new RuntimeException("Invalid Assignment Type : " + assignmentType);
        }
    }
    // ==========================================================
    // Select User
    // ==========================================================
    public void selectUser(String userName) {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        By searchLocator = By.id("itemSearch");

        // Wait for search box
        WebElement search = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchLocator));

        // Bring it above the sticky footer
        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                search);

        // Small upward scroll so footer doesn't overlap
        js.executeScript("window.scrollBy(0,-250);");

        wait.until(ExpectedConditions.visibilityOf(search));

        // Click using JS
        js.executeScript("arguments[0].click();", search);

        search.sendKeys(Keys.CONTROL + "a");
        search.sendKeys(Keys.DELETE);
        search.sendKeys(userName);

        // Wait for dropdown
        By optionLocator = By.xpath(
                "//div[contains(@class,'absolute')]//*[normalize-space()='"
                        + userName + "']");

        WebElement option = wait.until(
                ExpectedConditions.visibilityOfElementLocated(optionLocator));

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                option);

        wait.until(ExpectedConditions.elementToBeClickable(option));

        js.executeScript("arguments[0].click();", option);

        // Wait until dropdown disappears
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.xpath("//div[contains(@class,'absolute')]")));

        // Verify chip
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//span[contains(normalize-space(),'"
                        + userName + "')]")));
    }
    // ==========================================================
    // Enter Reason
    // ==========================================================

    public void enterReason(String reason) {

        if (reason == null || reason.trim().isEmpty())
            return;

        WebElement reasonTxt = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath("//textarea")));

        reasonTxt.clear();
        reasonTxt.sendKeys(reason);
    }

    // ==========================================================
    // Create Activity Button
    // ==========================================================
    public void clickCreateActivityButton() {

        JavascriptExecutor js = (JavascriptExecutor) driver;

        WebElement button = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        createActivityButton));

        js.executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                button);

        wait.until(ExpectedConditions.elementToBeClickable(button));

        js.executeScript("arguments[0].click();", button);
    }

    // ==========================================================
    // Complete Activity Creation
    // ==========================================================
    public void createActivity(CRMActivity activity) {

        selectActivityType(activity.getActivityType());

        enterPurpose(activity.getPurpose());

        enterDescription(activity.getDescription());

        selectDate(activity.getDate());

        selectStartTime(activity.getStartTime());

        selectEndTime(activity.getEndTime());

        selectAssignmentType(
                activity.getAssignmentType(),
                activity.getUser(),
                activity.getReason());

        clickCreateActivityButton();
    }
    // ==========================================================
    // Verification
    // ==========================================================

    public void verifyActivityCreated() {

        wait.until(ExpectedConditions.urlContains("activities"));
    }
    
}   

    // ==========================================================
    // Verification
    // ==========================================================
