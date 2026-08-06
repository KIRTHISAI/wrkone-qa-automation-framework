package stepdefinitions;

import Utilities.ExcelUtils;
import base.baseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.CRMActivity;
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
    // Create Activity From Excel
    // ===========================
    @When("User creates CRM Activity from Excel row {int}")
    public void userCreatesCRMActivity(int row) {

        CRMActivity activity = ExcelUtils.getCRMActivity(row);

        getCRMPage().createActivity(activity);
    }
    // ===========================
    // Verification
    // ===========================

    @Then("Activity should be created successfully")
    public void activityShouldBeCreatedSuccessfully() {
        getCRMPage().verifyActivityCreated();
    }
}