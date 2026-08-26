package stepdefinitions;

import base.baseClass;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import pages.LoginPage;

public class LoginSteps extends baseClass {

    // =========================================================
    // LAUNCH BROWSER
    // =========================================================

    @Given("User launches the browser")
    public void userLaunchesTheBrowser() {

        System.out.println(
                "Launching/reusing browser...");

        // Use the browser method from baseClass
        launchBrowser1();

        // Make sure we are on login page
        openLoginPage();

        System.out.println(
                "Current URL = "
                        + driver.getCurrentUrl());

        System.out.println(
                "Browser ready.");
    }

    // =========================================================
    // ENTER LOGIN EMAIL
    // =========================================================

    @When("User enters login email")
    public void userEntersLoginEmail() {

        getLoginPage().enterEmail(
                getConfigValue("login.email")
        );
    }

    // =========================================================
    // ENTER LOGIN PASSWORD
    // =========================================================

    @And("User enters login password")
    public void userEntersLoginPassword() {

        getLoginPage().enterPassword(
                getConfigValue("login.password")
        );
    }

    // =========================================================
    // CLICK LOGIN
    // =========================================================

    @And("User clicks Login button")
    public void userClicksLoginButton() {

        getLoginPage().clickLogin();
    }

    // =========================================================
    // VERIFY LOGIN
    // =========================================================

    @Then("User should login successfully")
    public void userShouldLoginSuccessfully() {

        getLoginPage().verifyLoginSuccessful();
    }

	public void login(String email, String password) {
		// TODO Auto-generated method stub
		
	}
}