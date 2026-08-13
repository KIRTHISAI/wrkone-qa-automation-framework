Feature: Linked CRM Activity

Scenario Outline: Create Linked Activity - <Activity Type> - <Assignment Type>

When User logs in for Linked Activity
And User clicks Activities
And User clicks All Activities
And User clicks Create Activity Menu

And User creates Linked CRM Activity from Excel row <row>

Then Linked CRM Activity should be created successfully

Examples:

| row |
| 1 |
| 2 |
| 3 |
| 4 |
| 5 |
