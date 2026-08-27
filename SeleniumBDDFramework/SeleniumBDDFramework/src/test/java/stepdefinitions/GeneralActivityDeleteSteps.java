package stepdefinitions;

import Utilities.ExcelUtils;
import base.baseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.CRMActivity;
import pages.crm.ActivityListPage;
import pages.crm.general.GeneralActivityDeletePage;

public class GeneralActivityDeleteSteps extends baseClass {

    private ActivityListPage activityListPage;

    @When("User deletes General Activity from Excel row {int}")
    public void userDeletesGeneralActivityFromExcelRow(int row) {
        CRMActivity activity = ExcelUtils.getCRMActivity(row);
        getActivityListPage().selectActivityAction(
            ActivityScenarioContext.getActivityId(), "Delete");

        GeneralActivityDeletePage deletePage = new GeneralActivityDeletePage(driver);
        deletePage.confirmDelete();
    }

    @Then("General Activity should be deleted successfully")
    public void generalActivityShouldBeDeletedSuccessfully() {
        getActivityListPage().waitForActivityMessage("Activity deleted successfully");
    }

    private ActivityListPage getActivityListPage() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized.");
        }

        if (activityListPage == null) {
            activityListPage = new ActivityListPage(driver);
        }

        return activityListPage;
    }
}