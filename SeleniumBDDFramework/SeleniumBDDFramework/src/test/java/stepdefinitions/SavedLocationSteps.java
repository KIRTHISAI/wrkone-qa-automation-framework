package stepdefinitions;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.SavedLocation;
import pages.crm.SavedLocationPage;
import base.baseClass;

public class SavedLocationSteps extends baseClass {

    private SavedLocationPage savedLocationPage;

    private SavedLocationPage getSavedLocationPage() {
        if (driver == null) {
            throw new IllegalStateException("WebDriver is not initialized.");
        }
        if (savedLocationPage == null) {
            savedLocationPage = new SavedLocationPage(driver);
        }
        return savedLocationPage;
    }

    @When("User opens Saved Locations")
    public void userOpensSavedLocations() {
        getSavedLocationPage().open();
    }

    @When("User creates saved location {string} at {string} with latitude {string} and longitude {string}")
    public void userCreatesSavedLocation(String name, String address,
                                         String latitude, String longitude) {
        getSavedLocationPage().create(
                new SavedLocation(name, address, latitude, longitude));
    }

    @When("User creates saved location {string} by searching {string} and selecting {string}")
    public void userCreatesSavedLocationBySearch(String name,
                                                 String searchText,
                                                 String exactResult) {
        getSavedLocationPage().createFromSearch(name, searchText, exactResult);
    }

    @When("User edits saved location {string} to {string} at {string} with latitude {string} and longitude {string}")
    public void userEditsSavedLocation(String existingName, String newName,
                                       String address, String latitude,
                                       String longitude) {
        getSavedLocationPage().edit(existingName,
                new SavedLocation(newName, address, latitude, longitude));
    }

    @When("User edits saved location {string} to {string} by searching {string} and selecting {string}")
    public void userEditsSavedLocationBySearch(String existingName,
                                               String newName,
                                               String searchText,
                                               String exactResult) {
        getSavedLocationPage().editFromSearch(existingName, newName,
                searchText, exactResult);
    }

    @When("User deletes saved location {string}")
    public void userDeletesSavedLocation(String name) {
        getSavedLocationPage().delete(name);
    }

    @Then("Saved location {string} should show address {string}")
    public void savedLocationShouldShowAddress(String name, String address) {
        getSavedLocationPage().verifyLocationDisplayed(name, address);
    }

    @Then("Saved location {string} should be deleted")
    public void savedLocationShouldBeDeleted(String name) {
        getSavedLocationPage().verifyLocationDeleted(name);
    }
}