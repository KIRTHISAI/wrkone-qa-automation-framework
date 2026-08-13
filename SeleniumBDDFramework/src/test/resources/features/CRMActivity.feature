Feature: CRM Activity

Scenario Outline: Create General Activity - <Activity Type> - <Assignment Type>

When User logs in for CRM Activity
And User clicks Applications
And User clicks CRM
Then CRM page should be displayed

When User clicks Activities
And User clicks All Activities
And User clicks Create Activity Menu

And User creates CRM Activity from Excel row <row>

Then Activity should be created successfully

Examples:

| row |
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