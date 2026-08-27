package stepdefinitions;

import Utilities.ExcelUtils;
import base.baseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.CRMActivity;
import pages.crm.ActivityListPage;
import pages.crm.general.GeneralActivityEditPage;

public class GeneralActivityEditSteps extends baseClass {

    private ActivityListPage activityListPage;

    @When("User edits General Activity from Excel row {int}")
    public void userEditsGeneralActivityFromExcelRow(int row) {
        CRMActivity activity = ExcelUtils.getCRMActivity(row);

        if (activity.getEditPurpose().isBlank()
                || activity.getEditDescription().isBlank()) {
            throw new IllegalArgumentException(
                    "Edit Purpose and Edit Description are required for Excel row " + row);
        }

        getActivityListPage().selectActivityAction(
            ActivityScenarioContext.getActivityId(), "Edit");

        GeneralActivityEditPage editPage = new GeneralActivityEditPage(driver);
        editPage.updatePurpose(activity.getEditPurpose());
        editPage.updateDescription(activity.getEditDescription());
        editPage.saveChanges();
    }

    @Then("General Activity should be updated successfully")
    public void generalActivityShouldBeUpdatedSuccessfully() {
        getActivityListPage().waitForActivityMessage("Activity updated successfully");
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