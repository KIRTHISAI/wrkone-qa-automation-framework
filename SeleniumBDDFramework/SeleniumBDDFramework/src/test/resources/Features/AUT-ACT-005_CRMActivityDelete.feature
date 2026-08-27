@CRM
@GeneralActivity
@DeleteActivity

Feature: General Activity Delete

  @DeleteGeneral
  Scenario Outline: Delete General Activity - Excel Row <row>

    Given User logs in for CRM Activity from Excel row <row>
    And User clicks Activities
    And User clicks All Activities
    And User clicks Create Activity Menu
    And User creates General Activity from Excel row <row>
    Then General Activity should be created successfully
    When User deletes General Activity from Excel row <row>
    Then General Activity should be deleted successfully

    Examples:
      | row |
      | 1   |
     