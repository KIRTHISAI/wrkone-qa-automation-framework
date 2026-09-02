@CRM
@SavedLocation

Feature: Saved Location CRUD

  @CreateEditDeleteSavedLocation
  Scenario: Create, edit, and delete a saved location

    Given User logs in for CRM Activity from Excel row 1
    When User opens Saved Locations
    And User creates saved location "Generic" by searching "Hyderabad, Telangana" and selecting "Hyderabad, Telangana, India"
    Then Saved location "Generic" should show address "Hyderabad, Telangana, India"
    When User edits saved location "Generic" to "Office" by searching "Fortunapix Pvt Ltd" and selecting "Fortunapix Pvt Ltd, Road No. 9, Guttala Begumpet, Kavuri Hills, Jubilee Hills, Hyderabad, Telangana, 500033, India"
    Then Saved location "Office" should show address "Fortunapix Pvt Ltd, Road No. 9, Guttala Begumpet, Kavuri Hills, Jubilee Hills, Hyderabad, Telangana, 500033, India"
    When User deletes saved location "Office"
    Then Saved location "Office" should be deleted
