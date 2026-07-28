package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    By userManagement = By.xpath("//span[text()='User Management']");
    By usersCard = By.id("core-dashboard-button-users");

    public void clickUserManagement() {
        wait.until(ExpectedConditions.elementToBeClickable(userManagement)).click();
    }

    public void clickUsersCard() {
        wait.until(ExpectedConditions.elementToBeClickable(usersCard)).click();
    }
}