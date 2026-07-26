package stepdefinitions;

import pages.AdduserPage;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import base.baseClass;

public class AdduserStepdefinitions  extends baseClass {

    AdduserPage adduser;

    @Given("User is on Add User page")
    public void user_is_on_add_user_page() {
        adduser = new AdduserPage(driver);
    }
    

    @When("User enters first name {string}")
    public void user_enters_first_name(String fname) {
        adduser.enterFirstName(fname);
    }

    @When("User enters last name {string}")
    public void user_enters_last_name(String lname) {
        adduser.enterLastName(lname);
    }

    @When("User enters display name {string}")
    public void user_enters_display_name(String dname) {
        adduser.enterDisplayName(dname);
    }

    @When("User enters email {string}")
    public void user_enters_email(String email) {
        adduser.enterEmail(email);
    }

    @When("User enters employee id {string}")
    public void user_enters_employee_id(String id) {
        adduser.enterEmployeeId(id);
    }

    @When("User clicks Save")
    public void user_clicks_save() {
        adduser.clickSave();
    }
}
