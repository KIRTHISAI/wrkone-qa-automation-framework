Feature: Linked CRM Activity

Scenario Outline: Create Linked Activity from Excel Row <row>

 Given User logs in for CRM Activity from Excel row <row>
And User clicks Activities

And User clicks All Activities

And User clicks Create Activity Menu

And User creates Linked CRM Activity from Excel row <row>

Then Linked CRM Activity should be created successfully

Examples:
| row |
| 1   |
| 2   |
| 3   |
