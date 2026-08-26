@CRM
@GeneralActivity
@ViewActivity

Feature: General Activity View

  @ViewGeneral

  Scenario Outline: View General Activity - Excel Row <row>

    Given CRM General Activity exists from Excel row <row>

    When User clicks Activities

    And User clicks All Activities

    And User views General Activity from Excel row <row>

    Then General Activity details should be displayed

    Examples:
      | row |
      | 1   |
      | 2   |
      | 3   |