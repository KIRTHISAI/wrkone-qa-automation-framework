package stepdefinitions;

import pages.AdduserPage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import base.baseClass;
import static org.junit.Assert.assertTrue;
public class AdduserStepdefinitions  extends baseClass {

    AdduserPage adduser;

    @When("User clicks Add User")
    public void user_clicks_add_user() {

        adduser = new AdduserPage(driver);
        adduser.clickAddUser();
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
    
    @And("User selects department")
    public void user_selects_department() {
        adduser.selectDepartment();
    }
    @When("User enters password {string}")
    public void user_enters_password(String pwd) {
        adduser.enterPassword(pwd);
    }
    @When("User clicks Create User")
    public void user_clicks_create_user() {
        adduser.clickCreateUser();
    }
    @Then("User should be created successfully!")
    public void user_should_be_created_successfully() throws InterruptedException {
        assertTrue(adduser.isUserCreatedSuccessfully());
        Thread.sleep(5000);
    }
    @Given("User is on Add User page")
    public void user_is_on_add_user_page() {
        adduser = new AdduserPage(driver);

    }
}
