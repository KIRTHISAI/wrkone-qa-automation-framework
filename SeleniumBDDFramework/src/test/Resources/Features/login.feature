Feature: Login

Scenario: Login with Valid Credentials

Given User launches browser
When User enters Email and password
And User clicks login button
Then User should login successfully