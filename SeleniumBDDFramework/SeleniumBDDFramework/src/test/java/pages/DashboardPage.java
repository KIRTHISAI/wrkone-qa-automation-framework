package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import java.time.Duration;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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

    public void clickUsersCard() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        WebElement usersButton = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("core-dashboard-button-users")
            )
        );

        usersButton.click();
    }
}