package stepdefinitions;

import Utilities.ExcelUtils;
import base.baseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.CRMActivity;
import pages.crm.ActivityListPage;
import pages.crm.general.GeneralActivityViewPage;

public class GeneralActivityViewSteps extends baseClass {

    private ActivityListPage activityListPage;
    private CRMActivity activity;

    @When("User views General Activity from Excel row {int}")
    public void userViewsGeneralActivityFromExcelRow(int row) {
        activity = ExcelUtils.getCRMActivity(row);
        getActivityListPage().selectActivityAction(
                ActivityScenarioContext.getActivityId(), "View");
    }

    @Then("General Activity details should be displayed")
    public void generalActivityDetailsShouldBeDisplayed() {
        GeneralActivityViewPage viewPage = new GeneralActivityViewPage(driver);
        viewPage.verifyActivityDetails(
                activity.getPurpose(), activity.getDescription());
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