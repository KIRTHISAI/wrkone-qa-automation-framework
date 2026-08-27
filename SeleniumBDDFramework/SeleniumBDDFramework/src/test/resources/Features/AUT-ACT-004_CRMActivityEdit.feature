@CRM
@GeneralActivity
@EditActivity

Feature: General Activity Edit

  @EditGeneral
  Scenario Outline: Edit General Activity - Excel Row <row>

    Given User logs in for CRM Activity from Excel row <row>
    And User clicks Activities
    And User clicks All Activities
    And User clicks Create Activity Menu
    And User creates General Activity from Excel row <row>
    Then General Activity should be created successfully
    When User edits General Activity from Excel row <row>

    Then General Activity should be updated successfully

    Examples:
      | row |
      | 1   |
      | 2   |
      | 3   |