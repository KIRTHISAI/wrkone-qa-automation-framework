package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.DashboardPage;

public class DashboardStep extends baseClass {

    private DashboardPage dashboard;

    private DashboardPage getDashboardPage() {
        if (dashboard == null) {
            dashboard = new DashboardPage(driver);
        }
        return dashboard;
    }

    @Then("Dashboard should be displayed")
    public void dashboard_should_be_displayed() {

        getDashboardPage();
        System.out.println("Dashboard displayed successfully");
    }

    @When("User clicks Users card")
    public void user_clicks_users_card() {

        getDashboardPage().clickUsersCard();
    }

    @Then("User navigates to Users page")
    public void user_navigates_to_users_page() {

        getDashboardPage().clickUserManagement();
        getDashboardPage().clickUsersCard();

        System.out.println("Navigated to Users page successfully.");
    }
}