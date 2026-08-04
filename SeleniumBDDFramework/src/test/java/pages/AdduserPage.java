package pages;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdduserPage {

    WebDriver driver;
    WebDriverWait wait;

    public static String generatedName;

    public AdduserPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Locators
    By addUserButton = By.xpath("//button[.//span[text()='Add User']]");
    By firstName = By.name("firstName");
    By lastName = By.name("lastName");
    By displayName = By.name("displayName");
    By primaryEmail = By.id("primaryEmail");
    By employeeId = By.id("empId");
    By departmentDropdown = By.id("department");
    By password = By.id("setPassword");
    By createUserButton = By.xpath("//button[normalize-space()='Create User']");
    By successMessage = By.xpath("//*[contains(text(),'User created successfully!')]");

    // Random Names
    private static final String[] FIRST_NAMES = {
            "Aarav", "Vihaan", "Arjun", "Sai", "Rahul",
            "Priya", "Ananya", "Sneha", "Keerthi", "Niharika",
            "Aditya", "Rohan", "Kiran", "Harsha", "Vikram",
            "Akash", "Rishi", "Manoj", "Surya", "Abhinav",
            "Pooja", "Divya", "Kavya", "Lakshmi", "Bhavya"
    };

    private static final String[] LAST_NAMES = {
            "Sharma", "Reddy", "Kumar", "Patel", "Verma",
            "Singh", "Rao", "Naidu", "Gupta", "Joshi",
            "Mehta", "Nair", "Das", "Chowdary", "Yadav",
            "Rana", "Mishra", "Pandey", "Kulkarni", "Iyer",
            "Varma", "Shetty", "Babu", "Rathod", "Kapoor"
    };

    public void clickAddUser() {
        wait.until(ExpectedConditions.elementToBeClickable(addUserButton)).click();
    }

    public void enterUserDetails() {

        Random random = new Random();

        String fname = FIRST_NAMES[random.nextInt(FIRST_NAMES.length)];
        String lname = LAST_NAMES[random.nextInt(LAST_NAMES.length)];

        generatedName = fname + " " + lname;

        String email = fname.toLowerCase()
                + System.currentTimeMillis()
                + "@onelern.com";

        String empId = String.valueOf(10000 + random.nextInt(90000));

        String pwd = "123456";

        enterFirstName(fname);
        enterLastName(lname);
        enterDisplayName(generatedName);
        enterEmail(email);
        enterEmployeeId(empId);
        selectDepartment();
        enterPassword(pwd);

        System.out.println("Generated Name : " + generatedName);
        System.out.println("Generated Email: " + email);
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
        WebElement emp = wait.until(
                ExpectedConditions.visibilityOfElementLocated(employeeId));
        emp.clear();
        emp.sendKeys(id);
    }

    public void selectDepartment() {

        WebElement department = wait.until(
                ExpectedConditions.elementToBeClickable(departmentDropdown));

        Select select = new Select(department);

        select.selectByIndex(1);
    }

    public void enterPassword(String pwd) {

        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(password));

        passwordField.clear();
        passwordField.sendKeys(pwd);
    }

    public void clickCreateUser() {

        wait.until(
                ExpectedConditions.elementToBeClickable(createUserButton))
                .click();
    }

    public boolean isUserCreatedSuccessfully() {

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(successMessage))
                .isDisplayed();
    }
}