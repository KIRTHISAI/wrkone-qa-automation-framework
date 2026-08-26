@CRM
@GeneralActivity
@DeleteActivity

Feature: General Activity Delete

  @DeleteGeneral
  Scenario Outline: Delete General Activity - Excel Row <row>

    Given CRM General Activity exists from Excel row <row>
    When User deletes General Activity from Excel row <row>
    Then General Activity should be deleted successfully

    Examples:
      | row |
      | 1   |
      | 2   |
      | 3   |