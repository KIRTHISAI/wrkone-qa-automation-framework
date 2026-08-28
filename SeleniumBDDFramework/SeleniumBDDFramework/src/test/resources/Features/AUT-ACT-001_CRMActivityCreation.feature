@CRM
@GeneralActivity
@CreateActivity

Feature: General Activity Creation

  @CreateGeneral
  Scenario Outline: Create General Activity - Excel Row <row>

    Given User logs in for CRM Activity from Excel row <row>
    And User clicks Activities
    And User clicks All Activities
    And User clicks Create Activity Menu
    And User creates General Activity from Excel row <row>
    Then General Activity should be created successfully

    Examples:
      | row |
      | 1   |
      | 2   |
      | 3   |
      | 4   |
      | 5   |
      | 6   |
      | 7   |
      | 8   |
      | 9   |
      | 10  |
      | 11  |
      | 12  |
      
   
      
      
      
      