package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;

import model.CRMActivity;

import pages.LoginPage;
import pages.crm.ActivityCommonPage;

import Utilities.ExcelUtils;
import base.baseClass;

public class CRMStepdefinition extends baseClass {

    private ActivityCommonPage crmPage;
    // =========================================================
    // GET CRM PAGE
    // =========================================================

    private ActivityCommonPage getCRMPage() {

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver is NULL. Browser must be started before accessing ActivityCommonPage."
            );
        }

        if (crmPage == null) {

            crmPage =
                    new ActivityCommonPage(driver);
        }

        return crmPage;
    }

    // =========================================================
    // LOGIN
    // =========================================================

    @Given("User logs in for CRM Activity from Excel row {int}")
    public void userLogsInForCRMActivityFromExcel(
            int rowNumber) {

        System.out.println();
        System.out.println(
                "=========================================="
        );
        System.out.println(
                "CRM ACTIVITY LOGIN"
        );
        System.out.println(
                "Excel Row = " + rowNumber
        );
        System.out.println(
                "=========================================="
        );

        if (driver == null) {

            throw new IllegalStateException(
                    "WebDriver is NULL. Browser must be started before CRM login."
            );
        }

        // -----------------------------------------------------
        // READ ACTIVITY DATA
        // -----------------------------------------------------

        CRMActivity activity =
                ExcelUtils.getCRMActivity(rowNumber);

        if (activity == null) {

            throw new IllegalStateException(
                    "CRMActivity data is NULL for Excel row "
                            + rowNumber
            );
        }

        // -----------------------------------------------------
        // READ LOGIN DATA
        // -----------------------------------------------------

        String email =
                ExcelUtils.getCRMEmail(rowNumber);

        String password =
                ExcelUtils.getCRMPassword(rowNumber);

        if (email == null ||
                email.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Login email is empty for Excel row "
                            + rowNumber
            );
        }

        if (password == null ||
                password.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "Login password is empty for Excel row "
                            + rowNumber
            );
        }

        System.out.println(
                "Login Email = " + email
        );

        System.out.println(
                "Current URL before login = "
                        + driver.getCurrentUrl()
        );

        // -----------------------------------------------------
        // LOGIN
        // -----------------------------------------------------

        System.out.println(
                "# Starting Login"
        );

        getLoginPage().login(
                email,
                password
        );

        System.out.println(
                "# Login submitted"
        );

        // -----------------------------------------------------
        // WAIT FOR LOGIN TO COMPLETE
        // -----------------------------------------------------

        waitForLoginCompletion();

        System.out.println(
                "CRM Activity login completed."
        );

        System.out.println(
                "Current URL after login = "
                        + driver.getCurrentUrl()
        );
    }

    // =========================================================
    // WAIT FOR LOGIN COMPLETION
    // =========================================================

    private void waitForLoginCompletion() {
        waitForUrl("/qa-crm");
    }

    // =========================================================
    // ACTIVITIES
    // =========================================================

    @And("User clicks Activities")
    public void userClicksActivities() {

        getCRMPage()
                .clickActivities();
    }

    // =========================================================
    // ALL ACTIVITIES
    // =========================================================

    @And("User clicks All Activities")
    public void userClicksAllActivities() {

        getCRMPage()
                .clickAllActivities();
    }

    // =========================================================
    // CREATE ACTIVITY MENU
    // =========================================================

    @And("User clicks Create Activity Menu")
    public void userClicksCreateActivityMenu() {

        getCRMPage()
                .clickCreateActivity();
    }
}