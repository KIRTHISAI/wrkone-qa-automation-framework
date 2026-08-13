package pages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import model.CRMActivity;
import model.LinkedCRMActivity;

public class CRMPage {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    private static final Duration WAIT_DURATION =
            Duration.ofSeconds(30);

    private static final int MAX_RETRIES = 4;

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);

    // ============================================================
    // CONSTRUCTOR
    // ============================================================

    public CRMPage(WebDriver driver) {

        if (driver == null) {
            throw new IllegalArgumentException(
                    "WebDriver cannot be null");
        }

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                WAIT_DURATION);

        this.js = (JavascriptExecutor) driver;
    }

    // ============================================================
    // NAVIGATION
    // ============================================================

    private final By applicationsMenu =
            By.xpath("//*[normalize-space()='Applications']");

    private final By crmMenu =
            By.xpath("//*[normalize-space()='CRM']");

    private final By activitiesMenu =
            By.xpath("//*[normalize-space()='Activities']");

    private final By allActivitiesMenu =
            By.xpath("//*[normalize-space()='All Activities']");

    // ============================================================
    // CREATE ACTIVITY
    // ============================================================

    private final By createActivityPageButton =
            By.id("activities-page-create-button");

    private final By createActivityButton =
            By.id("btn-submit");

    // ============================================================
    // LINKED ACTIVITY
    // ============================================================

    private final By linkedActivityRadio =
            By.id("activity-create-page-category-radio-lead");

    private final By selectLeadName =
            By.xpath(
                    "//button[.//*[normalize-space()='Select Lead Name']]");

    private final By leadSearchInput =
            By.id("lead-search-input");

    private final By leadResults =
            By.cssSelector("#lead-list > *");

    private final By linkToStage =
            By.id("activity-stage-select");

    // ============================================================
    // ACTIVITY TYPE
    // ============================================================

    private final By activityTypeDropdown =
            By.id("activity-type-select");

    // ============================================================
    // LINKED PURPOSE
    //
    // Linked Activity Purpose = SELECT DROPDOWN
    // ============================================================

    private final By linkedPurposeDropdown =
            By.id("activity-purpose-select");
    private final By generalPurposeByPlaceholder =
            By.xpath(
                    "//input[" +
                            "@placeholder='Purpose' or " +
                            "@aria-label='Purpose'" +
                            "]" +
                            " | " +
                            "//textarea[" +
                            "@placeholder='Purpose' or " +
                            "@aria-label='Purpose'" +
                            "]");

    private final By generalPurposeByName =
            By.xpath(
                    "//input[" +
                            "contains(" +
                            "translate(@name," +
                            "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                            "'abcdefghijklmnopqrstuvwxyz')," +
                            "'purpose')" +
                            "]" +
                            " | " +
                            "//textarea[" +
                            "contains(" +
                            "translate(@name," +
                            "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
                            "'abcdefghijklmnopqrstuvwxyz')," +
                            "'purpose')" +
                            "]");

    private final By generalPurposeByLabel =
            By.xpath(
                    "//label[" +
                            "normalize-space()='Purpose'" +
                            "]" +
                            "/following::input[1]" +
                            " | " +
                            "//label[" +
                            "normalize-space()='Purpose'" +
                            "]" +
                            "/following::textarea[1]");

    private final By generalPurposeInsideField =
            By.xpath(
                    "//*[self::label or " +
                            "self::div or " +
                            "self::section]" +
                            "[normalize-space()='Purpose']" +
                            "/ancestor::*[" +
                            "self::div or " +
                            "self::section or " +
                            "self::form" +
                            "][1]" +
                            "]" +
                            "//input[" +
                            "not(@type='hidden')" +
                            " and " +
                            "not(@type='checkbox')" +
                            " and " +
                            "not(@type='radio')" +
                            "]" +
                            " | " +
                            "//*[self::label or " +
                            "self::div or " +
                            "self::section]" +
                            "[normalize-space()='Purpose']" +
                            "/ancestor::*[" +
                            "self::div or " +
                            "self::section or " +
                            "self::form" +
                            "][1]" +
                            "]" +
                            "//textarea");

    // ============================================================
    // DESCRIPTION
    // ============================================================

    private final By description =
            By.id("activity-description-textarea");

    // ============================================================
    // DATE / TIME
    // ============================================================

    private final By dateInput =
            By.id("activity-date-input");

    private final By startTimeButton =
            By.id("start-time-btn");

    private final By endTimeButton =
            By.id("end-time-btn");

    private final By confirmButton =
            By.xpath(
                    "//button[normalize-space()='Confirm']");

    // ============================================================
    // ASSIGNMENT
    // ============================================================

    private final By userSearch =
            By.id("itemSearch");

    private final By reason =
            By.id("assignment-reason");

    // ============================================================
    // APPLICATIONS
    // ============================================================

    public void clickApplications() {

        System.out.println("Clicking Applications...");

        click(applicationsMenu);

        System.out.println(
                "Applications clicked successfully.");
    }

    // ============================================================
    // CRM
    // ============================================================

    public void clickCRM() {

        System.out.println("Clicking CRM...");

        click(crmMenu);

        System.out.println(
                "CRM clicked successfully.");
    }

    // ============================================================
    // VERIFY CRM PAGE
    // ============================================================

    public void verifyCRMPageDisplayed() {

        System.out.println(
                "Verifying CRM page...");

        try {

            wait.until(
                    ExpectedConditions.or(

                            ExpectedConditions.urlContains(
                                    "qa-crm"),

                            ExpectedConditions
                                    .presenceOfElementLocated(
                                            activitiesMenu)
                    ));

            System.out.println(
                    "CRM page verified successfully.");

            System.out.println(
                    "Current URL = " +
                            driver.getCurrentUrl());

        } catch (Exception e) {

            throw new AssertionError(
                    "CRM page was not displayed. Current URL = "
                            + driver.getCurrentUrl(),
                    e);
        }
    }

    // ============================================================
    // ACTIVITIES
    // ============================================================

    public void clickActivities() {

        System.out.println(
                "Clicking Activities...");

        click(activitiesMenu);

        System.out.println(
                "Activities clicked successfully.");
    }

    // ============================================================
    // ALL ACTIVITIES
    // ============================================================

    public void clickAllActivities() {

        System.out.println(
                "Clicking All Activities...");

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement element =
                        waitForVisible(
                                allActivitiesMenu);

                scrollTo(element);

                clickFresh(
                        allActivitiesMenu);

                wait.until(
                        ExpectedConditions.or(

                                ExpectedConditions
                                        .urlContains(
                                                "activities"),

                                ExpectedConditions
                                        .presenceOfElementLocated(
                                                createActivityPageButton)
                        ));

                System.out.println(
                        "All Activities clicked successfully.");

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "All Activities stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "All Activities attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to open All Activities.",
                            e);
                }
            }
        }
    }

    // ============================================================
    // CREATE ACTIVITY PAGE
    // ============================================================

    public void clickCreateActivityMenu() {

        System.out.println(
                "Clicking Create Activity button...");

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement button =
                        waitForVisible(
                                createActivityPageButton);

                scrollTo(button);

                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        createActivityPageButton));

                button =
                        driver.findElement(
                                createActivityPageButton);

                try {

                    button.click();

                } catch (ElementClickInterceptedException e) {

                    js.executeScript(
                            "arguments[0].click();",
                            button);
                }

                wait.until(
                        ExpectedConditions.or(

                                ExpectedConditions
                                        .presenceOfElementLocated(
                                                activityTypeDropdown),

                                ExpectedConditions
                                        .presenceOfElementLocated(
                                                linkedActivityRadio)
                        ));

                System.out.println(
                        "Create Activity form opened successfully.");

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Create Activity button stale. Retry "
                                + attempt);

            } catch (TimeoutException e) {

                System.out.println(
                        "Create Activity timeout. Retry "
                                + attempt);

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Create Activity page did not open.",
                            e);
                }

            } catch (Exception e) {

                System.out.println(
                        "Create Activity attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to open Create Activity page.",
                            e);
                }
            }
        }
    }

    // ============================================================
    // CHROME PASSWORD WARNING
    // ============================================================

    public void handleChromePasswordWarning() {

        System.out.println(
                "Checking Chrome password warning...");

        try {

            Thread.sleep(1500);

            java.awt.Robot robot =
                    new java.awt.Robot();

            robot.keyPress(
                    java.awt.event.KeyEvent.VK_ENTER);

            robot.keyRelease(
                    java.awt.event.KeyEvent.VK_ENTER);

            Thread.sleep(1000);

            System.out.println(
                    "Chrome password warning handled.");

        } catch (Exception e) {

            System.out.println(
                    "Chrome password warning not handled: "
                            + e.getMessage());
        }
    }

    // ============================================================
    // SELECT LINKED ACTIVITY
    // ============================================================

    public void selectLinkedActivity() {

        System.out.println(
                "Selecting Linked Activity...");

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement radio =
                        waitForVisible(
                                linkedActivityRadio);

                scrollTo(radio);

                if (!radio.isSelected()) {

                    clickElement(radio);
                }

                wait.until(driver -> {

                    try {

                        return driver.findElement(
                                linkedActivityRadio)
                                .isSelected();

                    } catch (Exception e) {

                        return false;
                    }
                });

                System.out.println(
                        "Linked Activity selected.");

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Linked Activity radio stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "Linked Activity selection attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to select Linked Activity.",
                            e);
                }
            }
        }
    }

    // ============================================================
    // CREATE LINKED CRM ACTIVITY
    // ============================================================

    public void createLinkedActivity(
            LinkedCRMActivity activity) {

        if (activity == null) {

            throw new IllegalArgumentException(
                    "LinkedCRMActivity cannot be null");
        }

        System.out.println(
                "==========================================");

        System.out.println(
                "Creating LINKED CRM Activity");

        System.out.println(
                "Lead = " +
                        activity.getLeadName());

        System.out.println(
                "Activity Type = " +
                        activity.getActivityType());

        System.out.println(
                "Purpose = " +
                        activity.getPurpose());

        System.out.println(
                "Description = " +
                        activity.getDescription());

        System.out.println(
                "Date = " +
                        activity.getDate());

        System.out.println(
                "Start Time = " +
                        activity.getStartTime());

        System.out.println(
                "End Time = " +
                        activity.getEndTime());

        System.out.println(
                "Link To Stage = " +
                        activity.getLinkToStage());

        System.out.println(
                "Assignment Type = " +
                        activity.getAssignmentType());

        System.out.println(
                "User = " +
                        activity.getUser());

        System.out.println(
                "Reason = " +
                        activity.getReason());

        System.out.println(
                "==========================================");

        // --------------------------------------------------------
        // LINKED ACTIVITY
        // --------------------------------------------------------

        selectLinkedActivity();

        // --------------------------------------------------------
        // LEAD
        // --------------------------------------------------------

        selectLead(
                activity.getLeadName());

        // --------------------------------------------------------
        // ACTIVITY TYPE
        // --------------------------------------------------------

        selectActivityType(
                activity.getActivityType());

        // --------------------------------------------------------
        // LINKED PURPOSE
        //
        // IMPORTANT:
        // Linked Activity uses SELECT dropdown.
        // --------------------------------------------------------

        if (activity.getPurpose() != null &&
                !activity.getPurpose().trim().isEmpty()) {

            selectLinkedActivityPurpose(
                    activity.getPurpose());
        }

        // --------------------------------------------------------
        // DESCRIPTION
        // --------------------------------------------------------

        if (activity.getDescription() != null &&
                !activity.getDescription().trim().isEmpty()) {

            enterDescription(
                    activity.getDescription());
        }

        // --------------------------------------------------------
        // LINK TO STAGE
        // --------------------------------------------------------

        if (activity.getLinkToStage() != null &&
                !activity.getLinkToStage().trim().isEmpty()) {

            selectLinkToStage(
                    activity.getLinkToStage());
        }

        // --------------------------------------------------------
        // DATE
        // --------------------------------------------------------

        selectDate(
                activity.getDate());

        // --------------------------------------------------------
        // START TIME
        // --------------------------------------------------------

        selectStartTime(
                activity.getStartTime());

        // --------------------------------------------------------
        // END TIME
        // --------------------------------------------------------

        selectEndTime(
                activity.getEndTime());

        // --------------------------------------------------------
        // ASSIGNMENT
        // --------------------------------------------------------

        selectAssignmentType(
                activity.getAssignmentType(),
                activity.getUser(),
                activity.getReason());

        // --------------------------------------------------------
        // CREATE
        // --------------------------------------------------------

        clickCreateActivityButton();

        System.out.println(
                "Linked CRM Activity created successfully.");
    }

    // ============================================================
    // SELECT LEAD
    // ============================================================

    public void selectLead(String schoolName) {

        String expectedName =
                required(
                        schoolName,
                        "Lead/School Name");

        System.out.println(
                "Searching Lead/School = "
                        + expectedName);

        clickFresh(selectLeadName);

        WebElement searchBox =
                waitForVisible(
                        leadSearchInput);

        scrollTo(searchBox);

        searchBox.click();

        searchBox.sendKeys(
                Keys.CONTROL,
                "a");

        searchBox.sendKeys(
                Keys.BACK_SPACE);

        searchBox.sendKeys(
                expectedName);

        System.out.println(
                "Lead search entered = "
                        + expectedName);

        wait.until(driver -> {

            try {

                List<WebElement> results =
                        driver.findElements(
                                leadResults);

                for (WebElement result : results) {

                    if (result.isDisplayed()) {
                        return true;
                    }
                }

            } catch (Exception ignored) {
            }

            return false;
        });

        boolean selected = false;

        for (int attempt = 1;
             attempt <= MAX_RETRIES && !selected;
             attempt++) {

            try {

                List<WebElement> results =
                        driver.findElements(
                                leadResults);

                System.out.println(
                        "Lead results found = "
                                + results.size());

                for (WebElement result : results) {

                    if (!result.isDisplayed()) {
                        continue;
                    }

                    String resultText =
                            result.getText()
                                    .trim();

                    System.out.println(
                            "Lead result = ["
                                    + resultText
                                    + "]");

                    String displayedName =
                            resultText;

                    if (resultText.contains("·")) {

                        displayedName =
                                resultText
                                        .split("·")[0]
                                        .trim();

                    } else if (resultText.contains("\n")) {

                        displayedName =
                                resultText
                                        .split("\\R")[0]
                                        .trim();
                    }

                    if (displayedName.equalsIgnoreCase(
                            expectedName)) {

                        scrollTo(result);

                        try {

                            result.click();

                        } catch (
                                ElementClickInterceptedException e) {

                            js.executeScript(
                                    "arguments[0].click();",
                                    result);
                        }

                        selected = true;

                        System.out.println(
                                "Lead selected successfully = "
                                        + expectedName);

                        break;
                    }
                }

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Lead result stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "Lead selection attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());
            }

            if (!selected) {

                try {

                    Thread.sleep(500);

                } catch (InterruptedException e) {

                    Thread.currentThread()
                            .interrupt();
                }
            }
        }

        if (!selected) {

            throw new NoSuchElementException(
                    "Exact Lead not found: "
                            + expectedName);
        }
    }

    // ============================================================
    // ACTIVITY TYPE
    // ============================================================

    public void selectActivityType(
            String type) {

        String requiredType =
                required(
                        type,
                        "Activity Type");

        System.out.println(
                "Selecting Activity Type = ["
                        + requiredType
                        + "]");

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement dropdown =
                        waitForVisible(
                                activityTypeDropdown);

                scrollTo(dropdown);

                Select select =
                        new Select(dropdown);

                select.selectByVisibleText(
                        requiredType);

                wait.until(driver -> {

                    try {

                        Select current =
                                new Select(
                                        driver.findElement(
                                                activityTypeDropdown));

                        String selected =
                                current
                                        .getFirstSelectedOption()
                                        .getText()
                                        .trim();

                        return selected.equalsIgnoreCase(
                                requiredType);

                    } catch (Exception e) {

                        return false;
                    }
                });

                System.out.println(
                        "Activity Type selected = ["
                                + requiredType
                                + "]");

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Activity Type stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "Activity Type attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to select Activity Type: "
                                    + requiredType,
                            e);
                }
            }
        }
    }

    // ============================================================
    // GENERAL ACTIVITY PURPOSE
    //
    // THIS IS THE IMPORTANT FIX
    // ============================================================

    public void enterPurpose(String value) {

        String purposeValue = required(value, "Purpose");

        System.out.println("Entering GENERAL Activity Purpose = [" + purposeValue + "]");

        WebElement purposeField = null;

        for (int retry = 1; retry <= 4; retry++) {

            try {
                WebElement currentField = wait.until(driver -> {

                    try {

                        WebElement element = findGeneralPurposeField1();

                        if (element != null
                                && element.isDisplayed()
                                && element.isEnabled()) {

                            return element;
                        }

                    } catch (StaleElementReferenceException e) {
                        // React re-rendered the field. Try again.
                    } catch (NoSuchElementException e) {
                        // Field is not available yet.
                    } catch (Exception e) {
                        // Ignore and retry.
                    }

                    return null;
                });

                /*
                 * Assign only AFTER the wait is completed.
                 * This avoids the effectively-final compilation problem.
                 */
                purposeField = currentField;

                scrollIntoView(purposeField);

                try {
                    purposeField.click();
                } catch (Exception e) {

                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].focus();",
                            purposeField
                    );
                }

                /*
                 * Clear existing value safely.
                 */
                try {
                    purposeField.sendKeys(Keys.CONTROL, "a");
                    purposeField.sendKeys(Keys.BACK_SPACE);
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].value = '';",
                            purposeField
                    );
                }

                /*
                 * Enter General Activity Purpose.
                 */
                purposeField.sendKeys(purposeValue);

                /*
                 * Trigger React/Angular change events.
                 */
                purposeField.sendKeys(Keys.TAB);

                /*
                 * Verify that the value was actually entered.
                 */
                String enteredValue = purposeField.getAttribute("value");

                if (enteredValue == null || enteredValue.trim().isEmpty()) {

                    enteredValue = purposeField.getAttribute("textContent");
                }

                if (enteredValue != null
                        && enteredValue.trim().equals(purposeValue.trim())) {

                    System.out.println(
                            "General Purpose entered successfully = ["
                                    + purposeValue + "]"
                    );

                    return;
                }

                /*
                 * If sendKeys didn't update React's controlled input,
                 * set the value using JavaScript and fire input/change events.
                 */
                ((JavascriptExecutor) driver).executeScript(
                        """
                        const element = arguments[0];
                        const value = arguments[1];

                        const prototype =
                            Object.getPrototypeOf(element);

                        const descriptor =
                            Object.getOwnPropertyDescriptor(
                                prototype,
                                'value'
                            );

                        if (descriptor && descriptor.set) {
                            descriptor.set.call(element, value);
                        } else {
                            element.value = value;
                        }

                        element.dispatchEvent(
                            new Event('input', { bubbles: true })
                        );

                        element.dispatchEvent(
                            new Event('change', { bubbles: true })
                        );
                        """,
                        purposeField,
                        purposeValue
                );

                purposeField.sendKeys(Keys.TAB);

                /*
                 * Verify again.
                 */
                String finalValue =
                        purposeField.getAttribute("value");

                if (finalValue != null
                        && finalValue.trim().equals(purposeValue.trim())) {

                    System.out.println(
                            "General Purpose entered successfully = ["
                                    + purposeValue + "]"
                    );

                    return;
                }

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "General Purpose field became stale. Retry "
                                + retry
                );

            } catch (TimeoutException e) {

                System.out.println(
                        "General Purpose timeout. Retry "
                                + retry
                );

            } catch (Exception e) {

                System.out.println(
                        "General Purpose attempt failed. Retry "
                                + retry
                                + " : "
                                + e.getMessage()
                );
            }

            /*
             * Small delay before React re-renders the field.
             */
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        throw new IllegalStateException(
                "Unable to enter General Activity Purpose: ["
                        + purposeValue
                        + "]"
        );
    }
    private WebElement findGeneralPurposeField1() {

        List<By> locators = Arrays.asList(

                // Most likely General Activity Purpose field
                By.cssSelector("input[name='purpose']"),

                By.cssSelector("textarea[name='purpose']"),

                By.cssSelector("input[placeholder*='Purpose']"),

                By.cssSelector("textarea[placeholder*='Purpose']"),

                By.xpath(
                        "//label[contains(normalize-space(),'Purpose')]" +
                        "/following::input[1]"
                ),

                By.xpath(
                        "//label[contains(normalize-space(),'Purpose')]" +
                        "/following::textarea[1]"
                ),

                By.xpath(
                        "//*[contains(normalize-space(),'Purpose')]" +
                        "/following::input[1]"
                ),

                By.xpath(
                        "//*[contains(normalize-space(),'Purpose')]" +
                        "/following::textarea[1]"
                )
        );

        for (By locator : locators) {

            try {

                List<WebElement> elements =
                        driver.findElements(locator);

                for (WebElement element : elements) {

                    if (element.isDisplayed()
                            && element.isEnabled()) {
                        String tagName =
                                element.getTagName();

                        String type =
                                element.getAttribute("type");

                        String id =
                                element.getAttribute("id");

                        if ("select".equalsIgnoreCase(tagName)) {
                            continue;
                        }

                        if ("activity-purpose-select".equals(id)) {
                            continue;
                        }

                        if ("select".equalsIgnoreCase(type)) {
                            continue;
                        }

                        return element;
                    }
                }

            } catch (StaleElementReferenceException e) {
                // Try next locator.
            } catch (Exception e) {
                // Try next locator.
            }
        }

        throw new NoSuchElementException(
                "General Activity Purpose input field was not found."
        );
    }
    private void scrollIntoView(WebElement element) {

        ((JavascriptExecutor) driver).executeScript(
                """
                arguments[0].scrollIntoView({
                    behavior: 'instant',
                    block: 'center',
                    inline: 'nearest'
                });
                """,
                element
        );
    }
    private WebElement findGeneralPurposeField() {

        By[] locators = {

                generalPurposeByPlaceholder,

                generalPurposeByName,

                generalPurposeByLabel,

                generalPurposeInsideField
        };

        for (By locator : locators) {

            try {

                List<WebElement> elements =
                        driver.findElements(locator);

                for (WebElement element : elements) {

                    try {

                        if (!element.isDisplayed()) {
                            continue;
                        }

                        String tag =
                                element.getTagName();

                        if ("select".equalsIgnoreCase(tag)) {
                            continue;
                        }

                        String id =
                                element.getAttribute("id");

                        if ("activity-purpose-select"
                                .equalsIgnoreCase(id)) {

                            continue;
                        }

                        String type =
                                element.getAttribute("type");

                        if ("hidden".equalsIgnoreCase(type)) {
                            continue;
                        }

                        if ("checkbox".equalsIgnoreCase(type)) {
                            continue;
                        }

                        if ("radio".equalsIgnoreCase(type)) {
                            continue;
                        }

                        System.out.println(
                                "General Purpose field found using locator = "
                                        + locator);

                        System.out.println(
                                "Purpose field tag = "
                                        + tag);

                        System.out.println(
                                "Purpose field id = "
                                        + id);

                        return element;

                    } catch (
                            StaleElementReferenceException ignored) {
                    }
                }

            } catch (Exception ignored) {
            }
        }

        return null;
    }
    private void selectLinkedActivityPurpose(
            String value) {

        String requiredPurpose =
                required(
                        value,
                        "Linked Activity Purpose");

        System.out.println(
                "Selecting LINKED Activity Purpose = ["
                        + requiredPurpose
                        + "]");

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement dropdown =
                        waitForVisible(
                                linkedPurposeDropdown);

                scrollTo(dropdown);

                wait.until(driver -> {

                    try {

                        WebElement element =
                                driver.findElement(
                                        linkedPurposeDropdown);

                        return element.isDisplayed()
                                && element.isEnabled();

                    } catch (Exception e) {

                        return false;
                    }
                });

                dropdown =
                        driver.findElement(
                                linkedPurposeDropdown);

                Select select =
                        new Select(dropdown);

                WebElement matchingOption = null;

                for (WebElement option :
                        select.getOptions()) {

                    String optionText =
                            option.getText()
                                    .trim();

                    System.out.println(
                            "Linked Purpose option = ["
                                    + optionText
                                    + "]");

                    if (optionText.equalsIgnoreCase(
                            requiredPurpose)) {

                        matchingOption = option;

                        break;
                    }
                }

                if (matchingOption == null) {

                    throw new NoSuchElementException(
                            "Linked Activity Purpose option not found: ["
                                    + requiredPurpose
                                    + "]");
                }

                String actualOptionText =
                        matchingOption.getText()
                                .trim();

                select.selectByVisibleText(
                        actualOptionText);

                wait.until(driver -> {

                    try {

                        Select current =
                                new Select(
                                        driver.findElement(
                                                linkedPurposeDropdown));

                        String selected =
                                current
                                        .getFirstSelectedOption()
                                        .getText()
                                        .trim();

                        return selected.equalsIgnoreCase(
                                requiredPurpose);

                    } catch (Exception e) {

                        return false;
                    }
                });

                System.out.println(
                        "LINKED Activity Purpose selected successfully = ["
                                + requiredPurpose
                                + "]");

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Linked Purpose dropdown stale. Retry "
                                + attempt);

            } catch (TimeoutException e) {

                System.out.println(
                        "Linked Purpose dropdown timeout. Retry "
                                + attempt);

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to select Linked Activity Purpose: ["
                                    + requiredPurpose
                                    + "]",
                            e);
                }

            } catch (Exception e) {

                System.out.println(
                        "Linked Purpose attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to select Linked Activity Purpose: ["
                                    + requiredPurpose
                                    + "]",
                            e);
                }
            }
        }
    }

    // ============================================================
    // DESCRIPTION
    // ============================================================

    public void enterDescription(String value) {

        String descriptionValue =
                required(
                        value,
                        "Description");

        System.out.println(
                "Entering Description = ["
                        + descriptionValue
                        + "]");

        enterText(
                description,
                descriptionValue,
                "Description");

        System.out.println(
                "Description entered successfully.");
    }

    // ============================================================
    // LINK TO STAGE
    // ============================================================

    private void selectLinkToStage(
            String stage) {

        String requiredStage =
                required(
                        stage,
                        "Link To Stage");

        WebElement dropdown =
                waitForVisible(
                        linkToStage);

        scrollTo(dropdown);

        Select select =
                new Select(dropdown);

        select.selectByVisibleText(
                requiredStage);

        System.out.println(
                "Link To Stage selected = ["
                        + requiredStage
                        + "]");
    }

    // ============================================================
    // DATE
    // ============================================================

    public void selectDate(String excelDate) {

        String inputDate =
                required(
                        excelDate,
                        "Activity Date");

        LocalDate parsedDate = null;

        DateTimeFormatter[] formats = {

                DateTimeFormatter.ofPattern(
                        "dd-MM-yyyy"),

                DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd"),

                DateTimeFormatter.ofPattern(
                        "M/d/yy"),

                DateTimeFormatter.ofPattern(
                        "M/d/yyyy")
        };

        for (DateTimeFormatter formatter :
                formats) {

            try {

                parsedDate =
                        LocalDate.parse(
                                inputDate,
                                formatter);

                break;

            } catch (DateTimeParseException ignored) {
            }
        }

        if (parsedDate == null) {

            throw new IllegalArgumentException(
                    "Invalid Activity Date: "
                            + excelDate);
        }

        String htmlDate =
                parsedDate.format(
                        DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd"));

        System.out.println(
                "Selecting Date = "
                        + inputDate
                        + " -> "
                        + htmlDate);

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement dateElement =
                        waitForPresent(
                                dateInput);

                scrollTo(dateElement);

                removeReadonly(
                        dateElement);

                setDateValue(
                        dateElement,
                        htmlDate);

                wait.until(driver -> {

                    try {

                        String actual =
                                driver.findElement(
                                        dateInput)
                                        .getAttribute(
                                                "value");

                        return htmlDate.equals(
                                actual);

                    } catch (Exception e) {

                        return false;
                    }
                });

                System.out.println(
                        "Activity Date selected = "
                                + htmlDate);

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Date field stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "Date selection attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to select date: "
                                    + inputDate,
                            e);
                }
            }
        }
    }

    // ============================================================
    // START TIME
    // ============================================================

    public void selectStartTime(String time) {

        String normalized =
                normalizeTime(time);

        System.out.println(
                "Selecting Start Time = "
                        + normalized);

        selectTime(
                startTimeButton,
                normalized);
    }

    // ============================================================
    // END TIME
    // ============================================================

    public void selectEndTime(String time) {

        String normalized =
                normalizeTime(time);

        System.out.println(
                "Selecting End Time = "
                        + normalized);

        selectTime(
                endTimeButton,
                normalized);
    }

    // ============================================================
    // TIME
    // ============================================================

    private void selectTime(
            By timeButton,
            String time) {

        openTimePicker(
                timeButton);

        selectTimeOption(
                time);

        confirmTimePicker();

        verifyTime(
                timeButton,
                time);
    }

    // ============================================================
    // NORMALIZE TIME
    // ============================================================

    private String normalizeTime(
            String time) {

        String value =
                required(
                        time,
                        "Time")
                        .replaceAll(
                                "\\s+",
                                " ")
                        .trim()
                        .toUpperCase(
                                Locale.ENGLISH);

        try {

            LocalTime parsed =
                    LocalTime.parse(
                            value,
                            TIME_FORMAT);

            return parsed.format(
                    TIME_FORMAT)
                    .toUpperCase(
                            Locale.ENGLISH);

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "Invalid time: "
                            + time
                            + ". Expected format like 9:00 AM",
                    e);
        }
    }

    // ============================================================
    // OPEN TIME PICKER
    // ============================================================

    private void openTimePicker(
            By locator) {

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement button =
                        waitForVisible(
                                locator);

                scrollTo(button);

                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        locator));

                clickFresh(
                        locator);

                waitForTimeOptions();

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Time button stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "Opening time picker attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to open time picker.",
                            e);
                }
            }
        }
    }

    // ============================================================
    // WAIT TIME OPTIONS
    // ============================================================

    private void waitForTimeOptions() {

        By timeOptions =
                By.xpath(
                        "//button[" +
                                "contains(normalize-space(.),'AM')" +
                                " or " +
                                "contains(normalize-space(.),'PM')" +
                                "]");

        wait.until(driver -> {

            try {

                for (WebElement option :
                        driver.findElements(
                                timeOptions)) {

                    if (option.isDisplayed()) {

                        return true;
                    }
                }

            } catch (Exception ignored) {
            }

            return false;
        });
    }

    // ============================================================
    // SELECT TIME OPTION
    // ============================================================

    private void selectTimeOption(
            String time) {

        LocalTime parsedTime;

        try {

            parsedTime =
                    LocalTime.parse(
                            time,
                            TIME_FORMAT);

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "Invalid time: "
                            + time,
                    e);
        }

        String normalTime =
                parsedTime.format(
                        DateTimeFormatter.ofPattern(
                                "h:mm a",
                                Locale.ENGLISH));

        String paddedTime =
                parsedTime.format(
                        DateTimeFormatter.ofPattern(
                                "hh:mm a",
                                Locale.ENGLISH));

        By timeOption =
                By.xpath(
                        "//button[" +
                                "normalize-space(.)="
                                + xpathLiteral(
                                        normalTime)
                                + " or " +
                                "normalize-space(.)="
                                + xpathLiteral(
                                        paddedTime)
                                + "]");

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement option =
                        waitForDisplayed(
                                timeOption);

                scrollTo(option);

                clickElement(option);

                System.out.println(
                        "Time option clicked = "
                                + normalTime);

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Time option stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "Time selection attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to select time: "
                                    + time,
                            e);
                }
            }
        }
    }

    // ============================================================
    // CONFIRM TIME
    // ============================================================

    private void confirmTimePicker() {

        try {

            List<WebElement> confirms =
                    driver.findElements(
                            confirmButton);

            for (WebElement confirm :
                    confirms) {

                try {

                    if (confirm.isDisplayed()
                            && confirm.isEnabled()) {

                        scrollTo(confirm);

                        clickElement(confirm);

                        System.out.println(
                                "Time picker confirmed.");

                        return;
                    }

                } catch (
                        StaleElementReferenceException ignored) {
                }
            }

        } catch (Exception ignored) {
        }
    }

    // ============================================================
    // VERIFY TIME
    // ============================================================

    private void verifyTime(
            By locator,
            String expected) {

        wait.until(driver -> {

            try {

                WebElement button =
                        driver.findElement(
                                locator);

                String actual =
                        getElementText(button);

                return sameTime(
                        actual,
                        expected);

            } catch (Exception e) {

                return false;
            }
        });

        System.out.println(
                "Time selected successfully = "
                        + normalizeTime(expected));
    }

    // ============================================================
    // SAME TIME
    // ============================================================

    private boolean sameTime(
            String actual,
            String expected) {

        if (actual == null
                || actual.trim().isEmpty()) {

            return false;
        }

        String actualValue =
                actual
                        .replaceAll(
                                "\\s+",
                                " ")
                        .trim()
                        .toUpperCase(
                                Locale.ENGLISH);

        String expectedValue =
                normalizeTime(expected);

        return actualValue.contains(
                expectedValue);
    }

    // ============================================================
    // ASSIGNMENT TYPE
    // ============================================================

    public void selectAssignmentType(
            String assignmentType,
            String user,
            String reasonText) {

        String type =
                required(
                        assignmentType,
                        "Assignment Type");

        // --------------------------------------------------------
        // SELF
        // --------------------------------------------------------

        if (type.equalsIgnoreCase(
                "I am doing this activity myself")) {

            clickAssignmentByText(type);

            System.out.println(
                    "Assignment Type selected = "
                            + type);

            return;
        }

        // --------------------------------------------------------
        // TEAMMATE
        // --------------------------------------------------------

        if (type.equalsIgnoreCase(
                "I want to assign this activity to my teammate")) {

            clickAssignmentByText(type);

            waitForAssignmentUserField();

            String selectedUser =
                    required(
                            user,
                            "User");

            selectSingleUser(
                    selectedUser);

            if (reasonText != null
                    && !reasonText.trim().isEmpty()) {

                enterReason(
                        reasonText);
            }

            return;
        }

        // --------------------------------------------------------
        // COLLABORATION
        // --------------------------------------------------------

        if (type.equalsIgnoreCase(
                "I want to tag users for collaboration")) {

            clickAssignmentByText(type);

            waitForAssignmentUserField();

            String selectedUser =
                    required(
                            user,
                            "User");

            selectCollaborationUsers(
                    selectedUser);

            return;
        }

        throw new IllegalArgumentException(
                "Unsupported Assignment Type: ["
                        + assignmentType
                        + "]");
    }

    // ============================================================
    // CLICK ASSIGNMENT TYPE
    // ============================================================

    private void clickAssignmentByText(
            String assignmentText) {

        String text =
                required(
                        assignmentText,
                        "Assignment Type");

        By locator =
                By.xpath(
                        "//*[self::label or self::button " +
                                "or self::div or self::span]" +
                                "[normalize-space(.)="
                                + xpathLiteral(text)
                                + "]");

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement element =
                        waitForDisplayed(
                                locator);

                scrollTo(element);

                clickElement(element);

                System.out.println(
                        "Assignment selected = ["
                                + text
                                + "]");

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Assignment element stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "Assignment selection attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to select Assignment Type: ["
                                    + text
                                    + "]",
                            e);
                }
            }
        }
    }

    // ============================================================
    // WAIT ASSIGNMENT USER
    // ============================================================

    private void waitForAssignmentUserField() {

        wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                userSearch));

        System.out.println(
                "Assignment user search field is visible.");
    }

    // ============================================================
    // COLLABORATION USERS
    // ============================================================

    public void selectCollaborationUsers(
            String users) {

        String userData =
                required(
                        users,
                        "Collaboration Users");

        String[] userList =
                userData.split(",");

        for (String user :
                userList) {

            String userName =
                    user.trim();

            if (!userName.isEmpty()) {

                selectSingleUser(
                        userName);
            }
        }
    }

    // ============================================================
    // SELECT SINGLE USER
    // ============================================================

    private void selectSingleUser(
            String userName) {

        String user =
                required(
                        userName,
                        "User");

        System.out.println(
                "Selecting User = ["
                        + user
                        + "]");

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement search =
                        waitForVisible(
                                userSearch);

                scrollTo(search);

                search =
                        driver.findElement(
                                userSearch);

                search.click();

                search.sendKeys(
                        Keys.CONTROL,
                        "a");

                search.sendKeys(
                        Keys.BACK_SPACE);

                search.sendKeys(
                        user);

                By userOption =
                        By.xpath(
                                "//*[normalize-space(text())="
                                        + xpathLiteral(user)
                                        + "]");

                WebElement selectedOption =
                        waitForDisplayed(
                                userOption);

                scrollTo(
                        selectedOption);

                clickElement(
                        selectedOption);

                System.out.println(
                        "User selected successfully = ["
                                + user
                                + "]");

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "User element stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "User selection attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to select user: ["
                                    + user
                                    + "]",
                            e);
                }
            }
        }
    }

    // ============================================================
    // REASON
    // ============================================================

    public void enterReason(
            String value) {

        String reasonValue =
                required(
                        value,
                        "Assignment Reason");

        enterText(
                reason,
                reasonValue,
                "Assignment Reason");

        System.out.println(
                "Reason entered = ["
                        + reasonValue
                        + "]");
    }

    // ============================================================
    // CREATE ACTIVITY BUTTON
    // ============================================================

    public void clickCreateActivityButton() {

        System.out.println(
                "Waiting for Create Activity button...");

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement button =
                        waitForVisible(
                                createActivityButton);

                scrollTo(button);

                wait.until(driver -> {

                    try {

                        WebElement current =
                                driver.findElement(
                                        createActivityButton);

                        return current.isDisplayed()
                                && current.isEnabled();

                    } catch (Exception e) {

                        return false;
                    }
                });

                button =
                        driver.findElement(
                                createActivityButton);

                clickElement(
                        button);

                System.out.println(
                        "Create Activity button clicked successfully.");

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Create button stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "Create button attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to click Create Activity button.",
                            e);
                }
            }
        }
    }

    // ============================================================
    // COMPATIBILITY
    // ============================================================

    public void clickCreateActivity() {

        clickCreateActivityButton();
    }

    // ============================================================
    // GENERAL CRM ACTIVITY
    // ============================================================

    public void createActivity(
            CRMActivity activity) {

        if (activity == null) {

            throw new IllegalArgumentException(
                    "CRMActivity cannot be null");
        }

        System.out.println(
                "==========================================");

        System.out.println(
                "Creating GENERAL CRM Activity");

        System.out.println(
                "Activity Type = "
                        + activity.getActivityType());

        System.out.println(
                "Purpose = "
                        + activity.getPurpose());

        System.out.println(
                "Description = "
                        + activity.getDescription());

        System.out.println(
                "Date = "
                        + activity.getDate());

        System.out.println(
                "Start Time = "
                        + activity.getStartTime());

        System.out.println(
                "End Time = "
                        + activity.getEndTime());

        System.out.println(
                "Assignment Type = "
                        + activity.getAssignmentType());

        System.out.println(
                "User = "
                        + activity.getUser());

        System.out.println(
                "Reason = "
                        + activity.getReason());

        System.out.println(
                "==========================================");

        // --------------------------------------------------------
        // ACTIVITY TYPE
        // --------------------------------------------------------

        selectActivityType(
                activity.getActivityType());

        // --------------------------------------------------------
        // GENERAL PURPOSE
        //
        // IMPORTANT:
        // DO NOT call selectLinkedActivityPurpose() here.
        // --------------------------------------------------------

        if (activity.getPurpose() != null
                && !activity.getPurpose()
                .trim()
                .isEmpty()) {

            enterPurpose(
                    activity.getPurpose());
        }

        // --------------------------------------------------------
        // DESCRIPTION
        // --------------------------------------------------------

        if (activity.getDescription() != null
                && !activity.getDescription()
                .trim()
                .isEmpty()) {

            enterDescription(
                    activity.getDescription());
        }

        // --------------------------------------------------------
        // DATE
        // --------------------------------------------------------

        selectDate(
                activity.getDate());

        // --------------------------------------------------------
        // START TIME
        // --------------------------------------------------------

        selectStartTime(
                activity.getStartTime());

        // --------------------------------------------------------
        // END TIME
        // --------------------------------------------------------

        selectEndTime(
                activity.getEndTime());

        // --------------------------------------------------------
        // ASSIGNMENT
        // --------------------------------------------------------

        selectAssignmentType(
                activity.getAssignmentType(),
                activity.getUser(),
                activity.getReason());

        // --------------------------------------------------------
        // CREATE
        // --------------------------------------------------------

        clickCreateActivityButton();

        System.out.println(
                "GENERAL CRM Activity created successfully.");
    }

    // ============================================================
    // COMPATIBILITY
    // ============================================================

    public void createCRMActivity(
            CRMActivity activity) {

        createActivity(activity);
    }

    // ============================================================
    // VERIFY ACTIVITY CREATED
    // ============================================================

    public void verifyActivityCreatedSuccessfully() {

        verifyActivityCreated();
    }

    public void verifyActivityCreated() {

        System.out.println(
                "Verifying Activity creation...");

        By successMessage =
                By.xpath(
                        "//*[contains(" +
                                "translate(normalize-space(.)," +
                                "'abcdefghijklmnopqrstuvwxyz'," +
                                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ')," +
                                "'ACTIVITY')" +
                                " and " +
                                "contains(" +
                                "translate(normalize-space(.)," +
                                "'abcdefghijklmnopqrstuvwxyz'," +
                                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ')," +
                                "'CREATED')]");

        try {

            waitForVisible(
                    successMessage);

            System.out.println(
                    "Activity creation success message displayed.");

            return;

        } catch (Exception ignored) {
        }

        By successToast =
                By.xpath(
                        "//*[contains(" +
                                "translate(normalize-space(.)," +
                                "'abcdefghijklmnopqrstuvwxyz'," +
                                "'ABCDEFGHIJKLMNOPQRSTUVWXYZ')," +
                                "'SUCCESS')]");

        try {

            waitForVisible(
                    successToast);

            System.out.println(
                    "Success message displayed.");

            return;

        } catch (Exception ignored) {
        }

        try {

            wait.until(driver -> {

                try {

                    List<WebElement> buttons =
                            driver.findElements(
                                    createActivityButton);

                    for (WebElement button :
                            buttons) {

                        if (button.isDisplayed()) {

                            return false;
                        }
                    }

                    return true;

                } catch (Exception e) {

                    return true;
                }
            });

            System.out.println(
                    "Activity form disappeared. "
                            + "Activity creation considered successful.");

            return;

        } catch (Exception e) {

            throw new AssertionError(
                    "Unable to verify Activity creation. "
                            + "Current URL = "
                            + driver.getCurrentUrl(),
                    e);
        }
    }

    // ============================================================
    // GENERIC CLICK
    // ============================================================

    private void click(
            By locator) {

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement element =
                        waitForVisible(
                                locator);

                scrollTo(element);

                wait.until(
                        ExpectedConditions
                                .elementToBeClickable(
                                        locator));

                clickFresh(
                        locator);

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Element stale. Retry "
                                + attempt);

            } catch (Exception e) {

                System.out.println(
                        "Click attempt "
                                + attempt
                                + " failed: "
                                + e.getMessage());

                if (attempt == MAX_RETRIES) {

                    throw new IllegalStateException(
                            "Unable to click element: "
                                    + locator,
                            e);
                }
            }
        }
    }

    // ============================================================
    // CLICK ELEMENT
    // ============================================================

    private void clickElement(
            WebElement element) {

        if (element == null) {

            throw new IllegalArgumentException(
                    "Element cannot be null");
        }

        try {

            scrollTo(element);

            element.click();

        } catch (ElementClickInterceptedException e) {

            js.executeScript(
                    "arguments[0].click();",
                    element);

        } catch (StaleElementReferenceException e) {

            throw e;

        } catch (Exception e) {

            js.executeScript(
                    "arguments[0].click();",
                    element);
        }
    }

    // ============================================================
    // FRESH CLICK
    // ============================================================

    private void clickFresh(
            By locator) {

        WebElement element =
                waitForVisible(
                        locator);

        clickElement(
                element);
    }

    // ============================================================
    // ENTER TEXT
    // ============================================================

    private void enterText(
            By locator,
            String value,
            String fieldName) {

        String text =
                required(
                        value,
                        fieldName);

        for (int attempt = 1;
             attempt <= MAX_RETRIES;
             attempt++) {

            try {

                WebElement element =
                        waitForVisible(
                                locator);

                scrollTo(element);

                element.click();

                element.sendKeys(
                        Keys.CONTROL,
                        "a");

                element.sendKeys(
                        Keys.BACK_SPACE);

                element.sendKeys(
                        text);

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        fieldName
                                + " field stale. Retry "
                                + attempt);

                if (attempt == MAX_RETRIES) {

                    throw e;
                }
            }
        }
    }

    // ============================================================
    // SET DATE
    // ============================================================

    private void setDateValue(
            WebElement element,
            String value) {

        js.executeScript(
                """
                const el = arguments[0];
                const value = arguments[1];

                const setter =
                    Object.getOwnPropertyDescriptor(
                        HTMLInputElement.prototype,
                        'value'
                    ).set;

                setter.call(el, value);

                el.dispatchEvent(
                    new Event(
                        'input',
                        { bubbles: true }
                    )
                );

                el.dispatchEvent(
                    new Event(
                        'change',
                        { bubbles: true }
                    )
                );

                el.dispatchEvent(
                    new Event(
                        'blur',
                        { bubbles: true }
                    )
                );
                """,
                element,
                value);
    }

    // ============================================================
    // REMOVE READONLY
    // ============================================================

    private void removeReadonly(
            WebElement element) {

        js.executeScript(
                "arguments[0].removeAttribute('readonly');",
                element);
    }

    // ============================================================
    // WAIT PRESENT
    // ============================================================

    private WebElement waitForPresent(
            By locator) {

        return wait.until(
                ExpectedConditions
                        .presenceOfElementLocated(
                                locator));
    }

    // ============================================================
    // WAIT VISIBLE
    // ============================================================

    private WebElement waitForVisible(
            By locator) {

        return wait.until(
                ExpectedConditions
                        .visibilityOfElementLocated(
                                locator));
    }

    // ============================================================
    // WAIT DISPLAYED
    // ============================================================

    private WebElement waitForDisplayed(
            By locator) {

        return wait.until(driver -> {

            try {

                List<WebElement> elements =
                        driver.findElements(
                                locator);

                for (WebElement element :
                        elements) {

                    try {

                        if (element.isDisplayed()) {

                            return element;
                        }

                    } catch (
                            StaleElementReferenceException ignored) {
                    }
                }

            } catch (Exception ignored) {
            }

            return null;
        });
    }

    // ============================================================
    // SCROLL
    // ============================================================

    private void scrollTo(
            WebElement element) {

        if (element == null) {
            return;
        }

        try {

            js.executeScript(
                    "arguments[0].scrollIntoView({" +
                            "block:'center'," +
                            "inline:'nearest'" +
                            "});",
                    element);

        } catch (Exception ignored) {
        }
    }

    // ============================================================
    // GET TEXT
    // ============================================================

    private String getElementText(
            WebElement element) {

        try {

            String text =
                    element.getText();

            if (text != null
                    && !text.trim().isEmpty()) {

                return text.trim();
            }

        } catch (Exception ignored) {
        }

        try {

            String aria =
                    element.getAttribute(
                            "aria-label");

            if (aria != null
                    && !aria.trim().isEmpty()) {

                return aria.trim();
            }

        } catch (Exception ignored) {
        }

        try {

            String value =
                    element.getAttribute(
                            "value");

            return value == null
                    ? ""
                    : value.trim();

        } catch (Exception e) {

            return "";
        }
    }

    // ============================================================
    // REQUIRED
    // ============================================================

    private String required(
            String value,
            String fieldName) {

        if (value == null
                || value.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    fieldName
                            + " cannot be empty");
        }

        return value.trim();
    }

    // ============================================================
    // XPATH LITERAL
    // ============================================================

    private String xpathLiteral(
            String value) {

        if (value == null) {
            return "''";
        }

        if (!value.contains("'")) {

            return "'"
                    + value
                    + "'";
        }

        if (!value.contains("\"")) {

            return "\""
                    + value
                    + "\"";
        }

        String[] parts =
                value.split(
                        "'",
                        -1);

        StringBuilder result =
                new StringBuilder(
                        "concat(");

        for (int i = 0;
             i < parts.length;
             i++) {

            if (i > 0) {

                result.append(
                        ", \"'\", ");
            }

            result.append("'")
                    .append(parts[i])
                    .append("'");
        }

        result.append(")");

        return result.toString();
    }
}