@CRM
@GeneralActivity
@EditActivity

Feature: General Activity Edit

  @EditGeneral
  Scenario Outline: Edit General Activity - Excel Row <row>

    Given CRM General Activity exists from Excel row <row>

    When User edits General Activity from Excel row <row>

    Then General Activity should be updated successfully

    Examples:
      | row |
      | 1   |
      | 2   |
      | 3   |