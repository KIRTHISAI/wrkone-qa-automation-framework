package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.LoginPage;

public class LoginSteps extends baseClass {

    LoginPage login;

    @Given("User launches browser")
    public void user_launches_browser() {

        launchBrowser();

        login = new LoginPage(driver);
    }

    @When("User enters Email and password")
    public void user_enters_email_and_password() {

        login.enterEmail("org2.1admin@onelern.com");
        login.enterPassword("123456");
    }

    @When("User clicks login button")
    public void user_clicks_login_button() {

        login.clickLogin();
    }

    @Then("User should login successfully")
    public void user_should_login_successfully() {

        System.out.println("Login Successful");
    }
}