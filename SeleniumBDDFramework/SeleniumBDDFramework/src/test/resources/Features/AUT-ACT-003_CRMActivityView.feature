@CRM
@GeneralActivity
@ViewActivity

Feature: General Activity View

  @ViewGeneral

  Scenario Outline: View General Activity - Excel Row <row>

    Given User logs in for CRM Activity from Excel row <row>
    And User clicks Activities
    And User clicks All Activities
    And User clicks Create Activity Menu
    And User creates General Activity from Excel row <row>
    Then General Activity should be created successfully
    When User views General Activity from Excel row <row>

    Then General Activity details should be displayed

    Examples:
      | row |
      | 1   |
      