package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {

    WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    By userManagement = By.xpath("//span[text()='User Management']");
    By users = By.xpath("//span[text()='Users']");

    public void clickUserManagement() {
        driver.findElement(userManagement).click();
    }

    public void clickUsers() {
        driver.findElement(users).click();
    }
}