package pages.crm.general;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import model.CRMActivity;
import pages.crm.ActivityCommonPage;

public class GeneralActivityCreatePage extends ActivityCommonPage {

    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public GeneralActivityCreatePage(WebDriver driver) {

        super(driver);

        System.out.println("==========================================");
        System.out.println("GeneralActivityCreatePage initialized.");
        System.out.println("Driver initialized = " + (driver != null));
        System.out.println("Wait initialized = " + (wait != null));
        System.out.println("Driver instance = " + driver);
        System.out.println("Wait instance = " + wait);
        System.out.println("==========================================");
    }

    // =========================================================
    // LOCATORS

    private final By generalActivity =
            By.id("activity-create-page-category-radio-general");

    private final By activityType =
            By.id("activity-type-select");

    private final By purpose =
            By.xpath("//input[@placeholder='Enter activity purpose']");

    private final By description =
            By.xpath("//textarea[@placeholder='Enter activity description...']");

    private final By date =
            By.xpath("//input[@placeholder='dd-mm-yyyy' or @type='date' "
                    + "or contains(translate(@id, 'DATE', 'date'), 'date') "
                    + "or contains(translate(@name, 'DATE', 'date'), 'date')]");

    private final By startTime =
            By.xpath("//*[self::button or self::input or @role='button' "
                    + "or @role='combobox'][contains(@placeholder, 'Start Time') "
                    + "or contains(normalize-space(.), 'Select Start Time')]");

    private final By endTime =
            By.xpath("//*[self::button or self::input or @role='button' "
                    + "or @role='combobox'][contains(@placeholder, 'End Time') "
                    + "or contains(normalize-space(.), 'Select End Time')]");

   // private final By createActivityButton =
            //By.xpath("//button[normalize-space()='Create Activity']");


    // =========================================================
    // CREATE GENERAL ACTIVITY
    // =========================================================

    public void createGeneralActivity(CRMActivity activity) {

        if (activity == null) {
            throw new IllegalArgumentException(
                    "CRMActivity cannot be null."
            );
        }

        System.out.println("==========================================");
        System.out.println("CREATING GENERAL ACTIVITY");
        System.out.println("==========================================");

        System.out.println("Activity Type    = " + activity.getActivityType());
        System.out.println("Purpose          = " + activity.getPurpose());
        System.out.println("Description      = " + activity.getDescription());
        System.out.println("Date             = " + activity.getDate());
        System.out.println("Start Time       = " + activity.getStartTime());
        System.out.println("End Time         = " + activity.getEndTime());
        System.out.println("Assignment Type  = " + activity.getAssignmentType());
        System.out.println("User             = " + activity.getUser());
        System.out.println("Reason           = " + activity.getReason());

        // =====================================================
        // 1. ACTIVITY TYPE
        // =====================================================

        selectGeneralActivity();
        selectActivityType(activity.getActivityType());

        // =====================================================
        // 2. PURPOSE
        // =====================================================

        enterPurpose(activity.getPurpose());

        // =====================================================
        // 3. DESCRIPTION
        // =====================================================

        enterDescription(activity.getDescription());

        // =====================================================
        // 4. DATE
        // =====================================================

        enterDate(activity.getDate());

        // =====================================================
        // 5. START TIME
        // =====================================================

        selectStartTime(activity.getStartTime());

        // =====================================================
        // 6. END TIME
        // =====================================================

        selectEndTime(activity.getEndTime());

        // =====================================================
        // 7. ASSIGNMENT TYPE
        // =====================================================

        selectAssignmentType(
                activity.getAssignmentType()
        );

        // =====================================================
        // 8. CONDITIONAL USER / REASON
        // =====================================================

        handleAssignmentDetails(activity);

        // =====================================================
        // 9. CREATE
        // =====================================================

        clickCreateActivity();

        System.out.println("==========================================");
        System.out.println("GENERAL ACTIVITY CREATION COMPLETED");
        System.out.println("==========================================");
    }


    // =========================================================
    // ACTIVITY TYPE
    // =========================================================

        private void selectGeneralActivity() {

                List<WebElement> options =
                                driver.findElements(generalActivity);

                if (options.isEmpty()) {
                        return;
                }

                WebElement option = wait.until(
                                ExpectedConditions.elementToBeClickable(generalActivity));

                if (!option.isSelected()) {
                        option.click();
                }
        }

        protected void selectActivityType(String type) {

        if (isBlank(type)) {
            throw new IllegalArgumentException(
                    "Activity Type is empty in Excel."
            );
        }

        System.out.println(
                "Selecting Activity Type = " + type
        );

        WebElement dropdown = wait.until(
                ExpectedConditions.presenceOfElementLocated(activityType));

        scrollIntoView(dropdown);

        new Select(dropdown).selectByVisibleText(type.trim());

        System.out.println(
                "Activity Type selected successfully."
        );
    }


    // =========================================================
    // PURPOSE
    // =========================================================

        protected void enterPurpose(String value) {

        System.out.println(
                "Entering Purpose = " + value
        );

        WebElement element =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                purpose
                        )
                );

        scrollIntoView(element);
        element.clear();
        element.sendKeys(value);

        System.out.println(
                "Purpose entered successfully."
        );
    }


    // =========================================================
    // DESCRIPTION
    // =========================================================

        protected void enterDescription(String value) {

        System.out.println(
                "Entering Description = " + value
        );

        WebElement element =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                description
                        )
                );

        scrollIntoView(element);
        element.clear();
        element.sendKeys(value);

        System.out.println(
                "Description entered successfully."
        );
    }


    // =========================================================
    // DATE
    // =========================================================

        protected void enterDate(String excelDate) {

        if (isBlank(excelDate)) {
            throw new IllegalArgumentException(
                    "Date is empty in Excel."
            );
        }

        System.out.println(
                "Entering Date = " + excelDate
        );

        String uiDate =
                convertExcelDateToUI(excelDate);

        System.out.println(
                "Converted Date = " + uiDate
        );

        WebElement element =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                date
                        )
                );

        scrollIntoView(element);
        element.click();

        element.sendKeys(Keys.CONTROL, "a");
        element.sendKeys(uiDate);

        element.sendKeys(Keys.TAB);

        System.out.println(
                "Date entered successfully."
        );
    }


    // =========================================================
    // START TIME
    // =========================================================

        protected void selectStartTime(String time) {

        if (isBlank(time)) {
            throw new IllegalArgumentException(
                    "Start Time is empty in Excel."
            );
        }

        System.out.println(
                "Selecting Start Time = " + time
        );

        WebElement element =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                startTime
                        )
                );

        scrollIntoView(element);
        element.click();

        enterTimeInModal(time, "Start Time");

        System.out.println(
                "Start Time selected successfully."
        );
    }


    // =========================================================
    // END TIME
    // =========================================================

        protected void selectEndTime(String time) {

        if (isBlank(time)) {
            throw new IllegalArgumentException(
                    "End Time is empty in Excel."
            );
        }

        System.out.println(
                "Selecting End Time = " + time
        );

        WebElement element =
                wait.until(
                        ExpectedConditions.elementToBeClickable(
                                endTime
                        )
                );

        scrollIntoView(element);
        element.click();

        System.out.println("Entering End Time in modal: " + time);
        enterTimeInModal(time, "End Time");

        System.out.println(
                "End Time selected successfully."
        );
    }


    // =========================================================
    // TIME DROPDOWN
    // =========================================================

    private void enterTimeInModal(String time, String modalTitle) {

        LocalTime parsedTime = LocalTime.parse(
                time.trim().toUpperCase(Locale.ENGLISH),
                DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH));

        String hour = String.valueOf(parsedTime.getHour() % 12 == 0
                ? 12
                : parsedTime.getHour() % 12);

        String minute = String.format(Locale.ENGLISH,
                "%02d", parsedTime.getMinute());

        String period = parsedTime.getHour() < 12 ? "AM" : "PM";

        By modalLocator = By.xpath(
                "//*[normalize-space()='" + modalTitle + "']"
                + "/ancestor::*[.//button[normalize-space()='Confirm']][1]");

        WebElement modal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(modalLocator));

        List<WebElement> inputs = modal.findElements(By.tagName("input"));

        if (inputs.size() < 2) {
            throw new AssertionError(
                    "Unable to find hour and minute inputs in " + modalTitle);
        }

        WebElement hourInput = inputs.get(0);
        WebElement minuteInput = inputs.get(1);

        scrollIntoView(hourInput);
        hourInput.click();
        hourInput.clear();
        hourInput.sendKeys(hour);

        minuteInput.click();
        minuteInput.clear();
        minuteInput.sendKeys(minute);

        List<WebElement> periodSelects = modal.findElements(By.tagName("select"));

        if (!periodSelects.isEmpty()) {
            new Select(periodSelects.get(0)).selectByVisibleText(period);
        } else {
            By periodButton = By.xpath(
                    ".//*[self::button or @role='combobox']"
                    + "[normalize-space()='AM' or normalize-space()='PM']");

            WebElement periodControl = modal.findElement(periodButton);
            periodControl.click();

            By periodOption = By.xpath(
                    "//*[self::button or @role='option' or self::li]"
                    + "[normalize-space()='" + period + "']");

            wait.until(ExpectedConditions.elementToBeClickable(periodOption))
                    .click();
        }

        WebElement confirm = modal.findElement(
                By.xpath(".//button[normalize-space()='Confirm']"));

        scrollIntoView(confirm);
        confirm.click();

        wait.until(ExpectedConditions.invisibilityOf(modal));
    }


    // =========================================================
    // ASSIGNMENT TYPE
    // =========================================================

        protected void selectAssignmentType(String assignmentType) {

        if (isBlank(assignmentType)) {
            throw new IllegalArgumentException(
                    "Assignment Type is empty in Excel."
            );
        }

        System.out.println(
                "Selecting Assignment Type = "
                        + assignmentType
        );

        By assignmentOption = By.xpath(
                "//*[self::label or self::div or @role='radio']"
                + "[translate(normalize-space(.),"
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz')='"
                + escapeXPath(assignmentType.toLowerCase(Locale.ENGLISH))
                + "']");

        WebElement option = wait.until(driver -> driver
                .findElements(assignmentOption)
                .stream()
                .filter(WebElement::isDisplayed)
                .findFirst()
                .orElse(null));

        scrollIntoView(option);
                try {
                        option.click();
                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                        ((org.openqa.selenium.JavascriptExecutor) driver)
                                        .executeScript("arguments[0].click();", option);
                }

        System.out.println(
                "Assignment Type selected successfully."
        );
    }

    // =========================================================
    // USER / REASON
    // =========================================================

    protected void handleAssignmentDetails(
            CRMActivity activity) {

        handleAssignmentDetails(
                activity.getAssignmentType(),
                activity.getUser(),
                activity.getReason());
    }

    protected void handleAssignmentDetails(
            String assignment,
            String user,
            String reason) {

        if (isBlank(assignment)) {
            return;
        }

        // -----------------------------------------------------
        // TEAMMATE
        // -----------------------------------------------------

        if (assignment.equalsIgnoreCase(
                "I want to assign this activity to my teammate")) {

            System.out.println(
                    "Assignment = Teammate"
            );

            if (!isBlank(user)) {

                selectUser(user);
            }

            if (!isBlank(reason)) {

                enterReason(reason);
            }

            return;
        }

        // -----------------------------------------------------
        // TAG USERS
        // -----------------------------------------------------

        if (assignment.equalsIgnoreCase(
                "I want to tag users for collaboration")) {

            System.out.println(
                    "Assignment = Tag Users"
            );

            if (!isBlank(user)) {

                selectUser(user);
            }

            return;
        }

        // -----------------------------------------------------
        // SELF
        // -----------------------------------------------------

        if (assignment.equalsIgnoreCase(
                "I am doing this activity myself")) {

            System.out.println(
                    "Assignment = Myself"
            );

            return;
        }

        System.out.println(
                "No additional assignment fields required."
        );
    }


    // =========================================================
    // USER SELECTION
    // =========================================================

        protected void selectUser(String user) {

        System.out.println(
                "Selecting User = " + user
        );

        By userInput =
                By.xpath(
                        "//*[starts-with(normalize-space(.), 'Users')]"
                        + "/following::input[@type='text' or not(@type)][1]"
                        + " | //input[@type='text' or not(@type)]"
                        + "[contains(translate(@placeholder,"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'user')"
                        + " or contains(translate(@name,"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'user')"
                        + " or contains(translate(@id,"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'), 'user')]"
                );

        try {

            WebElement input = wait.until(
                    ExpectedConditions.elementToBeClickable(userInput));

            scrollIntoView(input);
            input.click();
            input.sendKeys(org.openqa.selenium.Keys.CONTROL, "a");
            input.sendKeys(org.openqa.selenium.Keys.BACK_SPACE);

                        String searchText = user.trim();
                        input.sendKeys(searchText);

                        wait.until(webDriver -> {
                                WebElement currentInput = webDriver.findElement(userInput);
                                String currentValue = currentInput.getAttribute("value");
                                return currentValue != null
                                                && normalizeText(currentValue).equals(normalizeText(searchText));
                        });

            String normalizedUser = user.trim().toLowerCase(Locale.ROOT);
            String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
            String lowerCase = "abcdefghijklmnopqrstuvwxyz";
            By exactUserOption = By.xpath(
                    "//*[self::li or self::div or self::span or self::p or self::button or @role='option']"
                    + "[contains(translate(normalize-space(.), '" + upperCase + "', '"
                    + lowerCase + "'), '" + escapeXPath(normalizedUser) + "')"
                    + " or contains(translate(@title, '" + upperCase + "', '" + lowerCase
                    + "'), '" + escapeXPath(normalizedUser) + "')"
                    + " or contains(translate(@aria-label, '" + upperCase + "', '" + lowerCase
                    + "'), '" + escapeXPath(normalizedUser) + "')]" );

                        WebElement userOption = null;
                        try {
                                userOption = wait.until(driver -> {
                                        List<WebElement> matches = driver.findElements(exactUserOption);
                                        WebElement bestMatch = null;

                                        for (int index = matches.size() - 1; index >= 0; index--) {
                                                try {
                                                        WebElement candidate = matches.get(index);
                                                        if (!candidate.isDisplayed()) {
                                                                continue;
                                                        }

                                                        String candidateText = normalizeText(candidate.getText());
                                                        String candidateTitle = normalizeText(candidate.getAttribute("title"));
                                                        String candidateLabel = normalizeText(candidate.getAttribute("aria-label"));
                                                        if (normalizedUser.equals(candidateText)
                                                                        || normalizedUser.equals(candidateTitle)
                                                                        || normalizedUser.equals(candidateLabel)) {
                                                                return candidate;
                                                        }

                                                        if (bestMatch == null && ((candidateText != null && candidateText.contains(normalizedUser))
                                                                        || (candidateTitle != null && candidateTitle.contains(normalizedUser))
                                                                        || (candidateLabel != null && candidateLabel.contains(normalizedUser)))) {
                                                                bestMatch = candidate;
                                                        }
                                                } catch (org.openqa.selenium.StaleElementReferenceException e) {
                                                        // Retry while the result list is being refreshed.
                                                }
                                        }

                                        return bestMatch;
                                });
                        } catch (TimeoutException e) {
                                WebElement currentInput = wait.until(
                                                ExpectedConditions.elementToBeClickable(userInput));
                                currentInput.sendKeys(Keys.ARROW_DOWN);
                                currentInput.sendKeys(Keys.ENTER);
                        }

                        if (userOption != null) {
                                scrollIntoView(userOption);
                                try {
                                        userOption.click();
                                } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                                        ((org.openqa.selenium.JavascriptExecutor) driver)
                                                        .executeScript("arguments[0].click();", userOption);
                                }
            }

                        wait.until(driver -> {
                                try {
                                        String value = driver.findElement(userInput)
                                                        .getAttribute("value");
                                        if (value != null && normalizeText(value).contains(normalizeText(user))) {
                                                return true;
                                        }

                                        return driver.findElements(By.xpath(
                                                        "//*[self::span or self::div or self::button]"
                                                                        + "[contains(normalize-space(.), '"
                                                                        + escapeXPath(user.trim()) + "')]"))
                                                        .stream()
                                                        .anyMatch(WebElement::isDisplayed);
                                } catch (Exception e) {
                                        return false;
                                }
                        });

            System.out.println(
                    "User selected successfully."
            );

        } catch (TimeoutException e) {

            throw new AssertionError(
                    "Unable to select user: " + user,
                    e
            );
        }
    }


    // =========================================================
    // REASON
    // =========================================================

        private String normalizeText(String value) {
                return value == null
                                ? ""
                                : value.trim().toLowerCase(Locale.ROOT);
        }


		protected void enterReason(String reason) {

        System.out.println(
                "Entering Reason = " + reason
        );

        By reasonField =
                By.xpath(
                        "//textarea[contains("
                        + "translate(@placeholder,"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                        + "'abcdefghijklmnopqrstuvwxyz'),"
                        + "'reason')]"
                        + " | //*[@id='assignment-reason' or @name='assignment-reason']"
                        + " | "
                        + "//input[contains("
                        + "translate(@placeholder,"
                        + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                        + "'abcdefghijklmnopqrstuvwxyz'),"
                        + "'reason')]"
                );

        WebElement element =
                wait.until(
                        ExpectedConditions.visibilityOfElementLocated(
                                reasonField
                        )
                );

        scrollIntoView(element);
        element.clear();
        element.sendKeys(reason);

        System.out.println(
                "Reason entered successfully."
        );
    }
 // =========================================================
 // LOCATION
 // =========================================================

 private void enterLocation(String location) {

     if (isBlank(location)) {

         System.out.println(
                 "Location is blank. Skipping location."
         );

         return;
     }

     System.out.println(
             "Entering Location = " + location
     );

     By locationField = By.xpath(
             "//input[contains(" +
             "translate(@placeholder," +
             "'ABCDEFGHIJKLMNOPQRSTUVWXYZ'," +
             "'abcdefghijklmnopqrstuvwxyz')," +
             "'location')]"
     );

     WebElement element =
             wait.until(
                     ExpectedConditions.visibilityOfElementLocated(
                             locationField
                     )
             );

     element.click();

     element.clear();

     element.sendKeys(location);

     System.out.println(
             "Location entered successfully."
     );
 }

    // =========================================================
    // CREATE BUTTON
    // =========================================================

  /*  public void clickCreateActivity() {

        System.out.println(
                "Waiting for Create Activity button..."
        );

        WebElement button = wait.until(driver -> {
            WebElement candidate = driver.findElement(createActivityButton);
            return candidate.isDisplayed()
                    && candidate.isEnabled()
                    && !"true".equals(candidate.getAttribute("aria-disabled"))
                    ? candidate
                    : null;
        });

        scrollIntoView(button);
        wait.until(ExpectedConditions.elementToBeClickable(button));

        try {
            button.click();
        } catch (org.openqa.selenium.ElementClickInterceptedException e) {
            ((org.openqa.selenium.JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", button);
        }

        System.out.println(
                "Create Activity button clicked."
        );

        wait.until(
                ExpectedConditions.urlContains(
                        "/qa-crm/activities"
                )
        );

        System.out.println(
                "Activity creation completed."
        );
    }
*/
 // =========================================================
 // ASSIGNMENT TYPE - ASSIGN TO TEAMMATE
 // =========================================================

 private By assignToTeammateOption = By.xpath(
     "//label[contains(normalize-space(.),'I want to assign this activity to my teammate')]"
 );

 // Users field
 private By usersField = By.xpath(
     "//div[@id='assignTo-teammate-section']//input"
 );

 // Reason for Assignment
 private By assignmentReason = By.id("assignment-reason");

 // Create Activity button
 private By createActivityButton = By.id("btn-submit");


 // =========================================================
 // SELECT ASSIGN TO TEAMMATE
 // =========================================================

 public void selectAssignToTeammate() {

     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

     WebElement option = wait.until(
         ExpectedConditions.elementToBeClickable(assignToTeammateOption)
     );

     ((JavascriptExecutor) driver).executeScript(
         "arguments[0].click();", option
     );

     System.out.println("Assignment Type selected: Assign to teammate");
 }


 // =========================================================
 // ENTER ASSIGNMENT REASON
 // =========================================================

 public void enterAssignmentReason(String reason) {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	    WebElement reasonField = wait.until(
	        ExpectedConditions.visibilityOfElementLocated(
	            By.id("assignment-reason")
	        )
	    );

	    reasonField.click();
	    reasonField.clear();
	    reasonField.sendKeys(reason);

	    System.out.println("Reason for Assignment entered: " + reason);
	}
 @Override
 public void clickCreateActivity() {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            WebElement createButton = wait.until(webDriver -> {
                for (WebElement candidate : webDriver.findElements(By.id("btn-submit"))) {
                    try {
                        String disabled = candidate.getAttribute("disabled");
                        String ariaDisabled = candidate.getAttribute("aria-disabled");
                        if (candidate.isDisplayed() && candidate.isEnabled()
                                && disabled == null && !"true".equalsIgnoreCase(ariaDisabled)) {
                            return candidate;
                        }
                    } catch (org.openqa.selenium.StaleElementReferenceException ignored) {
                        // Retry until the form finishes updating.
                    }
                }
        return null;
            });

	    ((JavascriptExecutor) driver).executeScript(
	        "arguments[0].scrollIntoView({block:'center'});",
	        createButton
	    );

            try {
                createButton.click();
            } catch (org.openqa.selenium.ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", createButton);
            }

	    System.out.println("Create Activity button clicked");

            wait.until(webDriver -> {
                String currentUrl = webDriver.getCurrentUrl();
                return currentUrl.contains("/qa-crm/activities")
                        && !currentUrl.contains("/qa-crm/activities/create");
            });
	}

 // =========================================================
 // VERIFY TEAMMATE SECTION
 // =========================================================

 public boolean isTeammateSectionDisplayed() {

     WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

     try {
         wait.until(
             ExpectedConditions.visibilityOfElementLocated(
                 By.id("assignTo-teammate-section")
             )
         );

         return true;

     } catch (TimeoutException e) {
         return false;
     }
 }
    // =========================================================
    // VERIFY CREATION
    // =========================================================

    public void verifyActivityCreatedSuccessfully() {

        System.out.println(
                "=========================================="
        );

        System.out.println(
                "VERIFY GENERAL ACTIVITY CREATION"
        );

        System.out.println(
                "=========================================="
        );

        wait.until(
                ExpectedConditions.urlContains(
                        "/qa-crm/activities"
                )
        );

        String currentUrl =
                driver.getCurrentUrl();

        System.out.println(
                "Current URL = " + currentUrl
        );

        if (!currentUrl.contains(
                "/qa-crm/activities")) {

            throw new AssertionError(
                    "General Activity creation failed. "
                    + "Current URL = "
                    + currentUrl
            );
        }

        System.out.println(
                "General Activity created successfully."
        );
    }


    // =========================================================
    // DATE CONVERSION
    // =========================================================

    private String convertExcelDateToUI(
            String excelDate) {

        try {

            DateTimeFormatter inputFormatter =
                    DateTimeFormatter.ofPattern(
                            "M/d/yy",
                            Locale.ENGLISH
                    );

            DateTimeFormatter outputFormatter =
                    DateTimeFormatter.ofPattern(
                            "dd-MM-yyyy"
                    );

            LocalDate date =
                    LocalDate.parse(
                            excelDate.trim(),
                            inputFormatter
                    );

            return date.format(outputFormatter);

        } catch (Exception e) {

            throw new IllegalArgumentException(
                    "Invalid Excel date: "
                            + excelDate
                            + ". Expected format M/d/yy.",
                    e
            );
        }
    }


    // =========================================================
    // UTILITIES
    // =========================================================

    private boolean isBlank(String value) {

        return value == null
                || value.trim().isEmpty();
    }


    private String escapeXPath(String value) {

        if (!value.contains("'")) {
            return value;
        }

        return value.replace(
                "'",
                "&apos;"
        );
    }
}