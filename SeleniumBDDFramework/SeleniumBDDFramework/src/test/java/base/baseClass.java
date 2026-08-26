package base;

import java.io.InputStream;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.LoginPage;

public class baseClass {

    public static WebDriver driver;
    protected static Properties properties;
        private LoginPage loginPage;

    protected static final String LOGIN_URL =
            "https://wrkone.com/qa-core/login";

    protected static final String DASHBOARD_URL =
            "https://wrkone.com/qa-core/dashboard";

    private static final Duration PAGE_LOAD_TIMEOUT =
            Duration.ofSeconds(60);

    // ============================================================
    // LOAD CONFIG
    // ============================================================

    public static synchronized void loadConfig() {

        if (properties != null) {
            return;
        }

        properties = new Properties();

        try (InputStream input =
                     baseClass.class
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (input == null) {

                throw new RuntimeException(
                        "config.properties not found in "
                                + "src/test/resources");
            }

            properties.load(input);

            System.out.println(
                    "config.properties loaded successfully.");

            System.out.println(
                    "Available config keys: "
                            + properties.stringPropertyNames());

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to load config.properties", e);
        }
    }

    // ============================================================
    // GET CONFIG VALUE
    // ============================================================

    public static String getConfigValue(String... keys) {

        if (properties == null) {
            loadConfig();
        }

        for (String key : keys) {

            String value =
                    properties.getProperty(key);

            if (value != null && !value.trim().isEmpty()) {
                return value.trim();
            }
        }

        return null;
    }

    // ============================================================
    // GET USERNAME
    // ============================================================

    public static String getUsername() {

        String username =
                getConfigValue(
                        "login.email",
                        "crm.email",
                        "linked.crm.username",
                        "username",
                        "userName",
                        "email",
                        "useremail",
                        "userEmail",
                        "loginUsername"
                );

        if (username == null || username.isBlank()) {

            throw new IllegalStateException(
                    "Username is missing in config.properties."
            );
        }

        return username;
    }

    // ============================================================
    // GET PASSWORD
    // ============================================================

    public static String getPassword() {

        String password =
                getConfigValue(
                        "login.password",
                        "crm.activity.password",
                        "linked.crm.password",
                        "password",
                        "pass",
                        "loginPassword"
                );

        if (password == null || password.isBlank()) {

            throw new IllegalStateException(
                    "Password is missing in config.properties."
            );
        }

        return password;
    }
    public static synchronized void launchBrowser1() {

        if (driver != null) {

            try {

                driver.getWindowHandle();

                System.out.println(
                        "Existing Chrome session is active.");

                System.out.println(
                        "Current URL = "
                                + driver.getCurrentUrl());

                return;

            } catch (Exception e) {

                System.out.println(
                        "Existing driver is invalid. "
                                + "Creating a new browser.");

                driver = null;
            }
        }

        System.out.println(
                "Starting Chrome browser...");

        WebDriverManager.chromedriver().setup();

        ChromeOptions options =
                new ChromeOptions();

        Map<String, Object> prefs =
                new HashMap<>();

        prefs.put(
                "credentials_enable_service",
                false);

        prefs.put(
                "profile.password_manager_enabled",
                false);

        prefs.put(
                "profile.password_manager_leak_detection",
                false);

        options.setExperimentalOption(
                "prefs",
                prefs);

        options.addArguments(
                "--disable-features=PasswordLeakDetection");

        options.addArguments(
                "--disable-save-password-bubble");

        options.addArguments(
                "--no-first-run");

        options.addArguments(
                "--no-default-browser-check");

        driver =
                new ChromeDriver(options);

        driver.manage()
                .window()
                .maximize();

        driver.manage()
                .timeouts()
                .pageLoadTimeout(
                        PAGE_LOAD_TIMEOUT);

        driver.manage()
                .timeouts()
                .implicitlyWait(
                        Duration.ZERO);

        driver.get(LOGIN_URL);

        System.out.println(
                "URL = "
                        + driver.getCurrentUrl());

        System.out.println(
                "Chrome launched successfully.");
    }
    // ============================================================
    // OPEN LOGIN PAGE
    // ============================================================

    public static synchronized void openLoginPage() {

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver is not initialized. "
                            + "Launch the browser before opening "
                            + "the login page.");
        }

        try {

            String currentUrl =
                    driver.getCurrentUrl();

            System.out.println(
                    "Current URL = " +
                            currentUrl);

            if (!currentUrl.contains(
                    "/qa-core/login")) {

                System.out.println(
                        "Navigating to login page...");

                driver.get(LOGIN_URL);
            }

        } catch (Exception e) {

            throw new IllegalStateException(
                    "Unable to open login page.",
                    e);
        }

        System.out.println(
                "Login page URL = " +
                        driver.getCurrentUrl());
    }

    // ============================================================
    // GET DRIVER
    // ============================================================

    public static WebDriver getDriver() {

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver is not initialized."
            );
        }

        return driver;
    }

        protected LoginPage getLoginPage() {

                return loginPage != null
                                ? loginPage
                                : (loginPage = new LoginPage(getDriver()));
        }

        protected static void waitForUrl(String urlFragment) {

                new WebDriverWait(driver, Duration.ofSeconds(30))
                                .until(ExpectedConditions.urlContains(urlFragment));
        }

    public static synchronized void closeBrowser() {

        if (driver == null) {

            System.out.println(
                    "No active browser to close.");

            return;
        }

        System.out.println(
                "Closing Chrome browser...");

        try {

            driver.quit();

            System.out.println(
                    "Chrome closed successfully.");

        } catch (Exception e) {

            System.out.println(
                    "Browser close warning: "
                            + e.getMessage());

        } finally {

            driver = null;
        }

        System.out.println(
                "======================================");
    }

	public static String getProperty(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}