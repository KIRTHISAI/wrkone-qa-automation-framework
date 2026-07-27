Feature: User Management

  Scenario: Login and Add User
    Given User launches browser
    When User enters Email and password
    And User clicks login button
    Then User should login successfully
    When User clicks User Management
    And User clicks Users
    And User clicks Add User
    And User enters first name "Keerthi"
    And User enters last name "Indla"
    And User enters display name "Keerthi"
    And User enters email "keerthi@onelern.com"
    And User enters employee id "12345"
    And User selects department
    And User enters password "123456"
    When User clicks Create User
    Then User should be created successfully!