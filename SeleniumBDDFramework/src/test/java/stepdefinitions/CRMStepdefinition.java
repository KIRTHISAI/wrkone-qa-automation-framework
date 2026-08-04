package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.CRMPage;

public class CRMStepdefinition extends baseClass {

    private CRMPage crmPage;

    private CRMPage getCRMPage() {
        if (crmPage == null) {
            crmPage = new CRMPage(driver);
        }
        return crmPage;
    }

    // ===========================
    // Navigation
    // ===========================

    @When("User clicks Applications")
    public void userClicksApplications() {
        getCRMPage().clickApplications();
    }

    @When("User clicks CRM")
    public void userClicksCRM() {
        getCRMPage().clickCRM();
    }

    @Then("CRM page should be displayed")
    public void crmPageShouldBeDisplayed() {
        getCRMPage().verifyCRMPage();
    }

    @When("User clicks Activities")
    public void userClicksActivities() {
        getCRMPage().clickActivities();
    }

    @When("User clicks All Activities")
    public void userClicksAllActivities() {
        getCRMPage().clickAllActivities();
    }

    @When("User clicks Create Activity Menu")
    public void userClicksCreateActivityMenu() {
        getCRMPage().clickCreateActivityMenu();
    }

    // ===========================
    // Activity Details
    // ===========================

    @When("User selects activity type {string}")
    public void userSelectsActivityType(String activityType) {
        getCRMPage().selectActivityType(activityType);
    }

    @When("User enters Purpose {string}")
    public void userEntersPurpose(String purpose) {
        getCRMPage().enterPurpose(purpose);
    }

    @When("User enters Description {string}")
    public void userEntersDescription(String description) {
        getCRMPage().enterDescription(description);
    }

    // ===========================
    // Schedule
    // ===========================

    @When("User selects date after 10 days")
    public void userSelectsDateAfter10Days() {
        getCRMPage().selectDate(10);
    }

    @When("User selects Start Time")
    public void userSelectsStartTime() {
        getCRMPage().selectStartTime("quick-time-10_00_AM");
    }

    @When("User selects End Time")
    public void userSelectsEndTime() {
        getCRMPage().selectEndTime("quick-time-11_00_AM");
    }

    // ===========================
    // Assignment
    // ===========================

    @And("User selects Assignment Type")
    public void userSelectsAssignmentType() {
        getCRMPage().selectAssignmentType();
    }

    @And("User clicks Create Activity Button")
    public void userClicksCreateActivityButton() {
        getCRMPage().clickCreateActivityButton();
    }

    @Then("Activity should be created successfully")
    public void activityShouldBeCreatedSuccessfully() {
        getCRMPage().verifyActivityCreated();
    }
}