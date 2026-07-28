package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.DashboardPage;

public class DashboardStep extends baseClass {

    DashboardPage dashboard;

    @Then("Dashboard should be displayed")
    public void dashboard_should_be_displayed() {

        dashboard = new DashboardPage(driver);

        System.out.println("Dashboard displayed successfully");
    }

    @When("User clicks Users card")
    public void user_clicks_users_card() {

        dashboard.clickUsersCard();
    }
}