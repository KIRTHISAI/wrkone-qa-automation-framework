@CRM
@ActivityFilter

Feature: Activity Filters

  @FilterByActivityType
  Scenario Outline: Filter activities by Activity Type "<activityType>"

    Given User logs in for CRM Activity from Excel row 1
    And User clicks Activities
    And User clicks All Activities
    When User clicks on Filters
    And User selects Activity Type filter "<activityType>"
    And User clicks Apply button for Activity Type filter
    Then Only activities with type "<activityType>" should be displayed
    When User clicks on Filters
    And User clicks Clear All button

    Examples:
      | activityType    |
      | Physical Visit |

  @FilterByStatus
  Scenario Outline: Filter activities by Status "<status>"

    Given User logs in for CRM Activity from Excel row 1
    And User clicks Activities
    And User clicks All Activities
    When User clicks on Filters
    And User selects Status filter "<status>"
    And User clicks Apply button for Status filter
    Then Only activities with status "<status>" should be displayed
    When User changes items per page from 10 to 25
    Then User should see up to 25 activity records
    When User clicks on Filters
    And User clicks on Sort tab
    And User selects "Date" sort order "Ascending"
    And User clicks Apply button for Sort
    Then Activities should be sorted by "Date" in "Ascending" order
    When User clicks on Filters
    And User clicks Clear All button

    Examples:
      | status    |
      | Overdue   |
      | Completed |
      