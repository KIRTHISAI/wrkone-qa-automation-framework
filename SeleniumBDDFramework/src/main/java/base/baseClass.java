package base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class baseClass {

    public static WebDriver driver;
    

        public static String createdUserName;

        // existing methods...

    public static void launchBrowser() {

        if (driver == null) {

            WebDriverManager.chromedriver().setup();

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