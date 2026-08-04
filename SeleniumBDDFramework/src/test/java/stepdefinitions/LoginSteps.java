package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;
import pages.LoginPage;

public class LoginSteps extends baseClass {

    LoginPage login = new LoginPage(driver);

   

    @Given("User launches the browser")
    public void user_launches_the_browser() {
        launchBrowser();
        login = new LoginPage(driver);
    }

    @When("User enters email {string}")
    public void user_enters_email(String email) {
        login.enterEmail(email);
    }

    @When("User enters password {string}")
    public void user_enters_password(String password) {
        login.enterPassword(password);
    }

    @When("User clicks Login button")
    public void user_clicks_Login_button() {
        login.clickLogin();
    }

    @Then("User should login successfully")
    public void user_should_login_successfully() {
        System.out.println("Login Successful");
    }

}