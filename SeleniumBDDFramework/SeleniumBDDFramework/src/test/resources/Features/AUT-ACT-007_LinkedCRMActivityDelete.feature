@CRM
@LinkedActivity
@DeleteActivity

Feature: Linked CRM Activity Delete

  @DeleteLinked
  Scenario Outline: Delete Linked Activity from Excel Row <row>

    Given User logs in for Linked Activity from Excel row <row>
    And User clicks Activities
    And User clicks All Activities
    And User clicks Create Activity Menu
    And User creates Linked CRM Activity from Excel row <row>
    Then Linked CRM Activity should be created successfully
    When User deletes Linked Activity from Excel row <row>
    Then Linked Activity should be deleted successfully

    Examples:
      | row |
      | 1   |
