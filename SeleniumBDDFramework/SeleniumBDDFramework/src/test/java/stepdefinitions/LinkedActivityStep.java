package stepdefinitions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import base.baseClass;
import model.LinkedCRMActivity;
import pages.LoginPage;
import pages.crm.linked.LinkedActivityCreatePage;
import Utilities.ExcelUtils;

public class LinkedActivityStep extends baseClass {

    private LinkedCRMActivity activity;
    private LinkedActivityCreatePage linkedActivityCreatePage;

    // =========================================================
    // LOGIN
    // =========================================================

    @Given("User logs in for Linked Activity from Excel row {int}")
    public void userLogsInForLinkedActivity(int row) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("LINKED ACTIVITY LOGIN FROM EXCEL");
        System.out.println("Excel Row = " + row);
        System.out.println("==========================================");

        LinkedCRMActivity loginActivity =
                ExcelUtils.getLinkedCRMActivity(row);

        if (loginActivity == null) {
            throw new IllegalStateException(
                    "Linked CRM Activity data is null for Excel row "
                            + row
            );
        }

        String email = loginActivity.getEmail();
        String password = loginActivity.getPassword();

        System.out.println(
                "Excel Email    = " + email
        );

        System.out.println(
                "Excel Password = ********"
        );

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Linked Activity login email is empty for Excel row "
                            + row
            );
        }

        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Linked Activity password is empty for Excel row "
                            + row
            );
        }

        // =========================================================
        // LOGIN
        // =========================================================

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(email, password);

        // =========================================================
        // WAIT FOR CRM APPLICATION
        // =========================================================

        waitForUrl("/qa-crm");

        // =========================================================
        // VERIFY CRM PAGE
        // =========================================================

        String currentUrl = driver.getCurrentUrl();

        if (!currentUrl.contains("/qa-crm")) {

            throw new IllegalStateException(
                    "Linked Activity login failed. " +
                    "Expected CRM URL but current URL is: "
                            + currentUrl
            );
        }

        System.out.println(
                "Linked Activity login successful."
        );

        System.out.println(
                "Logged-in Excel Row = " + row
        );

        System.out.println(
                "Logged-in Email = " + email
        );

        System.out.println(
                "Current URL = " + driver.getCurrentUrl()
        );
    }
    // =========================================================
    // CREATE LINKED ACTIVITY
    // =========================================================

    @When("User creates Linked CRM Activity from Excel row {int}")
    public void userCreatesLinkedCRMActivityFromExcelRow(int rowNumber) {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("CREATE LINKED CRM ACTIVITY");
        System.out.println("Excel Row = " + rowNumber);
        System.out.println("==========================================");

        // =====================================================
        // READ EXCEL DATA
        // =====================================================

        activity = ExcelUtils.getLinkedCRMActivity(rowNumber);

        String email = clean(activity.getEmail());
        String leadName = clean(activity.getLeadName());
        String activityType = clean(activity.getActivityType());
        String purpose = clean(activity.getPurpose());
        String description = clean(activity.getDescription());
        String linkToStage = clean(activity.getLinkToStage());
        String date = clean(activity.getDate());
        String startTime = clean(activity.getStartTime());
        String endTime = clean(activity.getEndTime());
        String assignmentType = clean(activity.getAssignmentType());
        String user = clean(activity.getUser());
        String reason = clean(activity.getReason());

        // =====================================================
        // VALIDATE CRITICAL DATA
        // =====================================================

        validateRequired(
                leadName,
                "Lead Name",
                rowNumber);

        validateRequired(
                activityType,
                "Activity Type",
                rowNumber);

        validateRequired(
                purpose,
                "Purpose",
                rowNumber);

        validateRequired(
                description,
                "Description",
                rowNumber);

        validateRequired(
                linkToStage,
                "Link To Stage",
                rowNumber);

        validateRequired(
                date,
                "Date",
                rowNumber);

        validateRequired(
                startTime,
                "Start Time",
                rowNumber);

        validateRequired(
                endTime,
                "End Time",
                rowNumber);

        validateRequired(
                assignmentType,
                "Assignment Type",
                rowNumber);

        // =====================================================
        // PRINT EXCEL DATA
        // =====================================================

        System.out.println("Email           = " + email);
        System.out.println("Password        = ********");
        System.out.println("Lead Name       = " + leadName);
        System.out.println("Activity Type   = " + activityType);
        System.out.println("Purpose         = " + purpose);
        System.out.println("Description     = " + description);
        System.out.println("Link To Stage   = " + linkToStage);
        System.out.println("Date            = " + date);
        System.out.println("Start Time      = " + startTime);
        System.out.println("End Time        = " + endTime);
        System.out.println("Assignment Type = " + assignmentType);
        System.out.println("User            = " + user);
        System.out.println("Reason          = " + reason);

        System.out.println();
        System.out.println("==========================================");

        // =====================================================
        // CREATE MODEL OBJECT
        // =====================================================

        // =====================================================
        // VERIFY MODEL DATA
        // =====================================================

        System.out.println();
        System.out.println("MODEL DATA AFTER CONSTRUCTION");
        System.out.println("Lead Name = " + activity.getLeadName());
        System.out.println("Purpose   = " + activity.getPurpose());
        System.out.println("Stage     = " + activity.getLinkToStage());

        if (activity.getLeadName() == null ||
                activity.getLeadName().trim().isEmpty()) {

            throw new IllegalStateException(
                    "Lead Name became NULL after LinkedCRMActivity "
                    + "object creation. Excel row = "
                    + rowNumber);
        }

        // =====================================================
        // DRIVER VALIDATION
        // =====================================================

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is NULL before LinkedActivityCreatePage.");
        }

        // =====================================================
        // CREATE PAGE OBJECT
        // =====================================================

        linkedActivityCreatePage =
                new LinkedActivityCreatePage(driver);

        System.out.println();
        System.out.println("LinkedActivityCreatePage initialized successfully.");
        System.out.println("Driver initialized = " + (driver != null));

        // =====================================================
        // CREATE LINKED ACTIVITY
        // =====================================================

        linkedActivityCreatePage.createLinkedActivity(activity);

        System.out.println();
        System.out.println("==========================================");
        System.out.println("LINKED ACTIVITY CREATED SUCCESSFULLY");
        System.out.println("==========================================");
    }

    // =========================================================
    // VERIFY CREATION
    // =========================================================

    @Then("Linked CRM Activity should be created successfully")
    public void linkedCRMActivityShouldBeCreatedSuccessfully() {

        System.out.println();
        System.out.println("==========================================");
        System.out.println("VERIFY LINKED ACTIVITY CREATION");
        System.out.println("==========================================");

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is NULL during Linked Activity verification.");
        }

        String currentUrl = driver.getCurrentUrl();

        System.out.println("Current URL = " + currentUrl);

        assertNotNull(
                "Linked CRM Activity model is null.",
                activity);

        assertNotNull(
                "Lead Name is null after creation.",
                activity.getLeadName());

        assertFalse(
                "Lead Name is empty after creation.",
                activity.getLeadName().trim().isEmpty());

        if (!currentUrl.contains("/qa-crm/activities")) {

            throw new AssertionError(
                    "Linked CRM Activity creation did not navigate "
                    + "to Activities page. Current URL = "
                    + currentUrl);
        }

        System.out.println(
                "Linked CRM Activity created successfully.");
    }

    // =========================================================
    // CLEAN STRING
    // =========================================================

    private String clean(String value) {

        if (value == null) {
            return null;
        }

        value = value.trim();

        if ("null".equalsIgnoreCase(value)) {
            return null;
        }

        return value;
    }

    // =========================================================
    // REQUIRED VALIDATION
    // =========================================================

    private void validateRequired(
            String value,
            String fieldName,
            int rowNumber) {

        if (value == null || value.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Required Linked Activity field is empty. "
                    + "Field = "
                    + fieldName
                    + ", Excel Row = "
                    + rowNumber);
        }
    }
}