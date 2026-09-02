@CRM
@Lead
@LeadCreation

Feature: Create Lead

  Scenario: Create a School lead

    Given User logs in for CRM Activity from Excel row 1
    And User clicks Sales
    And User clicks Leads
    And User clicks Create Lead button
    And User selects random Source of Lead
    And User selects Lead Type as "School"
    And User enters School Name as "New Era School"
    And User enters Lead Display Name as "New Era School"
    And User clicks Lead Submit button
    Then Lead created successfully popup should be displayed
