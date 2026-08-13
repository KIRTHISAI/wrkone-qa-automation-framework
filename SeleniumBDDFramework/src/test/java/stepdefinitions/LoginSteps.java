package stepdefinitions;

import base.baseClass;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import pages.LoginPage;

public class LoginSteps extends baseClass {

    private LoginPage loginPage;

    // ============================================================
    // GET LOGIN PAGE
    // ============================================================

    private LoginPage getLoginPage() {

        if (loginPage == null) {

            loginPage =
                    new LoginPage(driver);
        }

        return loginPage;
    }

    // ============================================================
    // LAUNCH BROWSER
    // ============================================================

    @Given("User launches the browser")
    public void userLaunchesTheBrowser() {

        System.out.println(
                "Launching/reusing browser...");

        launchBrowser1();

        openLoginPage();

        System.out.println(
                "Browser ready.");
    }

    // ============================================================
    // ENTER EMAIL
    // ============================================================

    @When("User enters login email")
    public void userEntersLoginEmail() {

        String username =
                getUsername();

        getLoginPage()
                .enterEmail(username);
    }

    // ============================================================
    // ENTER PASSWORD
    // ============================================================

    @When("User enters login password")
    public void userEntersLoginPassword() {

        String password =
                getPassword();

        getLoginPage()
                .enterPassword(password);
    }

    // ============================================================
    // CLICK LOGIN
    // ============================================================

    @When("User clicks Login button")
    public void userClicksLoginButton() {

        getLoginPage()
                .clickLogin();
    }

    // ============================================================
    // VERIFY LOGIN
    // ============================================================
    @Then("User should login successfully")
    public void userShouldLoginSuccessfully() {

        System.out.println("Verifying dashboard...");

        if (!getLoginPage().isDashboardDisplayed()) {

            System.out.println(
                    "Login verification failed."
            );

            System.out.println(
                    "Current URL = [" +
                    driver.getCurrentUrl() +
                    "]"
            );

            throw new AssertionError(
                    "Login failed. Dashboard was not displayed."
            );
        }

        System.out.println(
                "Login successful. Dashboard is displayed."
        );
    }
}