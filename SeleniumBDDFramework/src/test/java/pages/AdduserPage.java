package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.UUID;
import java.util.Random;
public class AdduserPage {

    WebDriver driver;

    public static String generatedName;

    public AdduserPage(WebDriver driver) {
        this.driver = driver;
    }

    // Locators
        // Locators
        By addUserButton = By.xpath("//button[.//span[text()='Add User']]");
        By firstName = By.name("firstName");
        By lastName = By.name("lastName");
        By displayName = By.name("displayName");
        By primaryEmail = By.id("primaryEmail");
        By employeeId = By.id("empId");
        By departmentDropdown=By.id("department");
        By password = By.id("setPassword");
        By createUserButton = By.xpath("//button[normalize-space()='Create User']");
        By successMessage=By.xpath("//*[contains(text(),'User created successfully!')]");
        public void clickAddUser() {

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            wait.until(ExpectedConditions.elementToBeClickable(addUserButton)).click();
        }
        public void enterUserDetails() {

            String fname = "User" + new Random().nextInt(1000);
            String lname = "Test" + new Random().nextInt(1000);

            generatedName = fname + " " + lname;

            String email = "user" + System.currentTimeMillis() + "@onelern.com";
            String empId = String.valueOf(10000 + new Random().nextInt(90000));
            String pwd = "123456";

            enterFirstName(fname);
            enterLastName(lname);
            enterDisplayName(generatedName);
            enterEmail(email);
            enterEmployeeId(empId);
            selectDepartment();
            enterPassword(pwd);

            System.out.println("Generated Name = " + generatedName);
        }
       public void enterFirstName(String fname) {
       driver.findElement(firstName).sendKeys(fname);
    }

    public void enterLastName(String lname) {
        driver.findElement(lastName).sendKeys(lname);
    }

   public void enterDisplayName(String dname) {
        driver.findElement(displayName).sendKeys(dname);
    }

    public void enterEmail(String email) {

        WebElement emailField = driver.findElement(primaryEmail);

        emailField.clear();

       emailField.sendKeys(email);
    }

   public void enterEmployeeId(String id) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement emp =
                wait.until(ExpectedConditions.visibilityOfElementLocated(employeeId));

        emp.clear();
        emp.sendKeys(id);
    }
   public void selectDepartment() {

	    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

	    WebElement department = wait.until(
	            ExpectedConditions.elementToBeClickable(By.id("department")));

	    Select select = new Select(department);

	    // Select the second option (index starts from 0)
	    select.selectByIndex(1);
	}
    public void enterPassword(String pwd) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

       WebElement passwordField =
              wait.until(ExpectedConditions.visibilityOfElementLocated(password));

        passwordField.clear();
       passwordField.sendKeys(pwd);
    }
     public void clickCreateUser() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        wait.until(ExpectedConditions.elementToBeClickable(createUserButton)).click();
    }
    public boolean isUserCreatedSuccessfully() {
    	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    	return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage)).isDisplayed();
    	
    }
   
    }
    