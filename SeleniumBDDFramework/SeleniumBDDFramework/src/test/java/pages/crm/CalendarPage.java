package pages.crm;

import java.time.Duration;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CalendarPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By calendarMenu = By.id("nav-calendar-btn");
    private final By newActivityButton = By.xpath(
            "//button[.//text()[normalize-space()='New Activity'] "
            + "or normalize-space()='New Activity']");
        private final By editActivityButton = By.xpath(
            "//button[normalize-space()='Edit Activity']");
            private final By purposeInput = By.id("activity-purpose-input");
            private final By descriptionInput = By.id("activity-description-textarea");
        private final By updateActivityButton = By.id("btn-submit");
        private final By activityUpdatedToast = By.xpath(
            "//*[contains(normalize-space(), 'Activity updated successfully')]");
            private final By calendarHeader = By.xpath(
                "//div[contains(@class, 'calendar-header-bar')]");
            private final By previousMonthButton = By.xpath(
                "//div[contains(@class, 'calendar-header-bar')]//button[1]");
            private final By nextMonthButton = By.xpath(
                "//div[contains(@class, 'calendar-header-bar')]//button[2]");

    public CalendarPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null");
        }

        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open() {
        click(calendarMenu);
        wait.until(ExpectedConditions.urlContains("/qa-crm/calendar"));
    }

    public void clickNewActivity() {
        click(newActivityButton);
        wait.until(ExpectedConditions.urlContains("/qa-crm/activities/create"));

        String createActivityUrl = driver.getCurrentUrl().split("\\?")[0];
        driver.navigate().to(createActivityUrl);
        wait.until(ExpectedConditions.urlToBe(createActivityUrl));
    }

        public void editActivity(String currentPurpose, String activityDate,
                     String updatedPurpose, String updatedDescription) {
            editActivity(currentPurpose, null, activityDate, updatedPurpose, updatedDescription);
            }

            public void editActivity(String currentPurpose, String currentDescription,
                     String activityDate, String updatedPurpose, String updatedDescription) {
            By activity = activityLocator(currentPurpose, currentDescription);

        open();
        navigateToActivityMonth(activityDate);
            scrollCalendarToActivityDate();

        WebElement activityElement = wait.until(
            ExpectedConditions.elementToBeClickable(activity));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", activityElement);
        click(activity);
        click(editActivityButton);
        wait.until(ExpectedConditions.urlContains("/qa-crm/activities/create"));

        replaceText(purposeInput, updatedPurpose);
        replaceText(descriptionInput, updatedDescription);
        click(updateActivityButton);
    }

    public void editLinkedActivity(String currentPurpose, String activityDate,
                     String updatedDescription) {
        editLinkedActivity(currentPurpose, null, activityDate, updatedDescription);
        }

        public void editLinkedActivity(String currentPurpose, String currentDescription,
                 String activityDate, String updatedDescription) {
        By activity = activityLocator(currentPurpose, currentDescription);

        open();
        navigateToActivityMonth(activityDate);
        scrollCalendarToActivityDate();

        WebElement activityElement = wait.until(
            ExpectedConditions.elementToBeClickable(activity));
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
            "arguments[0].scrollIntoView({block:'center'});", activityElement);
        click(activity);
        click(editActivityButton);
        wait.until(ExpectedConditions.urlContains("/qa-crm/activities/create"));

        replaceText(descriptionInput, updatedDescription);
        click(updateActivityButton);
    }

    private By activityLocator(String purpose, String description) {
        String purposeLiteral = toXPathLiteral(purpose);
        if (description == null || description.isBlank()) {
            return By.xpath("//*[normalize-space()=" + purposeLiteral + "]");
        }

        return By.xpath("//*[normalize-space()=" + purposeLiteral + "]"
                + " | *[normalize-space()=" + toXPathLiteral(description) + "]");
    }

    private void scrollCalendarToActivityDate() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('*').forEach(function(element) {"
                + " if (element.scrollHeight > element.clientHeight) {"
                + "   element.scrollTop = element.scrollHeight;"
                + " }"
                + "});");
    }

    public void verifyActivityUpdatedSuccessfully() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(activityUpdatedToast));
    }

    private void replaceText(By locator, String value) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollIntoView(input);
        input.clear();
        input.sendKeys(value);
    }

    private void navigateToActivityMonth(String activityDate) {
        LocalDate date = LocalDate.parse(activityDate,
                DateTimeFormatter.ofPattern("M/d/yy", Locale.ENGLISH));
        YearMonth targetMonth = YearMonth.from(date);

        for (int attempt = 0; attempt < 24; attempt++) {
            YearMonth visibleMonth = getVisibleMonth();

            if (visibleMonth.equals(targetMonth)) {
                return;
            }

            click(visibleMonth.isBefore(targetMonth)
                    ? nextMonthButton : previousMonthButton);
        }

        throw new IllegalStateException(
                "Calendar did not navigate to " + targetMonth);
    }

    private YearMonth getVisibleMonth() {
        String headerText = wait.until(
                ExpectedConditions.visibilityOfElementLocated(calendarHeader)).getText();
        String[] headerLines = headerText.split("\\R");

        return YearMonth.parse(headerLines[0].trim(),
                DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH));
    }

    private void click(By locator) {
        WebElement element = wait.until(
                ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(element);

        try {
            element.click();
        } catch (ElementClickInterceptedException exception) {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", element);
        }
    }

    private void scrollIntoView(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", element);
    }

    private String toXPathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }

        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }

        return "concat('" + value.replace("'", "', \"'\", '") + "')";
    }
}