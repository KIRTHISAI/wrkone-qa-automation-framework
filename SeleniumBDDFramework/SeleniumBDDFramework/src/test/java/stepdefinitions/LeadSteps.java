package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import pages.crm.LeadPage;

public class LeadSteps extends baseClass {

    private LeadPage leadPage;

    private LeadPage getLeadPage() {

        if (driver == null) {
            throw new IllegalStateException(
                    "WebDriver is NULL. Browser was not initialized."
            );
        }

        if (leadPage == null) {
            leadPage = new LeadPage(driver);
        }

        return leadPage;
    }

    @And("User clicks Sales")
    public void userClicksSales() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks Sales");
        System.out.println("==========================================");

        getLeadPage().clickSales();
    }

    @And("User clicks Leads")
    public void userClicksLeads() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks Leads");
        System.out.println("==========================================");

        getLeadPage().clickLeads();
    }

    @And("User clicks Create Lead button")
    public void userClicksCreateLeadButton() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks Create Lead button");
        System.out.println("==========================================");

        getLeadPage().clickCreateLead();
    }

    @And("User selects random Source of Lead")
    public void userSelectsRandomSourceOfLead() {

        System.out.println("==========================================");
        System.out.println("STEP: User selects random Source of Lead");
        System.out.println("==========================================");

        getLeadPage().selectRandomSourceOfLead();
    }

    @And("User selects Lead Type as {string}")
    public void userSelectsLeadTypeAs(String leadType) {

        System.out.println("==========================================");
        System.out.println("STEP: User selects Lead Type as " + leadType);
        System.out.println("==========================================");

        getLeadPage().selectLeadType(leadType);
    }

    @And("User enters School Name as {string}")
    public void userEntersSchoolNameAs(String schoolName) {

        System.out.println("==========================================");
        System.out.println("STEP: User enters School Name as " + schoolName);
        System.out.println("==========================================");

        getLeadPage().enterSchoolName(schoolName);
    }

    @And("User enters Lead Display Name as {string}")
    public void userEntersLeadDisplayNameAs(String displayName) {

        System.out.println("==========================================");
        System.out.println("STEP: User enters Display Name as " + displayName);
        System.out.println("==========================================");

        getLeadPage().enterDisplayName(displayName);
    }

    @And("User clicks Lead Submit button")
    public void userClicksLeadSubmitButton() {

        System.out.println("==========================================");
        System.out.println("STEP: User clicks Lead Submit button");
        System.out.println("==========================================");

        getLeadPage().clickSubmit();
    }

    @Then("Lead created successfully popup should be displayed")
    public void leadCreatedSuccessfullyPopupShouldBeDisplayed() {

        System.out.println("==========================================");
        System.out.println("STEP: Verify Lead created successfully popup");
        System.out.println("==========================================");

        getLeadPage().verifyLeadCreatedPopup();
    }
}
