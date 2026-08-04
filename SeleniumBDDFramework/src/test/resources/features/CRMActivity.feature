Feature: CRM Activity

Scenario Outline: Create CRM Activity - <ActivityType>

When User clicks Applications
And User clicks CRM
Then CRM page should be displayed
When User clicks Activities
And User clicks All Activities
And User clicks Create Activity Menu
And User selects activity type "<ActivityType>"
And User enters Purpose "Automation Activity"
And User enters Description "Created through Selenium Automation"
And User selects date after 10 days
And User selects Start Time
And User selects End Time
And User selects Assignment Type
And User clicks Create Activity Button
Then Activity should be created successfully

Examples:
| ActivityType   |
| Call           |
| Physical Visit |
| Virtual Visit  |
| Email          |