package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.When;
import pages.DashboardPage;

public class DashboardStep extends baseClass {

    DashboardPage dashboard;

    @When("User clicks User Management")
    public void user_clicks_user_management() {

        dashboard = new DashboardPage(driver);
        dashboard.clickUserManagement();
    }

    @When("User clicks Users")
    public void user_clicks_users() {

        dashboard.clickUsers();
    }
}