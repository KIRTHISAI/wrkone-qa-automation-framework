@Login
Feature: Add User

Scenario: Create New User

Given User is on Add User page
When User enters first name "Keerthi"
And User enters last name "Indla"
And User enters display name "Keerthi"
And User enters email "keerthi@test.com"
And User enters employee id "12345"
And User clicks Save
Then User should be created successfully