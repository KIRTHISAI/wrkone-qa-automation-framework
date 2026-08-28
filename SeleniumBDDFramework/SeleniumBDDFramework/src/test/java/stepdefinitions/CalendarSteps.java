package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.CRMActivity;
import model.LinkedCRMActivity;
import pages.crm.CalendarPage;
import Utilities.ExcelUtils;

public class CalendarSteps extends baseClass {

    private CalendarPage calendarPage;

    private CalendarPage getCalendarPage() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized.");
        }

        if (calendarPage == null) {
            calendarPage = new CalendarPage(driver);
        }

        return calendarPage;
    }

    @When("User opens Calendar")
    public void userOpensCalendar() {
        getCalendarPage().open();
    }

    @When("User clicks New Activity from Calendar")
    public void userClicksNewActivityFromCalendar() {
        getCalendarPage().clickNewActivity();
    }

        @When("User edits Calendar activity from Excel row {int}")
        public void userEditsCalendarActivityFromExcelRow(int row) {
        CRMActivity activity = ExcelUtils.getCRMActivity(row);

        if (activity.getEditPurpose().isBlank()
            || activity.getEditDescription().isBlank()) {
            throw new IllegalArgumentException(
                "Edit Purpose and Edit Description are required for Excel row " + row);
        }

        getCalendarPage().editActivity(activity.getPurpose(), activity.getDescription(),
            activity.getDate(),
            activity.getEditPurpose(), activity.getEditDescription());
    }

    @Then("Calendar activity should be updated successfully")
    public void calendarActivityShouldBeUpdatedSuccessfully() {
        getCalendarPage().verifyActivityUpdatedSuccessfully();
    }

    @When("User edits Linked Calendar activity from Excel row {int}")
    public void userEditsLinkedCalendarActivityFromExcelRow(int row) {
        LinkedCRMActivity activity = ExcelUtils.getLinkedCRMActivity(row);

        if (activity.getEditDescription() == null
                || activity.getEditDescription().isBlank()) {
            throw new IllegalArgumentException(
                    "Edit Description is required for Linked Activity row " + row);
        }

        getCalendarPage().editLinkedActivity(activity.getPurpose(), activity.getDescription(),
            activity.getDate(),
                activity.getEditDescription());
    }

    @Then("Linked Calendar activity should be updated successfully")
    public void linkedCalendarActivityShouldBeUpdatedSuccessfully() {
        getCalendarPage().verifyActivityUpdatedSuccessfully();
    }
}