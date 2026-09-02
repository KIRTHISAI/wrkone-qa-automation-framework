package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.crm.ActivityFilterPage;

public class ActivityFilterSteps extends baseClass {

    private ActivityFilterPage filterPage;
    private String selectedActivityType;
    private String selectedStatus;

    // =========================================================
    // GET PAGE
    // =========================================================

    private ActivityFilterPage getFilterPage() {

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is NULL. Browser was not initialized."
            );
        }

        if (filterPage == null) {
            filterPage = new ActivityFilterPage(driver);
        }

        return filterPage;
    }

    // =========================================================
    // CLICK FILTERS
    // =========================================================

    @When("User clicks on Filters")
    public void userClicksOnFilters() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks on Filters");
        System.out.println("==========================================");

        getFilterPage().clickFilters();

        System.out.println("Filters panel opened.");
    }

    // =========================================================
    // SELECT ACTIVITY TYPE FILTER
    // =========================================================

    @When("User selects Activity Type filter {string}")
    public void userSelectsActivityTypeFilter(String activityType) {

        System.out.println("==========================================");
        System.out.println(
                "STEP: User selects Activity Type filter: "
                + activityType
        );
        System.out.println("==========================================");

        getFilterPage().selectActivityTypeFilter(activityType);
        selectedActivityType = activityType;

        System.out.println(
                "Activity Type filter '" + activityType + "' selected."
        );
    }

    // =========================================================
    // APPLY ACTIVITY TYPE FILTER
    // =========================================================

    @When("User clicks Apply button for Activity Type filter")
    public void userClicksApplyButtonForActivityTypeFilter() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks Apply button");
        System.out.println("==========================================");

        if (selectedActivityType == null || selectedActivityType.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Activity Type must be selected before clicking Apply."
            );
        }

        getFilterPage().applyActivityTypeFilter(selectedActivityType);

        System.out.println(
                "Activity Type filter '" + selectedActivityType + "' applied."
        );
    }

    // =========================================================
    // SELECT STATUS FILTER
    // =========================================================

    @When("User selects Status filter {string}")
    public void userSelectsStatusFilter(String status) {

        System.out.println("==========================================");
        System.out.println("STEP: User selects Status filter: " + status);
        System.out.println("==========================================");

        getFilterPage().selectStatusFilter(status);
        selectedStatus = status;

        System.out.println(
                "Status filter '" + status + "' selected."
        );
    }

    // =========================================================
    // APPLY STATUS FILTER
    // =========================================================

    @When("User clicks Apply button for Status filter")
    public void userClicksApplyButtonForStatusFilter() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks Apply button for Status filter");
        System.out.println("==========================================");

        if (selectedStatus == null || selectedStatus.trim().isEmpty()) {
            throw new IllegalStateException(
                    "Status must be selected before clicking Apply."
            );
        }

        getFilterPage().applyStatusFilter(selectedStatus);

        System.out.println(
                "Status filter '" + selectedStatus + "' applied."
        );
    }

    // =========================================================
    // VERIFY FILTERED RESULTS
    // =========================================================

    @Then("Only activities with type {string} should be displayed")
    public void onlyActivitiesWithTypeShouldBeDisplayed(
            String activityType) {

        System.out.println("==========================================");
        System.out.println(
                "STEP: Verifying filtered results for type: "
                + activityType
        );
        System.out.println("==========================================");

        getFilterPage().verifyFilteredResults(activityType);

        System.out.println(
                "Filter verification passed for type: " + activityType
        );
    }

        // =========================================================
        // VERIFY STATUS FILTERED RESULTS
        // =========================================================

        @Then("Only activities with status {string} should be displayed")
        public void onlyActivitiesWithStatusShouldBeDisplayed(String status) {

        System.out.println("==========================================");
        System.out.println(
            "STEP: Verifying filtered results for status: " + status
        );
        System.out.println("==========================================");

        getFilterPage().verifyFilteredResultsByStatus(status);

        System.out.println(
            "Filter verification passed for status: " + status
        );
        }

        // =========================================================
        // CHANGE ITEMS PER PAGE
        // =========================================================

        @When("User changes items per page from {int} to {int}")
        public void userChangesItemsPerPageFromTo(int currentSize, int newSize) {

        System.out.println("==========================================");
        System.out.println(
            "STEP: User changes items per page from "
            + currentSize + " to " + newSize
        );
        System.out.println("==========================================");

        getFilterPage().changeItemsPerPage(currentSize, newSize);

        System.out.println(
            "Items per page changed from " + currentSize
            + " to " + newSize
        );
        }

        // =========================================================
        // VERIFY ACTIVITY RECORD COUNT
        // =========================================================

        @Then("User should see up to {int} activity records")
        public void userShouldSeeUpToActivityRecords(int expectedCount) {

        System.out.println("==========================================");
        System.out.println(
            "STEP: Verifying visible activity records up to: " + expectedCount
        );
        System.out.println("==========================================");

        getFilterPage().verifyActivityRecordCount(expectedCount);

        System.out.println(
            "Visible activity record count verified: " + expectedCount
        );
        }

    // =========================================================
    // CLICK SORT TAB
    // =========================================================

    @When("User clicks on Sort tab")
    public void userClicksOnSortTab() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks on Sort tab");
        System.out.println("==========================================");

        getFilterPage().clickSortTab();

        System.out.println("Sort tab opened.");
    }

    // =========================================================
    // SELECT SORT ORDER
    // =========================================================

    @When("User selects {string} sort order {string}")
    public void userSelectsSortOrder(String sortBy, String sortOrder) {

        System.out.println("==========================================");
        System.out.println(
                "STEP: User selects " + sortBy
                + " sort order " + sortOrder
        );
        System.out.println("==========================================");

        getFilterPage().selectSortOrder(sortBy, sortOrder);

        System.out.println(
                sortBy + " sort order selected: " + sortOrder
        );
    }

    // =========================================================
    // APPLY SORT
    // =========================================================

    @When("User clicks Apply button for Sort")
    public void userClicksApplyButtonForSort() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks Apply button for Sort");
        System.out.println("==========================================");

        getFilterPage().applySort();

        System.out.println("Sort applied.");
    }

    // =========================================================
    // VERIFY SORTED RESULTS
    // =========================================================

    @Then("Activities should be sorted by {string} in {string} order")
    public void activitiesShouldBeSortedByInOrder(
            String sortBy, String sortOrder) {

        System.out.println("==========================================");
        System.out.println(
                "STEP: Verifying activities sorted by " + sortBy
                + " in " + sortOrder + " order"
        );
        System.out.println("==========================================");

        getFilterPage().verifyActivitiesSorted(sortBy, sortOrder);

        System.out.println(
                "Activities sorted by " + sortBy
                + " in " + sortOrder + " order."
        );
    }

    // =========================================================
    // CLEAR ALL FILTERS
    // =========================================================

    @When("User clicks Clear All button")
    public void userClicksClearAllButton() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks Clear All button");
        System.out.println("==========================================");

        getFilterPage().clickClearAll();
        selectedActivityType = null;
        selectedStatus = null;

        System.out.println("All filters cleared.");
    }
}
