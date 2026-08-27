package pages.crm;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import model.SavedLocation;

public class SavedLocationPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By savedLocationsMenu = By.xpath(
            "//*[self::span or self::a or self::div][normalize-space()='Saved Locations']");
    private final By newLocationButton = By.xpath(
            "//button[normalize-space()='New Location' or .//*[normalize-space()='New Location']]");
    private final By searchField = By.xpath(
            "//input[contains(@placeholder, 'Search for location')]");
        private final By locationDialog = By.xpath(
            "//input[contains(@placeholder, 'Search for location')]"
                + "/ancestor::div[.//*[normalize-space()='Pick Location']][1]");
    private final By saveButton = By.xpath(
            "//div[.//*[normalize-space()='Pick Location']]"
                + "//button[normalize-space()='Save Location' or normalize-space()='Save' "
                + "or normalize-space()='Create Location']");
    private final By confirmButton = By.xpath(
            "//button[normalize-space()='Confirm' or normalize-space()='Delete']");

    public SavedLocationPage(WebDriver driver) {
        if (driver == null) {
            throw new IllegalArgumentException("WebDriver cannot be null.");
        }
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void open() {
        driver.get("https://wrkone.com/qa-crm/activities/saved-locations");
        wait.until(ExpectedConditions.urlContains("/qa-crm/activities/saved-locations"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[normalize-space()='Saved Locations']")));
    }

    public void openFromActivities() {
        wait.until(ExpectedConditions.elementToBeClickable(savedLocationsMenu)).click();
        wait.until(ExpectedConditions.urlContains("/qa-crm/activities/saved-locations"));
    }

    public void create(SavedLocation location) {
        wait.until(ExpectedConditions.elementToBeClickable(newLocationButton)).click();
        fillForm(location);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        waitForSaveCompletion(location.getName());
    }

    public void createFromSearch(String name, String searchText,
                                 String exactResult) {
        deleteIfPresent(name);
        wait.until(ExpectedConditions.elementToBeClickable(newLocationButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(locationDialog));
        type(field("Location name", "name"), name);
        type(searchField, searchText);

        By result = By.xpath(
                "//input[contains(@placeholder, 'Search for location')]"
                        + "/ancestor::div[.//*[normalize-space()='Pick Location']][1]"
                        + "//*[normalize-space()='" + exactResult + "']");
        wait.until(ExpectedConditions.elementToBeClickable(result)).click();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        waitForSaveCompletion(name);
    }

    private void deleteIfPresent(String name) {
        closeLocationDialogIfOpen();
        if (!isDisplayed(row(name))) {
            return;
        }

        openRowMenu(name);
        clickMenuItem("Delete");
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(row(name)));
    }

    public void edit(String existingName, SavedLocation updatedLocation) {
        openRowMenu(existingName);
        clickMenuItem("Edit");
        fillForm(updatedLocation);
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        waitForSaveCompletion(updatedLocation.getName());
    }

    public void editFromSearch(String existingName, String newName,
                               String searchText, String exactResult) {
        deleteIfPresent(newName);
        openRowMenu(existingName);
        clickMenuItem("Edit");
        wait.until(ExpectedConditions.visibilityOfElementLocated(locationDialog));
        type(field("Location name", "name"), newName);
        type(searchField, searchText);

        By result = By.xpath(
                "//input[contains(@placeholder, 'Search for location')]"
                        + "/ancestor::div[.//*[normalize-space()='Pick Location']][1]"
                        + "//*[normalize-space()=" + xpathLiteral(exactResult) + "]");
        wait.until(ExpectedConditions.elementToBeClickable(result)).click();
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
        waitForSaveCompletion(newName);
    }

    public void delete(String name) {
        openRowMenu(name);
        clickMenuItem("Delete");
        wait.until(ExpectedConditions.elementToBeClickable(confirmButton)).click();
        wait.until(ExpectedConditions.invisibilityOfElementLocated(row(name)));
    }

    public void verifyLocationDisplayed(String name, String address) {
        WebElement locationRow = wait.until(
                ExpectedConditions.visibilityOfElementLocated(row(name)));
        if (!locationRow.getText().contains(address)) {
            throw new AssertionError("Address not found for saved location '" + name + "'.");
        }
    }

    public void verifyLocationDeleted(String name) {
        if (!driver.findElements(row(name)).isEmpty()) {
            throw new AssertionError("Saved location still exists: " + name);
        }
    }

    private void fillForm(SavedLocation location) {
        type(field("Name", "name"), location.getName());
        type(field("Address", "address"), location.getAddress());
        if (!location.getLatitude().isBlank()) {
            type(field("Latitude", "latitude"), location.getLatitude());
        }
        if (!location.getLongitude().isBlank()) {
            type(field("Longitude", "longitude"), location.getLongitude());
        }
    }

    private By field(String label, String token) {
        return By.xpath("//label[contains(normalize-space(), '" + label + "')]/following::*[self::input or self::textarea][1]"
            + " | //*[(self::input or self::textarea) and contains(translate(@id, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + token + "') or contains(translate(@name, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + token + "') or contains(translate(@placeholder, 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), '"
                + token + "')]");
    }

    private void type(By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        if (!element.isEnabled() || element.getAttribute("readonly") != null
                || element.getAttribute("disabled") != null) {
            return;
        }
        element.clear();
        element.sendKeys(value);
    }

    private void openRowMenu(String name) {
        closeLocationDialogIfOpen();
        WebElement locationRow = wait.until(
                ExpectedConditions.visibilityOfElementLocated(row(name)));
        wait.until(ExpectedConditions.elementToBeClickable(
                locationRow.findElement(By.xpath(".//button")))).click();
    }

    private void closeLocationDialogIfOpen() {
        if (isDisplayed(locationDialog)) {
            driver.switchTo().activeElement().sendKeys(Keys.ESCAPE);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(locationDialog));
        }
    }

    private void waitForSaveCompletion(String name) {
        wait.until(webDriver -> {
            boolean locationVisible = isDisplayed(row(name));
            boolean dialogVisible = isDisplayed(locationDialog);
            return locationVisible || !dialogVisible;
        });
        closeLocationDialogIfOpen();
        waitForLocation(name);
    }

    private boolean isDisplayed(By locator) {
        try {
            for (WebElement element : driver.findElements(locator)) {
                if (element.isDisplayed()) {
                    return true;
                }
            }
            return false;
        } catch (StaleElementReferenceException e) {
            return false;
        }
    }

    private void clickMenuItem(String text) {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//*[self::button or @role='menuitem'][normalize-space()='" + text + "']"))).click();
    }

    private By row(String name) {
        return By.xpath("//*[self::tr or @role='row' or contains(@class, 'row')][.//*[normalize-space()="
                + xpathLiteral(name) + "]]");
    }

    private String xpathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        return "concat('" + value.replace("'", "', \"'\", '") + "')";
    }

    private void waitForLocation(String name) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(row(name)));
    }
}