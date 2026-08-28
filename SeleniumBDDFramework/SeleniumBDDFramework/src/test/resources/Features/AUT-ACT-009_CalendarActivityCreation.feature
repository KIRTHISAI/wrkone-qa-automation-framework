@Calendar

Feature: General Activity Creation from Calendar

  @CreateGeneralActivityFromCalendar
  Scenario: Create a general activity from Calendar

    Given User logs in for CRM Activity from Excel row 1
    When User opens Calendar
    And User clicks New Activity from Calendar
    And User creates General Activity from Excel row 1
    Then General Activity should be created successfully
    When User edits Calendar activity from Excel row 1
    Then Calendar activity should be updated successfully

  @LinkedActivity @CreateLinkedActivityFromCalendar
  Scenario: Create and edit a linked activity from Calendar

    Given User logs in for Linked Activity from Excel row 1
    When User opens Calendar
    And User clicks New Activity from Calendar
    And User creates Linked CRM Activity from Calendar Excel row 1
    Then Linked CRM Activity should be created successfully
    When User edits Linked Calendar activity from Excel row 1
    Then Linked Calendar activity should be updated successfully