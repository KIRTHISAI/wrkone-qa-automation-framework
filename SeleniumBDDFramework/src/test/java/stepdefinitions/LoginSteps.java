package stepdefinitions;
 
import base.baseClass;
import org.openqa.selenium.By;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import pages.LoginPage;
 
public class LoginSteps extends baseClass {
 
    LoginPage login;
 
    @Given("User launches browser")
    public void loginpage() {
    launchBrowser();
   
    }
 
    @When("User enters Email and password")
    public void user_enters_Email_and_password() {
   
    	driver.findElement(By.id("email")).sendKeys("org2.1admin@onelern.com");
    	driver.findElement(By.id("password")).sendKeys("123456");
    	driver.findElement(By.xpath("//button[@type='submit']")).click();
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
 
