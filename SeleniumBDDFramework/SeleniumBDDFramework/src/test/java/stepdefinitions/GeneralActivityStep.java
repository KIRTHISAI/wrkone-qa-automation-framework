package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import model.CRMActivity;
import pages.crm.ActivityListPage;
import pages.crm.general.GeneralActivityCreatePage;
import Utilities.ExcelUtils;

public class GeneralActivityStep extends baseClass {

    private GeneralActivityCreatePage generalActivityPage;
    private CRMActivity activity;

    // =========================================================
    // GET PAGE
    // =========================================================

    private GeneralActivityCreatePage getGeneralActivityPage() {

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is NULL. Browser was not initialized."
            );
        }

        if (generalActivityPage == null) {
            generalActivityPage =
                    new GeneralActivityCreatePage(driver);
        }

        return generalActivityPage;
    }

    // =========================================================
    // CREATE GENERAL ACTIVITY FROM EXCEL
    // =========================================================

        @When("User creates General Activity from Excel row {int}")
    public void userCreatesGeneralActivityFromExcelRow(int row) {

        System.out.println("==========================================");
        System.out.println(
                "READING GENERAL ACTIVITY FROM EXCEL ROW = " + row
        );
        System.out.println("==========================================");

        activity = ExcelUtils.getCRMActivity(row);

        if (activity == null) {
            throw new IllegalStateException(
                    "CRMActivity returned NULL for Excel row " + row
            );
        }

        System.out.println("Excel data loaded successfully.");

        System.out.println(
                "Activity Type   = " + activity.getActivityType()
        );
        System.out.println(
                "Purpose         = " + activity.getPurpose()
        );
        System.out.println(
                "Description     = " + activity.getDescription()
        );
        System.out.println(
                "Date            = " + activity.getDate()
        );
        System.out.println(
                "Start Time      = " + activity.getStartTime()
        );
        System.out.println(
                "End Time        = " + activity.getEndTime()
        );
        System.out.println(
                "Assignment Type = " + activity.getAssignmentType()
        );
        System.out.println(
                "User            = " + activity.getUser()
        );
        System.out.println(
                "Reason          = " + activity.getReason()
        );

        // =====================================================
        // CALL PAGE OBJECT
        // =====================================================

        getGeneralActivityPage()
                .createGeneralActivity(activity);

        ActivityListPage activityListPage = new ActivityListPage(driver);
        ActivityScenarioContext.setActivityId(
                activityListPage.captureNewestActivityId());

        System.out.println(
                "General Activity creation flow completed."
        );
    }
    @Then("General Activity should be created successfully")
    public void generalActivityShouldBeCreatedSuccessfully() {

        getGeneralActivityPage()
                .verifyActivityCreatedSuccessfully();
    }
}