Feature: User Management

Scenario: Create, Update Role and Deactivate User

  When User clicks Users card
And User clicks Add User
And User enters user details
And User clicks Create User
Then User should be created successfully

When User searches for the created user
And User opens the user profile

And User clicks Manage Roles
And User assigns QA Role
And User clicks Update Roles

And User clicks Edit User
When User deactivates the user
And User confirms deactivation
And User searches for the created user
Then User status should be Deactivated