@CRM
@LinkedActivity
@ViewActivity

Feature: Linked CRM Activity View

  @ViewLinked
  Scenario Outline: View Linked Activity from Excel Row <row>

    Given User logs in for Linked Activity from Excel row <row>
    And User clicks Activities
    And User clicks All Activities
    And User clicks Create Activity Menu
    And User creates Linked CRM Activity from Excel row <row>
    Then Linked CRM Activity should be created successfully
    When User views Linked Activity from Excel row <row>
    Then Linked Activity details should be displayed

    Examples:
      | row |
      | 1   |
