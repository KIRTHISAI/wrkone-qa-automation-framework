package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
public class baseClass {

    public static WebDriver driver;
    public static void launchBrowser() {

        if (driver == null) {

            driver = new ChromeDriver();
            driver.manage().window().maximize();

           driver.manage().timeouts()
                    .implicitlyWait(Duration.ofSeconds(10));

            driver.get("https://wrkone.com/qa-core/login");
        }
    }

    public static void closeBrowser() {

        if (driver != null) {

            driver.quit();
            driver = null;
        }
    }
}