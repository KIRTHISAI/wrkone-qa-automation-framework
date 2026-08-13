Feature: AUT-ACT-001 - CRM Activity Creation

  Scenario Outline: AUT-ACT-001 - Create General Activity - <Activity Type> - <Assignment Type>

    When User logs in for CRM Activity
    And User clicks Applications
    And User clicks CRM
    Then CRM page should be displayed

    When User clicks Activities
    And User clicks All Activities
    And User clicks Create Activity Menu

    And User creates CRM Activity from Excel row <row>

    Then Activity should be created successfully

    And User handles CRM Activity edit from Excel row <row>

    Examples:
      | row |
      | 1   |
      | 2   |
      | 3   |
      