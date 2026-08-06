Feature: CRM Activity

Scenario Outline: Create CRM Activity

When User clicks Applications
And User clicks CRM
Then CRM page should be displayed
When User clicks Activities
And User clicks All Activities
And User clicks Create Activity Menu
When User creates CRM Activity from Excel row <RowNo>
Then Activity should be created successfully

Examples:
| RowNo |
| 1 |
| 2 |
| 3 |
| 4 |
| 5 |
| 6 |
| 7 |
| 8 |
| 9 |
| 10 |
| 11 |
| 12 |