package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage  {
 
    WebDriver driver;
 
    public LoginPage(WebDriver ChromeDriver) {
        this.driver = ChromeDriver;
    }
 
    By email = By.id("email");
    By password = By.id("password");
    By loginBtn = By.xpath("//button[@type='submit']");
 
    public void enterEmail(String user) {
        driver.findElement(email).sendKeys(user);
    }
 
    public void enterPassword(String pass) {
        driver.findElement(password).sendKeys(pass);
    }
 
    public void clickLogin() {
        driver.findElement(loginBtn).click();
    }
}