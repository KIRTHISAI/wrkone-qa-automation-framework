package pages.crm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class LeadPage extends ActivityCommonPage {

    private final By salesMenu = By.xpath(
            "//span[normalize-space()='Sales']"
    );

    private final By leadsMenu = By.xpath(
            "//*[normalize-space()='Leads']"
    );

    private final By createLeadButton = By.xpath(
            "//button[normalize-space()='Create Lead' or .//text()[normalize-space()='Create Lead']]"
    );

    private final By sourceOfLeadDropdown = By.xpath(
            "//*[normalize-space()='Source of Lead *' or normalize-space()='Source of Lead']"
            + "/following::select[1]"
    );

    private final By leadTypeDropdown = By.xpath(
            "//*[normalize-space()='Lead Type *' or normalize-space()='Lead Type']"
            + "/following::select[1]"
    );

    private final By schoolNameInput = By.xpath(
            "//*[normalize-space()='School Name *' or normalize-space()='School Name']"
            + "/following::input[1]"
    );

    private final By displayNameInput = By.xpath(
            "//*[normalize-space()='Display Name *' or normalize-space()='Display Name']"
            + "/following::input[1]"
    );

    private final By submitButton = By.xpath(
            "//button[normalize-space()='Submit' or .//text()[normalize-space()='Submit']]"
    );

    private final By leadCreatedPopup = By.xpath(
            "//*[contains(translate(normalize-space(), "
            + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), "
            + "'lead created succesfully') "
            + "or contains(translate(normalize-space(), "
            + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), "
            + "'lead created successfully')]"
    );

    private final Random random = new Random();

    public LeadPage(WebDriver driver) {
        super(driver);
        System.out.println("LeadPage initialized.");
    }

    public void clickSales() {

        verifyCRMPageLoaded();

        WebElement sales = wait.until(
                ExpectedConditions.elementToBeClickable(salesMenu)
        );

        scrollIntoView(sales);
        clickElement(sales, "Sales menu");
        System.out.println("Sales menu clicked.");
    }

    public void clickLeads() {

        WebElement leads = wait.until(
                ExpectedConditions.elementToBeClickable(leadsMenu)
        );

        scrollIntoView(leads);
        clickElement(leads, "Leads menu");

        wait.until(ExpectedConditions.urlContains("/qa-crm/leads"));
        System.out.println("Leads page opened.");
    }

    public void clickCreateLead() {

        WebElement createLead = wait.until(
                ExpectedConditions.elementToBeClickable(createLeadButton)
        );

        scrollIntoView(createLead);
        clickElement(createLead, "Create Lead button");

        wait.until(ExpectedConditions.urlContains("/qa-crm/leads/create"));
        System.out.println("Create Lead page opened.");
    }

    public String selectRandomSourceOfLead() {

        WebElement sourceDropdown = wait.until(
                ExpectedConditions.elementToBeClickable(sourceOfLeadDropdown)
        );

        scrollIntoView(sourceDropdown);

        Select sourceSelect = new Select(sourceDropdown);
        List<WebElement> selectableSources = new ArrayList<>();

        for (WebElement option : sourceSelect.getOptions()) {
            String optionText = option.getText().trim();
            String optionValue = option.getAttribute("value");

            if (!optionText.isEmpty()
                    && !optionText.toLowerCase().contains("select")
                    && optionValue != null
                    && !optionValue.trim().isEmpty()) {
                selectableSources.add(option);
            }
        }

        if (selectableSources.isEmpty()) {
            throw new AssertionError("No selectable Source of Lead options found.");
        }

        WebElement selectedSource = selectableSources.get(
                random.nextInt(selectableSources.size())
        );
        String selectedSourceText = selectedSource.getText().trim();

        sourceSelect.selectByVisibleText(selectedSourceText);
        System.out.println("Selected Source of Lead: " + selectedSourceText);

        return selectedSourceText;
    }

    public void selectLeadType(String leadType) {

        WebElement leadTypeElement = wait.until(
                ExpectedConditions.elementToBeClickable(leadTypeDropdown)
        );

        scrollIntoView(leadTypeElement);
        new Select(leadTypeElement).selectByVisibleText(required(leadType, "Lead Type"));
        System.out.println("Selected Lead Type: " + leadType);
    }

    public void enterSchoolName(String schoolName) {

        WebElement schoolNameElement = wait.until(
                ExpectedConditions.elementToBeClickable(schoolNameInput)
        );

        scrollIntoView(schoolNameElement);
        schoolNameElement.clear();
        schoolNameElement.sendKeys(required(schoolName, "School Name"));
        System.out.println("Entered School Name: " + schoolName);
    }

    public void enterDisplayName(String displayName) {

        WebElement displayNameElement = wait.until(
                ExpectedConditions.elementToBeClickable(displayNameInput)
        );

        scrollIntoView(displayNameElement);
        displayNameElement.clear();
        displayNameElement.sendKeys(required(displayName, "Display Name"));
        System.out.println("Entered Display Name: " + displayName);
    }

    public void clickSubmit() {

        WebElement submit = wait.until(
                ExpectedConditions.elementToBeClickable(submitButton)
        );

        scrollIntoView(submit);
        clickElement(submit, "Submit button");
        System.out.println("Submit button clicked.");
    }

    public void verifyLeadCreatedPopup() {

        WebElement popup = wait.until(
                ExpectedConditions.visibilityOfElementLocated(leadCreatedPopup)
        );

        System.out.println("Lead created popup displayed: " + popup.getText().trim());
    }

    private void clickElement(WebElement element, String elementName) {

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            System.out.println(
                    "Normal click intercepted on " + elementName
                    + ". Using JavaScript click."
            );
            jsClick(element);
        }
    }
}
