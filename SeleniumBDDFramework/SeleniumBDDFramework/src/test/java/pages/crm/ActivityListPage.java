package pages.crm;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ActivityListPage extends ActivityCommonPage {

    private final By searchInput = By.cssSelector("input[placeholder='Search activities']");

    public ActivityListPage(WebDriver driver) {
        super(driver);
    }

        public String captureNewestActivityId() {
        By newestActivityId = By.xpath(
            "(//tr//*[starts-with(normalize-space(), 'ACT-')])[1]");
        String activityId = wait.until(
            ExpectedConditions.visibilityOfElementLocated(newestActivityId)).getText().trim();
        if (activityId.isEmpty()) {
            throw new IllegalStateException("Created activity ID was not displayed.");
        }

        return activityId;
        }

        public void selectActivityAction(String activityId, String action) {
        WebElement search = wait.until(
            ExpectedConditions.elementToBeClickable(searchInput));

        search.clear();
        search.sendKeys(activityId);

        By exactActivityRow = By.xpath(
                "//tr[.//*[normalize-space()=" + toXPathLiteral(activityId) + "]][1]");
        WebElement row = wait.until(
            ExpectedConditions.visibilityOfElementLocated(exactActivityRow));

        scrollActionsIntoView(row);

        WebElement actionMenu = wait.until(webDriver -> {
            WebElement currentRow = webDriver.findElement(exactActivityRow);
            java.util.List<WebElement> buttons = currentRow.findElements(By.tagName("button"));
            return buttons.isEmpty() ? null : buttons.get(buttons.size() - 1);
        });
        jsClick(actionMenu);

        By actionOption = By.xpath(
                "//*[self::button or self::a or @role='menuitem']"
                + "[contains(normalize-space(.), " + toXPathLiteral(action) + ")]");
        WebElement option = wait.until(
                ExpectedConditions.elementToBeClickable(actionOption));
        jsClick(option);
    }

        public void waitForActivityMessage(String message) {
                By toast = By.xpath("//*[contains(normalize-space(), "
                                + toXPathLiteral(message) + ")]");
                wait.until(ExpectedConditions.visibilityOfElementLocated(toast));
        }

    private void scrollActionsIntoView(WebElement row) {
        WebElement container = (WebElement) ((JavascriptExecutor) driver).executeScript(
                "let element = arguments[0];"
                + "while (element && element.parentElement) {"
                + "  element = element.parentElement;"
                + "  if (element.scrollWidth > element.clientWidth) return element;"
                + "}"
                + "return arguments[0];", row);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollLeft = arguments[0].scrollWidth;", container);
    }

    private String toXPathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }
        if (!value.contains("\"")) {
            return "\"" + value + "\"";
        }
        return "concat('" + value.replace("'", "', \"'\", '") + "')";
    }
}
