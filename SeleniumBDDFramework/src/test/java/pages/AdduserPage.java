package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdduserPage {

    WebDriver driver;

    public AdduserPage(WebDriver Chromedriver) {
        this.driver = Chromedriver;
    }

    By firstName = By.xpath("//input[@name='firstName']");
    By lastName = By.xpath("//input[@name='lastName']");
    By displayName = By.xpath("//input[@name='displayName']");
    By primaryEmail = By.xpath("//input[@name='email']");
    By employeeId = By.xpath("//input[@name='employeeId']");
    By saveButton = By.xpath("//button[text()='Save']");

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
        driver.findElement(primaryEmail).clear();
        driver.findElement(primaryEmail).sendKeys(email);
    }

    public void enterEmployeeId(String id) {
        driver.findElement(employeeId).sendKeys(id);
    }

    public void clickSave() {
        driver.findElement(saveButton).click();
    }
}
