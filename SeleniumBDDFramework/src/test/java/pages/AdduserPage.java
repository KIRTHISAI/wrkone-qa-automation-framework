package pages;

import java.time.Duration;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AdduserPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public static String generatedName;
    public static String generatedEmail;
    public static String generatedEmployeeId;

    public AdduserPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ==============================
    // Locators
    // ==============================

    private final By addUserButton =
            By.xpath("//button[.//span[text()='Add User']]");

    private final By firstName =
            By.name("firstName");

    private final By lastName =
            By.name("lastName");

    private final By displayName =
            By.name("displayName");

    private final By primaryEmail =
            By.id("primaryEmail");

    private final By employeeId =
            By.id("empId");

    private final By departmentDropdown =
            By.id("department");

    private final By password =
            By.id("setPassword");

    private final By createUserButton =
            By.xpath("//button[normalize-space()='Create User']");

    private final By successMessage =
            By.xpath("//*[contains(text(),'User created successfully!')]");


    // ==============================
    // Add User
    // ==============================

    public void clickAddUser() {

        System.out.println("Waiting for Add User button...");

        wait.until(
                ExpectedConditions.elementToBeClickable(addUserButton)
        ).click();

        System.out.println("Add User button clicked successfully.");

        // Wait until the form is actually loaded
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstName)
        );

        System.out.println("Add User form detected.");
    }


    // ==============================
    // Enter User Details
    // ==============================
    public void enterUserDetails() {

        String[] firstNames = {
            "John", "David", "Michael", "Robert", "James",
            "Daniel", "William", "Thomas", "Joseph", "Andrew"
        };

        String[] lastNames = {
            "Smith", "Johnson", "Williams", "Brown", "Wilson",
            "Taylor", "Anderson", "Thomas", "Jackson", "White"
        };

        Random random = new Random();

        String fname = firstNames[random.nextInt(firstNames.length)];
        String lname = lastNames[random.nextInt(lastNames.length)];

        generatedName = fname + " " + lname;

        generatedEmail =
                fname.toLowerCase() +
                "." +
                lname.toLowerCase() +
                System.currentTimeMillis() +
                "@onelern.com";

        generatedEmployeeId =
                String.valueOf(
                        10000 + random.nextInt(90000)
                );

        String pwd = "123456";

        System.out.println("======================================");
        System.out.println("Creating User");
        System.out.println("First Name   = " + fname);
        System.out.println("Last Name    = " + lname);
        System.out.println("Display Name = " + generatedName);
        System.out.println("Email        = " + generatedEmail);
        System.out.println("Employee ID  = " + generatedEmployeeId);
        System.out.println("======================================");

        enterFirstName(fname);
        enterLastName(lname);
        enterDisplayName(generatedName);
        enterEmail(generatedEmail);
        enterEmployeeId(generatedEmployeeId);
        selectDepartment();
        enterPassword(pwd);
    }

    // ==============================
    // First Name
    // ==============================

    public void enterFirstName(String fname) {

        System.out.println("Entering First Name = " + fname);

        clearAndType(firstName, fname);
    }


    // ==============================
    // Last Name
    // ==============================

    public void enterLastName(String lname) {

        System.out.println("Entering Last Name = " + lname);

        clearAndType(lastName, lname);
    }


    // ==============================
    // Display Name
    // ==============================

    public void enterDisplayName(String dname) {

        System.out.println("Entering Display Name = " + dname);

        clearAndType(displayName, dname);
    }


    // ==============================
    // Email
    // ==============================

    public void enterEmail(String email) {

        System.out.println("Entering Email = " + email);

        clearAndType(primaryEmail, email);
    }


    // ==============================
    // Employee ID
    // ==============================

    public void enterEmployeeId(String id) {

        System.out.println("Entering Employee ID = " + id);

        clearAndType(employeeId, id);
    }


    // ==============================
    // Department
    // ==============================

    public void selectDepartment() {

        System.out.println("Selecting Department...");

        for (int attempt = 1; attempt <= 3; attempt++) {

            try {

                WebElement department =
                        wait.until(
                                ExpectedConditions.elementToBeClickable(
                                        departmentDropdown
                                )
                        );

                Select select = new Select(department);

                select.selectByIndex(1);

                System.out.println("Department selected successfully.");

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Department element became stale. Retry: "
                                + attempt
                );

                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }


    // ==============================
    // Password
    // ==============================

    public void enterPassword(String pwd) {

        System.out.println("Entering Password...");

        clearAndType(password, pwd);
    }


    // ==============================
    // Generic Clear & Type
    // ==============================

    private void clearAndType(By locator, String value) {

        for (int attempt = 1; attempt <= 3; attempt++) {

            try {

                WebElement element =
                        wait.until(
                                ExpectedConditions.visibilityOfElementLocated(
                                        locator
                                )
                        );

                wait.until(
                        ExpectedConditions.elementToBeClickable(locator)
                );

                // IMPORTANT:
                // Get a fresh element immediately before interaction
                element = driver.findElement(locator);

                element.click();

                element.clear();

                element.sendKeys(value);

                return;

            } catch (StaleElementReferenceException e) {

                System.out.println(
                        "Stale element detected for "
                                + locator
                                + ". Retry attempt: "
                                + attempt
                );

                if (attempt == 3) {
                    throw e;
                }
            }
        }
    }


    // ==============================
    // Create User
    // ==============================

    public void clickCreateUser() {

        System.out.println("Waiting for Create User button...");

        wait.until(
                ExpectedConditions.elementToBeClickable(createUserButton)
        ).click();

        System.out.println("Create User button clicked.");
    }


    // ==============================
    // Verify User Created
    // ==============================

    public boolean isUserCreatedSuccessfully() {

        System.out.println("Waiting for User created successfully message...");

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        successMessage
                )
        ).isDisplayed();
    }


    // ==============================
    // Getters
    // ==============================

    public WebDriver getDriver() {
        return driver;
    }

    public String getGeneratedName() {
        return generatedName;
    }

    public String getGeneratedEmail() {
        return generatedEmail;
    }

    public String getGeneratedEmployeeId() {
        return generatedEmployeeId;
    }
}