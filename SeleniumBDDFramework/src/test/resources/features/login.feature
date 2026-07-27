Feature: User Management

Scenario: Login and Add User
  Given User launches browser
  When User enters Email and password
  And User clicks login button
  Then User should login successfully
  When User clicks User Management
  And User clicks Users
  And User clicks Add User
  And User enters user details
  When User clicks Create User
  Then User should be created successfully!