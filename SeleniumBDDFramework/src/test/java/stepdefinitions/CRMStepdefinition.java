package stepdefinitions;

import java.time.Duration;
import model.CRMActivity;
import Utilities.ExtentManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Utilities.ExcelUtils;
import base.baseClass;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import model.LinkedCRMActivity;

import pages.CRMPage;
import pages.LoginPage;

public class CRMStepdefinition extends baseClass {

    private CRMPage crmPage;
    private LoginPage loginPage;

    // ============================================================
    // GET CRM PAGE
    // ============================================================

    private CRMPage getCRMPage() {

        if (crmPage == null) {
            crmPage = new CRMPage(driver);
        }

        return crmPage;
    }

    // ============================================================
    // GET LOGIN PAGE
    // ============================================================

    private LoginPage getLoginPage() {

        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }

        return loginPage;
    }
    private static final ThreadLocal<String> ACTIVITY_REPORT_NAME =
            new ThreadLocal<>();

    public static String getActivityReportName() {
        return ACTIVITY_REPORT_NAME.get();
    }

    public static void clearActivityReportName() {
        ACTIVITY_REPORT_NAME.remove();
    }
    // ============================================================
    // CRM LOGIN
    // ============================================================

    @When("User logs in for CRM Activity")
    public void userLogsInForCRMActivity() {

        System.out.println();
        System.out.println("======================================");
        System.out.println("Starting CRM Activity Login");
        System.out.println("======================================");

        launchBrowser1();

        String username = getConfigValue(
                "crm.email",
                "login.email"
        );

        String password = getConfigValue(
                "crm.activity.password",
                "login.password"
        );

        if (username == null || username.isBlank()) {
            throw new IllegalStateException(
                    "CRM username is missing in config.properties."
            );
        }

        if (password == null || password.isBlank()) {
            throw new IllegalStateException(
                    "CRM password is missing in config.properties."
            );
        }

        System.out.println("CRM username loaded successfully.");

        getLoginPage().login(username, password);

        System.out.println("CRM Activity login completed.");
    }

    // ============================================================
    // APPLICATIONS
    // ============================================================

    @When("User clicks Applications")
    public void userClicksApplications() {

        getCRMPage().clickApplications();
    }

    // ============================================================
    // CRM
    // ============================================================

    @When("User clicks CRM")
    public void userClicksCRM() {

        getCRMPage().clickCRM();
    }

    // ============================================================
    // VERIFY CRM PAGE
    // ============================================================

    @Then("CRM page should be displayed")
    public void crmPageShouldBeDisplayed() {

        getCRMPage().verifyCRMPageDisplayed();

        System.out.println(
                "CRM page displayed successfully."
        );
    }

    // ============================================================
    // ACTIVITIES
    // ============================================================

    @When("User clicks Activities")
    public void userClicksActivities() {

        getCRMPage().clickActivities();
    }

    // ============================================================
    // ALL ACTIVITIES
    // ============================================================

    @When("User clicks All Activities")
    public void userClicksAllActivities() {

        getCRMPage().clickAllActivities();
    }

    // ============================================================
    // CREATE ACTIVITY MENU
    // ============================================================

    @When("User clicks Create Activity Menu")
    public void userClicksCreateActivityMenu() {

        getCRMPage().clickCreateActivityMenu();
    }

    // ============================================================
    // CRM ACTIVITY FROM EXCEL
    // ============================================================

    @When("User creates CRM Activity from Excel row {int}")
    public void userCreatesCRMActivityFromExcel(
            int rowNumber) {

        System.out.println(
                "======================================"
        );

        System.out.println(
                "Reading CRM Activity Excel Row = "
                        + rowNumber
        );

        var activity =
                ExcelUtils.getCRMActivity(rowNumber);

        if (activity == null) {

            throw new RuntimeException(
                    "CRM Activity data is null for Excel row "
                            + rowNumber
            );
        }

        // ========================================================
        // GET ACTIVITY DETAILS
        // ========================================================

        String activityType =
                activity.getActivityType();

        String AssignmentType =
                activity.getAssignmentType();

        if (activityType == null) {
            activityType = "";
        }

        if (AssignmentType == null) {
        	AssignmentType = "";
        }

        activityType = activityType.trim();
        AssignmentType = AssignmentType.trim();

        // ========================================================
        // CREATE REPORT TEST
        // ========================================================

        String reportName =
                "Create CRM Activity"
                + " - "
                + activityType
                + " - "
                + AssignmentType;

        ExtentManager.createTest(
                reportName
        );

        ExtentManager.info(
                "Excel Row: "
                        + rowNumber
        );

        ExtentManager.info(
                "Activity Type: "
                        + activityType
        );

        ExtentManager.info(
                "Assignment Type: " + activity.getAssignmentType()
        );
      
        System.out.println(
                "Report Name = "
                        + reportName
        );

        // ========================================================
        // CREATE CRM ACTIVITY
        // ========================================================

        getCRMPage().createCRMActivity(
                activity
        );

        // ========================================================
        // REPORT LOG
        // ========================================================

        ExtentManager.info(
                "CRM Activity creation completed."
        );

        System.out.println(
                "CRM Activity creation completed."
        );

        System.out.println(
                "======================================"
        );
    }

    // ============================================================
    // LINKED CRM LOGIN
    // ============================================================

    private String safe(String AssignmentType) {
	// TODO Auto-generated method stub
	return null;
}

	@When("User logs in for Linked Activity")
    public void userLogsInForLinkedActivity() {

        System.out.println();
       launchBrowser1();

        String username = getConfigValue(
                "linked.crm.username"
        );

        String password = getConfigValue(
                "linked.crm.password"
        );
        getLoginPage().login(username, password);

        System.out.println(
                "Linked Activity login completed."
        );
    }

    // ============================================================
    // LINKED CRM ACTIVITY FROM EXCEL
    // ============================================================

	@When("User creates Linked CRM Activity from Excel row {int}")
	public void userCreatesLinkedCRMActivityFromExcel(
	        int rowNumber) {

	    System.out.println(
	            "======================================"
	    );

	    System.out.println(
	            "Reading Linked CRM Activity Excel Row = "
	                    + rowNumber
	    );

	    LinkedCRMActivity activity =
	            ExcelUtils.getLinkedCRMActivity(
	                    rowNumber
	            );

	    if (activity == null) {

	        throw new RuntimeException(
	                "Linked CRM Activity data is null for Excel row "
	                        + rowNumber
	        );
	    }

	    // ========================================================
	    // GET ACTIVITY DETAILS
	    // ========================================================

	    String activityType =
	            activity.getActivityType();

	    String purpose =
	            activity.getPurpose();

	    if (activityType == null) {
	        activityType = "";
	    }

	    if (purpose == null) {
	        purpose = "";
	    }

	    activityType = activityType.trim();
	    purpose = purpose.trim();

	    // ========================================================
	    // CREATE REPORT TEST
	    // ========================================================

	    String reportName =
	            "Create Linked CRM Activity"
	            + " - "
	            + activityType
	            + " - "
	            + purpose;

	    ExtentManager.createTest(
	            reportName
	    );

	    ExtentManager.info(
	            "Excel Row: "
	                    + rowNumber
	    );

	    ExtentManager.info(
	            "Activity Type: "
	                    + activityType
	    );

	    ExtentManager.info(
	            "Purpose: "
	                    + purpose
	    );

	    System.out.println(
	            "Report Name = "
	                    + reportName
	    );

	    // ========================================================
	    // CREATE LINKED ACTIVITY
	    // ========================================================

	    getCRMPage().createLinkedActivity(
	            activity
	    );

	    // ========================================================
	    // REPORT LOG
	    // ========================================================

	    ExtentManager.info(
	            "Linked CRM Activity creation completed."
	    );

	    System.out.println(
	            "Linked CRM Activity creation completed."
	    );

	    System.out.println(
	            "======================================"
	    );
	}

    // ============================================================
    // VERIFY NORMAL CRM ACTIVITY
    // ============================================================

	@Then("Activity should be created successfully")
	public void activityShouldBeCreatedSuccessfully() {

	    getCRMPage().verifyActivityCreatedSuccessfully();

	    ExtentManager.pass(
	            "Activity creation success message displayed."
	    );

	    System.out.println(
	            "Activity creation success message displayed."
	    );
	}

    // ============================================================
    // VERIFY LINKED CRM ACTIVITY
    // ============================================================
	@Then("Linked CRM Activity should be created successfully")
	public void linkedCRMActivityShouldBeCreatedSuccessfully() {

	    getCRMPage().verifyActivityCreatedSuccessfully();

	    ExtentManager.pass(
	            "Linked CRM Activity creation success message displayed."
	    );

	    System.out.println(
	            "Linked CRM Activity creation success message displayed."
	    );
	}
    // ============================================================
    // OPTIONAL OK POPUP HANDLER
    // ============================================================

    private void clickOkPopupIfPresent() {

        try {

            WebDriverWait shortWait =
                    new WebDriverWait(
                            driver,
                            Duration.ofSeconds(3)
                    );

            Alert alert =
                    shortWait.until(
                            ExpectedConditions.alertIsPresent()
                    );

            System.out.println(
                    "Popup detected: " + alert.getText()
            );

            alert.accept();

            System.out.println(
                    "Popup OK clicked."
            );

        } catch (TimeoutException e) {

            System.out.println(
                    "No popup appeared."
            );

        } catch (Exception e) {

            System.out.println(
                    "Unable to handle popup: "
                            + e.getMessage()
            );
        }
    }
}