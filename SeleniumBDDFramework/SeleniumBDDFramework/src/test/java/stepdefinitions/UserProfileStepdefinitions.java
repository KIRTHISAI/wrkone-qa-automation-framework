

package stepdefinitions;

import base.baseClass;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AdduserPage;
import pages.UserListPage;
import pages.UserProfilePage;

public class UserProfileStepdefinitions extends baseClass {

    private UserProfilePage profile;
    private UserListPage userListPage;

    private UserProfilePage getProfile() {
        if (profile == null) {
            profile = new UserProfilePage(driver);
        }
        return profile;
    }

    private UserListPage getUserListPage() {
        if (userListPage == null) {
            userListPage = new UserListPage(driver);
        }
        return userListPage;
    }

    @And("User clicks Manage Roles")
    public void user_clicks_manage_roles() {
        getProfile().clickManageRoles();
    }

    @And("User assigns QA Role")
    public void user_assigns_qa_role() {
        getProfile().assignGenericRole();
    }

    @And("User clicks Update Roles")
    public void user_clicks_update_roles() {
        getProfile().clickUpdateRoles();
    }

    @And("User clicks Edit User")
    public void user_clicks_edit_user() {
        getProfile().clickEditUser();
    }

    @When("User deactivates the user")
    public void user_deactivates_the_user() {
        getProfile().deactivateUser();
    }

    @And("User confirms deactivation")
    public void user_confirms_deactivation() {
        getProfile().clickUpdateUser();
    }

    @Then("User status should be Deactivated")
    public void user_status_should_be_deactivated() {

        String userName = AdduserPage.generatedName;

        new UserListPage(driver)
                .verifyUserStatus(userName, "Deactivated");
    }
}