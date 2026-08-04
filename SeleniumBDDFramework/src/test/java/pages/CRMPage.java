package pages;

import java.time.Duration;
import Utilities.DateTimeUtils;
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

    // ===========================
    // Locators
    // ===========================

    private By applicationsMenu = By.xpath("//span[text()='Applications']");
    private By crmMenu = By.xpath("//span[text()='CRM']");
    private By activities = By.xpath("//span[text()='Activities']");
    private By allActivities = By.xpath("//span[normalize-space()='All Activities']");
    private By createActivityMenu = By.xpath("//button[contains(text(),'Create Activity')]");

    // Activity Type Dropdown
    private By activityTypeDropdown = By.id("activity-type-select");

    // Activity Details
    private By purpose = By.xpath("//input[@placeholder='Enter activity purpose']");
    private By description = By.xpath("//textarea[@placeholder='Enter activity description...']");

    // Date & Time
    private By dateInput = By.id("activity-date-input");
    private By startTimeButton = By.id("start-time-btn");
    private By endTimeButton = By.id("end-time-btn");
    private By confirmButton = By.id("confirm-btn");

    // Assignment
    private By selfAssignment = By.id("assignmentType-self");

    // Submit
    private By createActivityButton = By.id("btn-submit");

    // ===========================
    // Navigation
    // ===========================

    public void clickApplications() {
        wait.until(ExpectedConditions.elementToBeClickable(applicationsMenu)).click();
    }

    public void clickCRM() {
        wait.until(ExpectedConditions.elementToBeClickable(crmMenu)).click();
    }

    public void verifyCRMPage() {
        wait.until(ExpectedConditions.urlContains("qa-crm"));
    }

    public void clickActivities() {
        wait.until(ExpectedConditions.elementToBeClickable(activities)).click();
    }

    public void clickAllActivities() {
        wait.until(ExpectedConditions.elementToBeClickable(allActivities)).click();
    }

    public void clickCreateActivityMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(createActivityMenu)).click();
    }

    // ===========================
    // Activity Type
    // ===========================

    public void selectActivityType(String activityType) {

        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(activityTypeDropdown));

        Select select = new Select(dropdown);
        select.selectByVisibleText(activityType);
    }

    // ===========================
    // Purpose & Description
    // ===========================

    public void enterPurpose(String value) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(purpose));

        element.clear();
        element.sendKeys(value);
    }

    public void enterDescription(String value) {

        WebElement element = wait.until(
                ExpectedConditions.visibilityOfElementLocated(description));

        element.clear();
        element.sendKeys(value);
    }

    // ===========================
    // Date
    // ===========================
    public void selectDate(int daysToAdd) {

        WebElement date = wait.until(
                ExpectedConditions.elementToBeClickable(dateInput));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", date);

        date.sendKeys(Keys.CONTROL + "a");
        date.sendKeys(DateTimeUtils.getDate(daysToAdd));
        date.sendKeys(Keys.TAB);
    }
    // ===========================
    // Start Time
     public void selectStartTime(String quickTimeId) {

    	    WebElement start = wait.until(
    	            ExpectedConditions.elementToBeClickable(startTimeButton));

    	    start.click();

    	    By time = By.id(quickTimeId);

    	    wait.until(ExpectedConditions.elementToBeClickable(time)).click();

    	    wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();

    	    wait.until(ExpectedConditions.invisibilityOfElementLocated(confirmButton));
    	}

    // ===========================
    // End Time
    // ===========================

    		 public void selectEndTime(String quickTimeId) {

    			    WebElement end = wait.until(
    			            ExpectedConditions.elementToBeClickable(endTimeButton));

    			    ((JavascriptExecutor) driver)
    			            .executeScript("arguments[0].click();", end);

    			    By time = By.id(quickTimeId);

    			    wait.until(ExpectedConditions.elementToBeClickable(time)).click();

    			    wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();

    			    wait.until(ExpectedConditions.invisibilityOfElementLocated(confirmButton));
    			}

    // ===========================
    // Assignment
    // ===========================

    public void selectAssignmentType() {

        WebElement radio = wait.until(
                ExpectedConditions.visibilityOfElementLocated(selfAssignment));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", radio);

        if (!radio.isSelected()) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", radio);
        }
    }

    // ===========================
    // Create Activity
    // ===========================

    public void clickCreateActivityButton() {

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(createActivityButton));

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", button);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", button);
    }

    // ===========================
    // Complete Activity Creation
    // ===========================

    public void createActivity(String activityType,
            String purpose,
            String description,
            int daysToAdd,
            String startTimeId,
            String endTimeId) {

selectActivityType(activityType);
enterPurpose(purpose);
enterDescription(description);

selectDate(daysToAdd);

selectStartTime(startTimeId);

selectEndTime(endTimeId);

selectAssignmentType();

clickCreateActivityButton();
}

    // ===========================
    // Verification
    // ===========================

    public void verifyActivityCreated() {
        wait.until(ExpectedConditions.urlContains("activities"));
    }
}