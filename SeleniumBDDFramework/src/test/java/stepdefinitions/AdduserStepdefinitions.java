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
    @And("User enters user details")
    public void user_enters_user_details() {
        adduser.enterUserDetails();
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
